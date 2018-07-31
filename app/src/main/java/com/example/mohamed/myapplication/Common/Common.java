package com.example.mohamed.myapplication.Common;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.mohamed.myapplication.models.User;

import java.util.Calendar;
import java.util.Locale;

/**
 * Created by mohamed on 3/21/18.
 */

public class Common {

    public static User currentUser;

    public static boolean isConnectToInternet(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {
            NetworkInfo[] info = connectivityManager.getAllNetworkInfo();

            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                        return true;
                }
            }
        }
        return false;
    }

    public static String getDate(long time){
        Calendar calendar= Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(time);
        StringBuilder date=new StringBuilder(android.text.format.DateFormat.format("dd/MM/yyyy     HH:mm",calendar).toString());
        return date.toString();
    }



}
