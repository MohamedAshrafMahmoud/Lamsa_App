package com.example.mohamed.myapplication.ViewHolders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mohamed.myapplication.Favorites;
import com.example.mohamed.myapplication.Common.ItemClickListner;
import com.example.mohamed.myapplication.R;
import com.example.mohamed.myapplication.models.second;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by 7aSSan on 4/20/2018.
 */

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.ViewHolder> {


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name, id, price;
        ImageView imageView, favo;
        private ItemClickListner itemClickListner;
        String url = "http://lamsa.pioneers-solutions.org/";


        public ViewHolder(View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.id);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            imageView = itemView.findViewById(R.id.image);
            favo = itemView.findViewById(R.id.fav);

        }

        public void setItemClickListner(ItemClickListner itemClickListner) {
            this.itemClickListner = itemClickListner;
        }

        @Override
        public void onClick(View v) {
            itemClickListner.onClick(v, getAdapterPosition(), false);
        }
    }


    Context context;
    ArrayList<second> items = new ArrayList<>();

    public FavAdapter(Context context, ArrayList<second> listitem) {
        this.context = context;
        this.items = listitem;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.item_fav, null);
        return new FavAdapter.ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {


        holder.id.setText(items.get(position).getId());
        holder.name.setText(items.get(position).getName());
        holder.price.setText(items.get(position).getPrice()+" L.E");



        if (items.get(position).getImage()!="null"){
            Picasso.get().load(holder.url + items.get(position).getImage()).into(holder.imageView);

        }else {
            holder.imageView.setImageResource(R.drawable.back);

        }



        holder.favo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                items.remove(position);
                Favorites.adapter.notifyItemRemoved(position);


                holder.favo.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                Toast.makeText(context, "Removed from favorites", Toast.LENGTH_SHORT).show();


            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }


}
