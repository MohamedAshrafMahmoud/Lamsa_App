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
import com.example.mohamed.myapplication.ViewHolders.Tab1_2Adapter;
import com.example.mohamed.myapplication.ViewHolders.Tab2Adapter;
import com.example.mohamed.myapplication.models.second;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Tab2 extends Fragment {

    ArrayList<second> array = new ArrayList<second>();
    RecyclerView recyclerView;
    String id;
    String url;

    Tab2Adapter adabter;
    RequestQueue requestQueue;

    SwipeRefreshLayout swipeRefreshLayout;


    public Tab2() {
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.tab2, container, false);

        recyclerView = (RecyclerView)view. findViewById(R.id.recyclelisttab2);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_Layout);
        swipeRefreshLayout.setColorSchemeColors(R.color.colorPrimary, android.R.color.holo_green_dark);

        url = "http://lamsa.pioneers-solutions.org/Api/Helper/GetAllLences";

        load2();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                load2();
                swipeRefreshLayout.setRefreshing(false);

            }
        });

        return view;
    }

    void load2() {

        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("please wait ....");
        progressDialog.show();

        Toast.makeText(getContext(), "load", Toast.LENGTH_SHORT).show();
        array=new ArrayList<>();
        requestQueue = Volley.newRequestQueue(getContext());

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    Toast.makeText(getContext(), "call", Toast.LENGTH_SHORT).show();
                    try {
                        JSONObject object = response.getJSONObject(i);

                        String id = object.getInt("Id") + "";
                        String name = object.getString("Name");
                        String image = object.getString("Image");
                        String price = object.getString("Price");


                        array.add(new second(id, name, image, price));
                        adabter = new Tab2Adapter(getContext(), array);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        recyclerView.setAdapter(adabter);


                        progressDialog.dismiss();

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(), ""+e.toString(), Toast.LENGTH_SHORT).show();

                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), ""+error.toString(), Toast.LENGTH_SHORT).show();

            }
        });
        requestQueue.add(jsonObjectRequest);
    }


}
