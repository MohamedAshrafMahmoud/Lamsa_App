package com.example.mohamed.myapplication;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.mohamed.myapplication.Common.Common;
import com.example.mohamed.myapplication.ViewHolders.Tab1Adapter;
import com.example.mohamed.myapplication.models.first;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Tab1 extends Fragment {

    ArrayList<first> array = new ArrayList<first>();
    RecyclerView recyclerView;
    String url = "http://lamsa.pioneers-solutions.org/Api/Helper/GetCategories";

    Tab1Adapter adabter;
    RequestQueue requestQueue;
    SwipeRefreshLayout swipeRefreshLayout;



    public Tab1() {
    }


    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.tab1, container, false);


        recyclerView = (RecyclerView)view.findViewById(R.id.recyclelist);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_Layout);
        swipeRefreshLayout.setColorSchemeColors(R.color.colorPrimary, android.R.color.holo_green_dark);

        if (Common.isConnectToInternet(view.getContext())) {

            load();

            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    load();
                    swipeRefreshLayout.setRefreshing(false);

                }
            });

        } else {
            Toast.makeText(getContext(), "Check internet connection", Toast.LENGTH_SHORT).show();
        }

        return view;
    }
    void load() {

        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("please wait ....");
        progressDialog.show();

        array=new ArrayList<>();
        requestQueue = Volley.newRequestQueue(getContext());
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//                            //array.clear();
//                            JSONArray jsonArray = response.getJSONArray("Description");
//                            for (int i = 0; i < jsonArray.length(); i++) {
//                                JSONObject getData = jsonArray.getJSONObject(i);
//
//                                String id = getData.getString("Id");
//                                String name = getData.getString("Name");
//                                String image = getData.getString("Image");
//
//                                array.add(new first(id, name, image));
//
//
//                            }
//
//                            adabter = new Tab1Adapter(First.this, array);
//                            recyclerView.setLayoutManager(new LinearLayoutManager(First.this));
//                            recyclerView.setAdapter(adabter);
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//                Log.e("VOLLEY", "Errrrrrrrrrrrrrrrrrrrrrrorrr");
//                error.printStackTrace();
//
//            }
//        });
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);

                        String id = object.getInt("Id") + "";
                        String name = object.getString("Name");
                        String image = object.getString("Image");

                        array.add(new first(id, name, image));
                        adabter = new Tab1Adapter(getContext(), array);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
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
        requestQueue.add(jsonArrayRequest);
    }


}
