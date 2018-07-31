package com.example.mohamed.myapplication;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.mohamed.myapplication.ViewHolders.Tab1_2Adapter;
import com.example.mohamed.myapplication.models.second;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Tab1_2 extends AppCompatActivity {

    ArrayList<second> array = new ArrayList<second>();
    RecyclerView recyclerView;
    String id;
    String url;

    Tab1_2Adapter adabter;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab1_2);

        recyclerView = (RecyclerView) findViewById(R.id.recyclelist2);

        id = getIntent().getStringExtra("id");
        url = "http://lamsa.pioneers-solutions.org/Api/Helper/GetServicesOfCategories?id=" + id;


        load();

    }


    void load() {

        final ProgressDialog progressDialog = new ProgressDialog(Tab1_2.this);
        progressDialog.setMessage("please wait ....");
        progressDialog.show();


        requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);

                        String id = object.getInt("Id") + "";
                        String name = object.getString("Name");
                        String image = object.getString("Image");
                        String price = object.getInt("price")+"";


                        array.add(new second(id, name, image, price));
                        adabter = new Tab1_2Adapter(Tab1_2.this, array);
                        recyclerView.setLayoutManager(new LinearLayoutManager(Tab1_2.this));
                        recyclerView.setAdapter(adabter);

                        progressDialog.dismiss();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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