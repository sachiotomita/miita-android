package com.naoto.yamaguchi.miita.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;
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
    }

    private void setLayout() {

    }

    private void loadLicense() {

    }
}
