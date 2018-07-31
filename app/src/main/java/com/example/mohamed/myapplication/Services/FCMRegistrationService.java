package com.example.mohamed.myapplication.Services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Map;

        ///for getting token   and send to server

public class FCMRegistrationService extends IntentService {
    SharedPreferences preferences;

    public FCMRegistrationService() {
        super("FCM");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String token = FirebaseInstanceId.getInstance().getToken();

        SharedPreferences.Editor edit = getSharedPreferences("da", Context.MODE_PRIVATE).edit();
        edit.putString("Token", token);
        edit.commit();

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Log.e("token is", token);
        if (!preferences.getBoolean("token_sent", false))
            sendTokenToServer(token);

    }

    private void sendTokenToServer(final String token) {
        String ADD_TOKEN_URL = "http://developerhendy.16mb.com/addnewtoken.php";
        StringRequest request = new StringRequest(Request.Method.POST, ADD_TOKEN_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int responseCode = Integer.parseInt(response);
                if (responseCode == 1) {
                    preferences.edit().putBoolean("token_sent", true).apply();
                    Log.e("Registration Service", "Response : Send Token Success");

                } else {
                    preferences.edit().putBoolean("token_sent", false).apply();
                    Log.e("Registration Service", "Response : Send Token Failed");


                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                preferences.edit().putBoolean("token_sent", false).apply();
                Log.e("Registration Service", "Error :Send Token Failed");

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("token", token);
                return params;

            }
        };

        Volley.newRequestQueue(this).add(request);

    }

}