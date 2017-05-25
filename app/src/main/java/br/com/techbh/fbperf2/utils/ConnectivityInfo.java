package br.com.techbh.fbperf2.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

/**
 * Checks if the device has an up and running internet connection
 */

public class ConnectivityInfo {

    /**
     *
     * @param context An valid context to access the {@link ConnectivityManager}
     * @return Return if the device has an up and running internet connection
     */
    public static boolean isInternetUp(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnected();

        return isConnected;
    }

    /**
     *
     * @param context An valid context to access the {@link ConnectivityManager}
     * @return Return if the device has an up and running internet connection by WiFi
     */
    public static boolean isConnectedByWifi(Context context) {

        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isWiFi = activeNetwork != null
                && activeNetwork.getType() == ConnectivityManager.TYPE_WIFI;

        return isInternetUp(context) && isWiFi;

    }

    /**
     *
     * @param context An valid context to access the {@link ConnectivityManager}
     * @return Return if the device has an up and running internet connection by Mobile
     */
    public static boolean isConnectedByMobile(Context context) {

        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        TelephonyManager tm =
                (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isMobile = activeNetwork != null
                && activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE
                //TODO: Avaliar porque foi necessário esses testes na implementação anterior
                /*&& (tm.getNetworkType() == TelephonyManager.NETWORK_TYPE_UNKNOWN
                    || tm.getNetworkType() == TelephonyManager.NETWORK_TYPE_EDGE
                    || tm.getNetworkType() == TelephonyManager.NETWORK_TYPE_GPRS)*/;

        return isInternetUp(context) && isMobile;
    }

}
