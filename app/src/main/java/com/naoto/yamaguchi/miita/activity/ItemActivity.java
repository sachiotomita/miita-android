package com.naoto.yamaguchi.miita.activity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.naoto.yamaguchi.miita.R;
import com.naoto.yamaguchi.miita.entity.ui.Item;
import com.naoto.yamaguchi.miita.imagefetcher.ImageFetcher;
import com.naoto.yamaguchi.miita.model.ItemModel;
import com.naoto.yamaguchi.miita.model.base.OnModelListener;
import com.naoto.yamaguchi.miita.oauth.CurrentUser;
import com.naoto.yamaguchi.miita.util.analytics.Analytics;
import com.naoto.yamaguchi.miita.util.exception.MiitaException;
import com.naoto.yamaguchi.miita.util.share.ShareUtil;

/**
 * TODO
 * 1. presenter class
 * 2. stock buttonの分離
 * 3. webView Clientの分離
 * 4. html生成の分離
 */
public class ItemActivity extends AppCompatActivity
        implements View.OnClickListener,
        AppBarLayout.OnOffsetChangedListener {

    private static final int COLLAPSED_OFFSET = -300;

    private AppBarLayout appBarLayout;
    private Toolbar toolbar;
    private ActionBar actionBar;
    private CollapsingToolbarLayout toolbarLayout;
    private ProgressBar spinner;
    private FloatingActionButton stockButton;
    private WebView webView;
    private CurrentUser currentUser;

    private TextView titleTextView;
    private View userView;
    private ImageView userImageView;
    private TextView userIdTextView;
    private TextView createdTextView;

    // FIXME: model -> presenter or viewModel
    private ItemModel model;

    private Item item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        Analytics.getInstance().onCreate(this);
        this.init();
        this.parseIntent();
        this.setLayout();
        this.checkStock();
        this.loadBody();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                supportFinishAfterTransition();
                return true;
            case R.id.action_share:
                ShareUtil.share(this, this.item);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void init() {
        this.model = new ItemModel(this);
        this.currentUser = CurrentUser.getInstance();
    }

    private void parseIntent() {
        Intent intent = this.getIntent();
        this.item = intent.getParcelableExtra("item");
    }

    private void setLayout() {
        this.appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        this.appBarLayout.addOnOffsetChangedListener(this);

        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(this.toolbar);

        this.actionBar = getSupportActionBar();
        this.actionBar.setDisplayHomeAsUpEnabled(true);
        this.actionBar.setHomeButtonEnabled(true);

        this.toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        this.toolbarLayout.setTitle(this.item.getTitle());

        this.stockButton = (FloatingActionButton) findViewById(R.id.fab);
        this.stockButton.setOnClickListener(this);
        this.stockButton.setImageResource(R.drawable.ic_default_stock_button_48px);
        this.stockButton.setBackgroundTintList(
                ColorStateList.valueOf(getResources().getColor(R.color.defaultButton))
        );

        this.userView = findViewById(R.id.item_header_user_view);
        this.userView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: transition user activity
            }
        });

        this.titleTextView = (TextView) findViewById(R.id.item_header_title);
        this.titleTextView.setTransitionName(getString(R.string.transition_title_item_list_to_item));
        this.titleTextView.setText(this.item.getTitle());

        this.userImageView = (ImageView) findViewById(R.id.item_header_user_image);
        this.userImageView.setTransitionName(getString(R.string.transition_image_item_list_to_item));
        ImageFetcher.getInstance()
                .setContext(this)
                .fetch(this.item.getUser().getImageUrlString(), this.userImageView);

        this.userIdTextView = (TextView) findViewById(R.id.item_header_user_id);
        this.userIdTextView.setTransitionName(getString(R.string.transition_user_id_item_list_to_item));
        this.userIdTextView.setText(this.item.getUser().getId());

        this.createdTextView = (TextView) findViewById(R.id.item_header_created);
        this.createdTextView.setTransitionName(getString(R.string.transition_created_item_list_to_item));
        final String desc = this.item.getCreatedAtString() + "に投稿しました";
        this.createdTextView.setText(desc);

        this.spinner = (ProgressBar) findViewById(R.id.progress_bar);
        this.spinner.setVisibility(View.VISIBLE);

        this.webView = (WebView) findViewById(R.id.item_webview);
        this.webView.getSettings().setJavaScriptEnabled(true);
        this.webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

                webView.setVisibility(View.GONE);
                spinner.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                webView.setVisibility(View.VISIBLE);
                spinner.setVisibility(View.GONE);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                // TODO: error handle
            }
        });
    }

    private void checkStock() {
        this.stockButton.setEnabled(false);

        if (!this.currentUser.isAuthorize()) {
            return;
        }

        this.model.request(ItemModel.Type.CHECK, this.item.getId(), new OnModelListener<Void>() {
            @Override
            public void onSuccess(Void results) {
                stockButton.setImageResource(R.drawable.ic_unstock_button_48px);
                stockButton.setBackgroundTintList(
                        ColorStateList.valueOf(getResources().getColor(R.color.unStockButton))
                );
            }

            @Override
            public void onError(MiitaException e) {
                stockButton.setImageResource(R.drawable.ic_stock_button_48px);
                stockButton.setBackgroundTintList(
                        ColorStateList.valueOf(getResources().getColor(R.color.stockButton))
                );
            }

            @Override
            public void onComplete() {
                stockButton.setEnabled(true);
            }
        });
    }

    private void loadBody() {
        String html = this.createHTML(this.item.getBody());
        this.webView.loadDataWithBaseURL(
                "file:///android_asset/",
                html,
                "text/html",
                "utf−8",
                null
        );
    }

    public String createHTML(String body) {
        StringBuffer htmlBuilder = new StringBuffer();

        htmlBuilder.append("<html>");
        htmlBuilder.append("<head>");
        htmlBuilder.append("<meta name=\"viewport\" content=\"width=device-width,initial-scale=1\" />");
        htmlBuilder.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\">");
        htmlBuilder.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"github.css\">");
        htmlBuilder.append("<script type=\"text/javascript\" src=\"highlight.js\"></script>");
        htmlBuilder.append("</head>");
        htmlBuilder.append("<body>");
        htmlBuilder.append(body);
        htmlBuilder.append("</body>");
        htmlBuilder.append("<script>var preArray = document.getElementsByTagName(\"pre\");for (var i = 0; i < preArray.length; i++) {hljs.highlightBlock(preArray[i])}</script>");
        htmlBuilder.append("</html>");

        return htmlBuilder.toString();
    }

    @Override
    public void onClick(View view) {
        this.stockButton.setEnabled(false);

        if (this.model.isStock()) {
            Analytics.getInstance().sendEvent(Analytics.ActionType.UNSTOCK, this.item.getId());

            this.model.request(ItemModel.Type.UNSTOCK, this.item.getId(), new OnModelListener<Void>() {
                @Override
                public void onSuccess(Void results) {
                    stockButton.setImageResource(R.drawable.ic_stock_button_48px);
                    stockButton.setBackgroundTintList(
                            ColorStateList.valueOf(getResources().getColor(R.color.stockButton))
                    );
                }

                @Override
                public void onError(MiitaException e) {
                    // TODO: show alert
                }

                @Override
                public void onComplete() {
                    stockButton.setEnabled(true);
                }
            });
        } else {
            Analytics.getInstance().sendEvent(Analytics.ActionType.STOCK, this.item.getId());

            this.model.request(ItemModel.Type.STOCK, this.item.getId(), new OnModelListener<Void>() {
                @Override
                public void onSuccess(Void results) {
                    stockButton.setImageResource(R.drawable.ic_unstock_button_48px);
                    stockButton.setBackgroundTintList(
                            ColorStateList.valueOf(getResources().getColor(R.color.unStockButton))
                    );
                }

                @Override
                public void onError(MiitaException e) {
                    // TODO: show alert
                }

                @Override
                public void onComplete() {
                    stockButton.setEnabled(true);
                }
            });
        }
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (verticalOffset >= COLLAPSED_OFFSET) {
            this.toolbarLayout.setTitle("");
        } else {
            this.toolbarLayout.setTitle(this.item.getTitle());
        }
    }
}
