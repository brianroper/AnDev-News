package com.brianroper.androidweekly.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

/**
 * Created by brianroper on 1/19/17.
 */

public class Util {

    /**
     * easy logging for String array lists
     */
    public static void logList(ArrayList<String> list, String message){
        for(int i = 0; i <= list.size(); i++){
            Log.i(message, list.get(i));
        }
    }

    /**
     * error toast displayed to user when there is no available network
     */
    static public void noActiveNetworkToast(Context context){
        Toasty.error(context,
                "There is currently no active network connection.",
                Toast.LENGTH_SHORT,
                true)
                .show();
    }

    /**
     * check the device for an active network connection
     */
    static public boolean activeNetworkCheck(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(
                Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }
}