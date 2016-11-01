package com.naoto.yamaguchi.miita.activity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.naoto.yamaguchi.miita.R;
import com.naoto.yamaguchi.miita.model.ItemModel;
import com.naoto.yamaguchi.miita.model.base.OnModelListener;
import com.naoto.yamaguchi.miita.oauth.CurrentUser;
import com.naoto.yamaguchi.miita.util.exception.MiitaException;

/**
 * TODO
 * 1. presenter class
 * 2. stock buttonの分離
 * 3. webView Clientの分離
 * 4. html生成の分離
 *
 */
public class ItemActivity extends AppCompatActivity
        implements View.OnClickListener {

  private Toolbar toolbar;
  private ActionBar actionBar;
  private CollapsingToolbarLayout toolbarLayout;
  private ProgressBar spinner;
  private FloatingActionButton stockButton;
  private WebView webView;
  private CurrentUser currentUser;

  // FIXME: model -> presenter or viewModel
  private ItemModel model;
  private String itemId;
  private String itemTitle;
  private String itemUrl;
  private String itemBody;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_item);

    this.init();
    this.parseIntent();
    this.setLayout();
    this.checkStock();
    this.loadBody();
  }

  private void init() {
    this.model = new ItemModel(this);
    this.currentUser = CurrentUser.getInstance();
  }

  private void parseIntent() {
    Intent intent = this.getIntent();
    this.itemId = intent.getStringExtra("item_id");
    this.itemTitle = intent.getStringExtra("item_title");
    this.itemUrl = intent.getStringExtra("item_url");
    this.itemBody = intent.getStringExtra("item_body");
  }

  private void setLayout() {
    this.toolbar = (Toolbar)findViewById(R.id.toolbar);
    setSupportActionBar(this.toolbar);

    this.actionBar = getSupportActionBar();
    this.actionBar.setDisplayHomeAsUpEnabled(true);
    this.actionBar.setHomeButtonEnabled(true);

    this.toolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.toolbar_layout);
    this.toolbarLayout.setTitle(this.itemTitle);

    this.stockButton = (FloatingActionButton)findViewById(R.id.fab);
    this.stockButton.setOnClickListener(this);

    this.spinner = (ProgressBar)findViewById(R.id.progress_bar);
    this.spinner.setVisibility(View.VISIBLE);

    this.webView = (WebView)findViewById(R.id.item_webview);
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

    if (!this.currentUser.isAuthorize(this)) {
      return;
    }

    this.model.request(ItemModel.Type.CHECK, this.itemId, new OnModelListener<Void>() {
      @Override
      public void onSuccess(Void results) {
        stockButton.setBackgroundTintList(
                ColorStateList.valueOf(getResources().getColor(R.color.red))
        );
      }

      @Override
      public void onError(MiitaException e) {
        stockButton.setBackgroundTintList(
                ColorStateList.valueOf(getResources().getColor(R.color.green))
        );
      }

      @Override
      public void onComplete() {
        stockButton.setEnabled(true);
      }
    });
  }

  private void loadBody() {
    String html = this.createHTML(this.itemBody);
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
      this.model.request(ItemModel.Type.UNSTOCK, this.itemId, new OnModelListener<Void>() {
        @Override
        public void onSuccess(Void results) {
          stockButton.setBackgroundTintList(
                  ColorStateList.valueOf(getResources().getColor(R.color.green))
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
      this.model.request(ItemModel.Type.STOCK, this.itemId, new OnModelListener<Void>() {
        @Override
        public void onSuccess(Void results) {
          stockButton.setBackgroundTintList(
                  ColorStateList.valueOf(getResources().getColor(R.color.red))
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
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        finish();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }
}
