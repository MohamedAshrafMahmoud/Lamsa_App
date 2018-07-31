package com.example.mohamed.myapplication;

import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mohamed.myapplication.Common.Common;
import com.example.mohamed.myapplication.models.second;
import com.google.firebase.iid.FirebaseInstanceId;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class Tab1_3 extends AppCompatActivity {

    TextView Name, Price, Id;
    ImageView imageView, fav;
    String id;
    String url;
    String urlImage;
    Button addItem;
    int timeSec;


    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab1_3);

        Id = (TextView) findViewById(R.id.id);
        Name = (TextView) findViewById(R.id.Name);
        Price = (TextView) findViewById(R.id.Price);
        imageView = (ImageView) findViewById(R.id.image2);
        fav = (ImageView) findViewById(R.id.fav1);
        addItem = (Button) findViewById(R.id.add);

        id = getIntent().getStringExtra("idsecond");

        url = "http://lamsa.pioneers-solutions.org/Api/Helper/GetService?id=" + id;
        urlImage = "http://lamsa.pioneers-solutions.org/";

        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fav.setImageResource(R.drawable.ic_favorite_black_24dp);

                try {
                    requestQueue = Volley.newRequestQueue(Tab1_3.this);
                    String URL = "http://lamsa.pioneers-solutions.org/Api/Helper/AddFaveorite?UserId=1021&ServiceId=" + id;
                    JSONObject jsonBody = new JSONObject();
                    jsonBody.put("UserId", 1021);
                    jsonBody.put("ServiceId", Integer.parseInt(id));
                    final String requestBody = jsonBody.toString();

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(Tab1_3.this, "Added to favorite " + response, Toast.LENGTH_SHORT).show();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("VOLLEY", error.toString());
                            Toast.makeText(Tab1_3.this, error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        @Override
                        public String getBodyContentType() {
                            return "application/json; charset=utf-8";
                        }

                        @Override
                        public byte[] getBody() throws AuthFailureError {
                            try {
                                return requestBody == null ? null : requestBody.getBytes("utf-8");
                            } catch (UnsupportedEncodingException uee) {
                                VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                                return null;
                            }
                        }

                        @Override
                        protected Response<String> parseNetworkResponse(NetworkResponse response) {
                            String responseString = "";
                            if (response != null) {
                                responseString = String.valueOf(response.statusCode);
                                // can get more details such as response.headers
                            }
                            return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                        }
                    };

                    requestQueue.add(stringRequest);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

//                        second second = new second();
//                        second.setId(Id.getText().toString());
//                        second.setName(Name.getText().toString());
//
//
//                        second.setPrice(Price.getText().toString());
//                        //  second.setImage(bm);
//
//                        if (!db.isFavorite(Name.getText().toString())) {
//                            db.addToFavorite(second);
//                            fav.setImageResource(R.drawable.ic_favorite_black_24dp);
//                            Toast.makeText(Tab1_3.this, "Added to favorites", Toast.LENGTH_SHORT).show();
//                        } else {
//                            db.deleteFromFavorite(Name.getText().toString());
//                            fav.setImageResource(R.drawable.ic_favorite_border_black_24dp);
//                            Toast.makeText(Tab1_3.this, "Removed from favorites", Toast.LENGTH_SHORT).show();
//                        }

            }
        });


        load();





        //internal notification

        final NewMessageNotification notification = new NewMessageNotification();

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Toast.makeText(Tab1_3.this, "sold" + "\n thank you", Toast.LENGTH_SHORT).show();


                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        timeSec = 3000;
                        try {
                            sleep(timeSec);

                        } catch (Exception e) {
                            // TODO: handle exception
                            e.printStackTrace();
                        } finally {


                            notification.notify(getBaseContext(), "", 0);
                        }
                    }
                };

                thread.start();
            }
        });

    }


    void load() {

        final ProgressDialog progressDialog = new ProgressDialog(Tab1_3.this);
        progressDialog.setMessage("please wait ....");
        progressDialog.show();


        requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    String id = response.getInt("Id") + "";
                    String name = response.getString("Name");
                    String image = response.getString("Image");
                    String price = response.getString("price");

                    Id.setText(id.toString());
                    Name.setText(name.toString());
                    Price.setText(price.toString() + "  L.E");
                    if (image != "null") {
                        Picasso.get().load(urlImage + image).into(imageView);

                    } else {
                        imageView.setImageResource(R.drawable.back);

                    }

                    progressDialog.dismiss();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });


        requestQueue.add(jsonObjectRequest);
    }


}
