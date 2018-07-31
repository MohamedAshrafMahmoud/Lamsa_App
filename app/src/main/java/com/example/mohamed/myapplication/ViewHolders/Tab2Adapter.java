package com.example.mohamed.myapplication.ViewHolders;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mohamed.myapplication.R;
import com.example.mohamed.myapplication.Tab1_3;
import com.example.mohamed.myapplication.Tab2_1;
import com.example.mohamed.myapplication.models.second;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by 7aSSan on 4/20/2018.
 */

public class Tab2Adapter extends RecyclerView.Adapter<Tab2Adapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, id, price;
        ImageView imageView;
        String url = "http://lamsa.pioneers-solutions.org/";


        public ViewHolder(View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.id);
            name = itemView.findViewById(R.id.name);
            imageView = itemView.findViewById(R.id.image);
            price = itemView.findViewById(R.id.price);


        }
    }

    Context context;
    ArrayList<second> items = new ArrayList<>();

    public Tab2Adapter(Context context, ArrayList<second> listitem) {
        this.context = context;
        this.items = listitem;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.item_tab2, null);
        return new Tab2Adapter.ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {


        holder.id.setText(items.get(position).getId());
        holder.name.setText(items.get(position).getName());
        holder.price.setText(items.get(position).getPrice() + "  L.E");

        if (items.get(position).getImage()!="null"){
            Picasso.get().load(holder.url + items.get(position).getImage()).into(holder.imageView);

        }else {
            holder.imageView.setImageResource(R.drawable.back);

        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, Tab2_1.class);
                i.putExtra("idsecond", items.get(position).getId());
                context.startActivity(i);
            }
        });


    }

    @Override
    public int getItemCount() {
        return items.size();
    }


}
