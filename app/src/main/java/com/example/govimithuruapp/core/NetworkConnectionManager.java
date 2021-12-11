/*
Adapted from https://medium.com/dsc-alexandria/implementing-internet-connectivity-checker-in-android-apps-bf28230c4e86
 */

package com.example.govimithuruapp.core;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;

public class NetworkConnectionManager {
    private Context context;

    // Singleton
    private static NetworkConnectionManager instance;

    private NetworkConnectionManager() {
    }

    public static NetworkConnectionManager getInstance(Context context) {
        if (instance == null) instance = new NetworkConnectionManager();
        instance.context = context;
        return instance;
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) return false;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            NetworkCapabilities cap = cm.getNetworkCapabilities(cm.getActiveNetwork());
            if (cap == null) return false;
            return cap.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Network[] networks = cm.getAllNetworks();
            for (Network n : networks) {
                NetworkInfo nInfo = cm.getNetworkInfo(n);
                if (nInfo != null && nInfo.isConnected()) return true;
            }
        } else {
            NetworkInfo[] networkInfos = cm.getAllNetworkInfo();
            for (NetworkInfo nInfo : networkInfos) {
                if (nInfo != null && nInfo.isConnected()) return true;
            }
        }
        return false;
    }

}
