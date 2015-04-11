package jgarciabt.smartwebview.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by JGarcia on 28/3/15.
 */
public class CustomWebViewClient extends WebViewClient {

    private Context context;

    public CustomWebViewClient(Context context)
    {
        this.context = context;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url)
    {
        if (Uri.parse(url).getHost().matches(Constants.HOST))
        {
            // This is my web site, so do not override; let my WebView load the page
            return false;
        }

        // Otherwise, the link is not for a page on my site, so launch another Activity that handles URLs
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        context.startActivity(intent);
        return true;
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl)
    {
        super.onReceivedError(view, errorCode, description, failingUrl);

        view.loadUrl(Constants.OFFLINE_FILE);
    }
}


