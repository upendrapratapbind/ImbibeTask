package com.example.imbibetask;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DownloadManager;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.webkit.DownloadListener;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {


    private WebView mywebView;
    public static Uri webRequestUri;
    String webUrl = "http://traveldocs.imbibetech.in//Itinerary.aspx?type=2DBDFAE4235C17E186F4F5B75C&guid=9500fb36-1a5d-429c-9f28-032081af34b8";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mywebView = (WebView) findViewById(R.id.webView);
        mywebView.setWebViewClient(new WebViewClient());
        mywebView.loadUrl(webUrl);
        WebSettings webSettings = mywebView.getSettings();
        mywebView.setWebViewClient(new MyWebViewClient());
        webSettings.setJavaScriptEnabled(true);

    }


    @SuppressWarnings("deprecation")
    @Override
    public void onBackPressed() {
        if (mywebView.canGoBack()) {
            mywebView.goBack();
        } else {
            super.onBackPressed();
        }

    }

}
