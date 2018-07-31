package com.example.mohamed.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mohamed.myapplication.Common.Common;
import com.example.mohamed.myapplication.ViewHolders.Tab1_2Adapter;
import com.example.mohamed.myapplication.models.second;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Tab2_1 extends AppCompatActivity {

    TextView Name, Price, Id;
    ImageView imageView, fav;
    String id;
    String url;
    String urlImage;


    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab2_1);

        Id = (TextView) findViewById(R.id.id);
        Name = (TextView) findViewById(R.id.Name);
        Price = (TextView) findViewById(R.id.Price);
        imageView = (ImageView) findViewById(R.id.image2);
        fav = (ImageView) findViewById(R.id.fav1);

        id = getIntent().getStringExtra("idsecond");

        url = "http://lamsa.pioneers-solutions.org/Api/Helper/GetLences?id=" + id;
        urlImage = "http://lamsa.pioneers-solutions.org/";

//        if (db.isFavorite(Name.getText().toString()))
//            fav.setImageResource(R.drawable.ic_favorite_black_24dp);

            fav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if (Name.getText().toString() == "" || Name.getText().toString() == null) {
                        Toast.makeText(Tab2_1.this, "loading...", Toast.LENGTH_SHORT).show();
                    } else {

                        // Bitmap bm=((BitmapDrawable)imageView.getDrawable()).getBitmap();

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
//                            Toast.makeText(Tab2_1.this, "Added to favorites", Toast.LENGTH_SHORT).show();
//                        } else {
//                            db.deleteFromFavorite(Name.getText().toString());
//                            fav.setImageResource(R.drawable.ic_favorite_border_black_24dp);
//                            Toast.makeText(Tab2_1.this, "Removed from favorites", Toast.LENGTH_SHORT).show();
//                        }
                    }
                }
            });


            load();

        }

    void load() {

        final ProgressDialog progressDialog = new ProgressDialog(Tab2_1.this);
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
                        String price = object.getInt("Price")+"";

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
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest);
    }


    }
