package jgarciabt.smartwebview;

import android.app.Activity;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.listeners.ActionClickListener;
import com.squareup.otto.Subscribe;

import butterknife.ButterKnife;
import butterknife.InjectView;
import jgarciabt.smartwebview.broadcast.NetworkBroadcastReceiver;
import jgarciabt.smartwebview.broadcast.events.InternetDownEvent;
import jgarciabt.smartwebview.bus.BusManager;
import jgarciabt.smartwebview.utils.Constants;
import jgarciabt.smartwebview.utils.CustomWebViewClient;
import jgarciabt.smartwebview.utils.SnackbarUtils;


/**
 * Created by JGarcia on 28/3/15.
 */
public class LauncherActivity extends Activity {

    @InjectView(R.id.webViewFrame) public WebView webViewFrame;
    private BusManager busManager;
    private NetworkBroadcastReceiver networkBroadcastReceiver;
    private IntentFilter intentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        ButterKnife.inject(this);
        busManager = BusManager.getInstance();
        busManager.register(this);

        networkBroadcastReceiver = new NetworkBroadcastReceiver(this);
        intentFilter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");

        setupWebView();
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
    public void onBackPressed() {

        if(webViewFrame.canGoBack())
        {
            if(!isOnRootURL(webViewFrame.getUrl()))
            {
                webViewFrame.goBack();
            }
            else
            {
                finish();
            }
        }
        else
        {
            finish();
        }
    }

    private void setupWebView()
    {
        webViewFrame.setWebViewClient(new CustomWebViewClient(this));

        webViewFrame.loadUrl(Constants.BASE_URL);
        WebSettings webSettings = webViewFrame.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }

    private boolean isOnRootURL(String currentURL)
    {
        return currentURL.matches(Constants.BASE_URL);
    }

    @Subscribe
    public void internetConnectionGone(InternetDownEvent event)
    {
        ActionClickListener action = new ActionClickListener() {
            @Override
            public void onActionClicked(Snackbar snackbar) {
                Log.v(Constants.LOG_TAG, "Dismissed");
            }
        };

        SnackbarUtils.showNoInternetSnackbar(this, action);

    }

}
