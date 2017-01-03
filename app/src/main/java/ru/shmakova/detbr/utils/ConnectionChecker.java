package ru.shmakova.detbr.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by user on 11.09.16.
 */
@SuppressWarnings("PMD.UseVarargs")
public class ConnectionChecker {
    private final Context context;

    public ConnectionChecker(Context context) {
        this.context = context;
    }

    public boolean isNetworkConnected() {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager != null) {
            NetworkInfo[] info = manager.getAllNetworkInfo();
            if (info != null) {
                return checkIfConnected(info);
            }
        }
        return false;
    }

    private boolean checkIfConnected(NetworkInfo[] info) {
        for (NetworkInfo anInfo : info) {
            if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                return true;
            }
        }
        return false;
    }
}
