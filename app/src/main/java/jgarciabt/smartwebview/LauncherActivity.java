package jgarciabt.smartwebview;

import android.app.Activity;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

import com.squareup.otto.Subscribe;

import butterknife.ButterKnife;
import butterknife.InjectView;
import jgarciabt.smartwebview.broadcast.NetworkBroadcastReceiver;
import jgarciabt.smartwebview.broadcast.events.InternetDownEvent;
import jgarciabt.smartwebview.bus.BusManager;
import jgarciabt.smartwebview.utils.Constants;
import jgarciabt.smartwebview.utils.CustomWebViewClient;


/**
 * Created by JGarcia on 28/3/15.
 */
public class LauncherActivity extends Activity {

    @InjectView(R.id.webViewFrame)
    public WebView webViewFrame;
    private BusManager busManager;
    private NetworkBroadcastReceiver networkBroadcastReceiver;
    private IntentFilter intentFilter;

    private String lastUrlAvailable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        ButterKnife.inject(this);
        busManager = BusManager.getInstance();
        busManager.register(this);

        networkBroadcastReceiver = new NetworkBroadcastReceiver(this);
        intentFilter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");

        if (savedInstanceState != null) {
            webViewFrame.restoreState(savedInstanceState);
            webViewFrame.setWebViewClient(new CustomWebViewClient(this));
            // Enable JavaScript only if you need it
            // webViewFrame.getSettings().setJavaScriptEnabled(true);
        } else {
            setupWebView();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        unregisterReceiver(networkBroadcastReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();

        registerReceiver(networkBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        webViewFrame.saveState(outState);
        outState.putString(Constants.LAST_URL_AVAILABLE, lastUrlAvailable);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        lastUrlAvailable = savedInstanceState.getString(Constants.LAST_URL_AVAILABLE);
    }

    @Override
    public void onBackPressed() {

        if (!isOnRootURL(webViewFrame.getUrl())) {
            String goBackUrl = previousLevelUrl(webViewFrame.getUrl());
            webViewFrame.loadUrl(goBackUrl);
        } else {
            finish();
        }
    }

    private void setupWebView() {

        lastUrlAvailable = Constants.BASE_URL;
        webViewFrame.loadUrl(Constants.BASE_URL);
        webViewFrame.setWebViewClient(new CustomWebViewClient(this));
        // Enable JavaScript only if you need it
        // webViewFrame.getSettings().setJavaScriptEnabled(true);
    }

    private boolean isOnRootURL(String currentUrl) {

        return (currentUrl.matches(Constants.BASE_URL) || currentUrl.matches(Constants.OFFLINE_FILE));
    }

    private String previousLevelUrl(String currentUrl) {

        String lastPath = Uri.parse(currentUrl).getLastPathSegment();
        return currentUrl.substring(0, currentUrl.length() - lastPath.length() - 1);
    }

    @Subscribe
    public void internetConnectionGone(InternetDownEvent event) {

        lastUrlAvailable = webViewFrame.getUrl();
    }
}
