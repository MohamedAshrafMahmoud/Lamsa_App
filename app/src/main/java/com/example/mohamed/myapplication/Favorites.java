package com.example.mohamed.myapplication;

import android.annotation.SuppressLint;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.mohamed.myapplication.ViewHolders.FavAdapter;
import com.example.mohamed.myapplication.models.second;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Favorites extends AppCompatActivity {


    RecyclerView recycler_Favorite;
    TextView textView;
    public static FavAdapter adapter;
    RequestQueue requestQueue;
    ArrayList<second> arrayList=new ArrayList<>();
    SwipeRefreshLayout swipeRefreshLayout;



    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorites);

        textView = (TextView) findViewById(R.id.empty_view);
        recycler_Favorite = (RecyclerView) findViewById(R.id.recyclerfav);
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_Layout);
        swipeRefreshLayout.setColorSchemeColors(R.color.colorPrimary, android.R.color.holo_green_dark);
        recycler_Favorite.setLayoutManager(new LinearLayoutManager(this));

//        if (arrayList.isEmpty()) {
//            recycler_Favorite.setVisibility(View.GONE);
//            textView.setVisibility(View.VISIBLE);
//        } else {
//            recycler_Favorite.setVisibility(View.VISIBLE);
//            textView.setVisibility(View.GONE);
//        }


        loadList();


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadList();
                swipeRefreshLayout.setRefreshing(false);

            }
        });

    }

    private void loadList() {
        arrayList=new ArrayList<>();
        String url = "http://lamsa.pioneers-solutions.org/Api/Helper/GetFavorite?UserId=1021";
        requestQueue = Volley.newRequestQueue(Favorites.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = null;
                        try {
                            object = response.getJSONObject(i);

                            arrayList.add(new second(
                                    object.getInt("Id") + ""
                                    , object.getString("Name")
                                    , object.getString("Image")
                                    , object.getString("price") ));
                            adapter=new FavAdapter(Favorites.this,arrayList);
                            recycler_Favorite.setAdapter(adapter);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } catch (Exception e) {
                        e.getMessage();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError) {
                    Toast.makeText(Favorites.this, "Check Internet Connection!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(Favorites.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        Volley.newRequestQueue(Favorites.this).add(jsonArrayRequest);


    }
}
