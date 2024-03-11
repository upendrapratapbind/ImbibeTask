package com.example.imbibetask;



import static android.content.Context.DOWNLOAD_SERVICE;

import android.app.DownloadManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.MailTo;
import android.net.Uri;
import android.os.Environment;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.URLUtil;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;


public class MyWebViewClient extends WebViewClient {

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;

    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        if (request.getUrl().getScheme().equals("mailto")) {
            // This is your website, so don't override. Let your WebView load the
            // page.
            MailTo mt = MailTo.parse(request.getUrl().toString());
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("message/rfc822");
            i.putExtra(Intent.EXTRA_EMAIL, new String[]{mt.getTo()});
            i.putExtra(Intent.EXTRA_SUBJECT, mt.getSubject());
            i.putExtra(Intent.EXTRA_CC, mt.getCc());
            i.putExtra(Intent.EXTRA_TEXT, mt.getBody());
            view.getContext().startActivity(i);
            view.reload();
            return true;
        } else if (request.getUrl().getScheme().contains("tel")) {
            Intent tel = new Intent(Intent.ACTION_DIAL, Uri.parse(request.getUrl().toString()));
            view.getContext().startActivity(tel);
            view.reload();
            return true;
        }
        else if (request.getUrl().getScheme().contains("about")) {
            view.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimeType, long contentLength) {

                        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                        request.setMimeType(mimeType);
                        String cookies = CookieManager.getInstance().getCookie(url);
                        String fileName = URLUtil.guessFileName(url, contentDisposition, ".pdf");
                        request.addRequestHeader("cookie", cookies);
                        request.addRequestHeader("User-Agent", userAgent);
                        request.setDescription("Downloading file...");
                        request.setTitle(URLUtil.guessFileName(url, contentDisposition, mimeType));
                        request.allowScanningByMediaScanner();
                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);
                        DownloadManager dm = (DownloadManager) view.getContext().getSystemService(DOWNLOAD_SERVICE);
                        dm.enqueue(request);
                        Toast.makeText(view.getContext(), "Downloading File...", Toast.LENGTH_LONG).show();

             }
        });
            return true;

        }
        return false;


    }

}

