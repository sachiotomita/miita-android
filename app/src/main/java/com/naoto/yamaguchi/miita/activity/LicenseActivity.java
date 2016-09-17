package com.naoto.yamaguchi.miita.activity;

import android.graphics.Bitmap;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.naoto.yamaguchi.miita.R;

public class LicenseActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ActionBar actionBar;
    private ProgressBar spinner;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_license);

        this.setLayout();
        this.loadLicense();
    }

    private void setLayout() {
        this.toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(this.toolbar);
        this.toolbar.setTitle("License");

        this.actionBar = getSupportActionBar();
        this.actionBar.setDisplayHomeAsUpEnabled(true);
        this.actionBar.setHomeButtonEnabled(true);

        this.spinner = (ProgressBar)findViewById(R.id.progress_bar);
        this.spinner.setVisibility(View.VISIBLE);

        this.webView = (WebView)findViewById(R.id.license_webview);
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

    private void loadLicense() {
        this.webView.loadUrl("file:///android_asset/license.html");
    }
}
