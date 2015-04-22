package jgarciabt.smartwebview.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import jgarciabt.smartwebview.broadcast.events.InternetDownEvent;
import jgarciabt.smartwebview.broadcast.events.InternetUpEvent;
import jgarciabt.smartwebview.bus.BusManager;


/**
 * Created by JGarcia on 28/3/15.
 */
public class NetworkBroadcastReceiver extends BroadcastReceiver{

    private Context context;
    private ConnectivityManager connMgr;
    private NetworkInfo wifiStatus;
    private NetworkInfo mobileStatus;
    private BusManager busManager;

    public NetworkBroadcastReceiver(Context context)
    {
        this.context = context;

        busManager = BusManager.getInstance();
        connMgr = null;
        wifiStatus = null;
        mobileStatus = null;
    }

    @Override
    public void onReceive(Context context, Intent intent)
    {

        connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        wifiStatus = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        mobileStatus = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        //TODO Fix that
        if(mobileStatus == null)
        {
            mobileStatus = wifiStatus;
        }
        if (!wifiStatus.isConnected() && !mobileStatus.isConnected()) {
            busManager.post(new InternetDownEvent());
        }

    }
}
