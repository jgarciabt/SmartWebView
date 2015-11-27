package jgarciabt.smartwebview.utils;

import android.content.Context;
import android.content.Intent;
import android.net.MailTo;
import android.net.Uri;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by JGarcia on 28/3/15.
 */
public class CustomWebViewClient extends WebViewClient {

    private Context context;

    public CustomWebViewClient(Context context) {
        this.context = context;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {

        if (url.startsWith("tel:")) {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
            context.startActivity(intent);
            view.reload();
            return true;
        }

        if (url.startsWith("mailto:")) {

            MailTo mailTo = MailTo.parse(url);
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("message/rfc822");
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{mailTo.getTo()});
            Intent mailer = Intent.createChooser(intent, null);
            context.startActivity(mailer);
            view.reload();
            return true;
        }

        if (Uri.parse(url).getHost().matches(Constants.HOST)) {
            // This is my web site, so do not override; let my WebView load the page
            return false;
        }

        // Otherwise, the link is not for a page on my site, so launch another Activity that handles URLs
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        context.startActivity(intent);
        return true;
    }

    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        super.onReceivedError(view, request, error);

        view.loadUrl(Constants.OFFLINE_FILE);
    }
}


