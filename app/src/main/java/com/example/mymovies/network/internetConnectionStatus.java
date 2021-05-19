package com.example.mymovies.network;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.example.mymovies.MovieDetailsActivity;

public  class internetConnectionStatus {

    public static boolean checkInternet(Context context){

        ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
        if (netInfo != null)
            return true;
//            if (netInfo.getType() == ConnectivityManager.TYPE_WIFI) {
////                Toast.makeText(context, "You're online - wi-fi", Toast.LENGTH_SHORT).show();
//            }
//            if (netInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
////                Toast.makeText(context, "You're online - date", Toast.LENGTH_SHORT).show();
//            }
//
        else
//            Toast.makeText(context, "You're offline", Toast.LENGTH_LONG).show();
        return false;

    }

}
