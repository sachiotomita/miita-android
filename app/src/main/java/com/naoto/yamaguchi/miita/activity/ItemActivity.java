package com.naoto.yamaguchi.miita.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.naoto.yamaguchi.miita.R;
import com.naoto.yamaguchi.miita.model.ItemModel;

public class ItemActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private ActionBar actionBar;
    private CollapsingToolbarLayout toolbarLayout;
    private ProgressBar spinner;
    private FloatingActionButton stockButton;
    private WebView webView;

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
    }

    private void init() {
        this.model = new ItemModel(this);
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
        // TODO:
    }

    @Override
    public void onClick(View view) {

    }
}
