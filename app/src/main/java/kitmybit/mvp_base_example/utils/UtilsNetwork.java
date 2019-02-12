package kitmybit.mvp_base_example.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;

public class UtilsNetwork {

    @SuppressLint("MissingPermission")
    public static boolean isNetwork(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        assert connectivityManager != null;
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isAvailable()
                && connectivityManager.getActiveNetworkInfo().isConnected();
    }
}
