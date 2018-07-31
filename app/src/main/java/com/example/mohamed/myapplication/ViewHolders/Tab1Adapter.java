package com.example.mohamed.myapplication.ViewHolders;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.mohamed.myapplication.R;
import com.example.mohamed.myapplication.Tab1_2;
import com.example.mohamed.myapplication.models.first;
import com.example.mohamed.myapplication.models.second;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by 7aSSan on 4/20/2018.
 */

public class Tab1Adapter extends RecyclerView.Adapter<Tab1Adapter.ViewHolder> {



    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,id;
        ImageView imageView;
        String url = "http://lamsa.pioneers-solutions.org/";
         ArrayList<second> array = new ArrayList<second>();
        RecyclerView recyclerView;
        String url2;

        Tab1_2Adapter adabter;
        RequestQueue requestQueue;



        public ViewHolder(View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.id);
            name = itemView.findViewById(R.id.name);
            imageView = itemView.findViewById(R.id.image);

            recyclerView = (RecyclerView)itemView.findViewById(R.id.recyclelist);




        }

    }




    public Context context;
    ArrayList<first> items = new ArrayList<>();

    public Tab1Adapter(Context context, ArrayList<first> listitem) {
        this.context = context;
        this.items = listitem;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.item_tab1, null);
        return new Tab1Adapter.ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {


        holder.id.setText(items.get(position).getId());
        holder.name.setText(items.get(position).getName());

        if (items.get(position).getImage()=="null"){
            holder.imageView.setImageResource(R.drawable.back);
        }else {
            Picasso.get().load(holder.url + items.get(position).getImage()).into(holder.imageView);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, Tab1_2.class);
                i.putExtra("id", items.get(position).getId());
                context.startActivity(i);
            }
        });


        holder.url2 = "http://lamsa.pioneers-solutions.org/Api/Helper/GetServicesOfCategories?id="+holder.id.getText().toString();

           holder.requestQueue = Volley.newRequestQueue(context);

            JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, holder.url2, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject object = response.getJSONObject(i);

                            String id = object.getInt("Id") + "";
                            String name = object.getString("Name");
                            String image = object.getString("Image");
                            String price = object.getInt("price")+"";


                           holder.array.add(new second(id, name, image, price));
                           holder.adabter = new Tab1_2Adapter(context, holder.array);
                            holder.recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
                            holder.recyclerView.setAdapter(holder.adabter);

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
            holder.requestQueue.add(jsonObjectRequest);
        }





    @Override
    public int getItemCount() {
        return items.size();
    }


}
