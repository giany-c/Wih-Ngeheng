package com.projekmobile.wihngeheng;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.MenuViewHolder>{

    private Context context;
    private List<DetailFav> FavList;

    public FavAdapter(Context context, List<DetailFav> FavList) {

        this.context = context;
        this.FavList = FavList;
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.fav_item, parent, false);
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {

        final DetailFav detailFav = FavList.get(position);

        holder.menu_name.setText(FavList.get(position).getMenu_name());
        Glide.with(context)
                .load(detailFav.getMenu_image())
                .into(holder.menu_image);
        holder.menu_description.setText(FavList.get(position).getMenu_description());
        holder.menu_price.setText(FavList.get(position).getMenu_price());
        holder.imageFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), MenuDescription.class);
                i.putExtra("menu_image", detailFav.getMenu_image());
                i.putExtra("menu_name", holder.menu_name.getText().toString());
                i.putExtra("menu_description", holder.menu_description.getText().toString());
                i.putExtra("menu_price", holder.menu_price.getText().toString());

                v.getContext().startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return FavList.size();
    }

    public class MenuViewHolder extends RecyclerView.ViewHolder {

        private TextView menu_name;
        private ImageView menu_image;
        private TextView menu_description;
        private TextView menu_price;
        private ImageView imageFav;


        public MenuViewHolder(View view) {
            super(view);

            menu_name = view.findViewById(R.id.txtFav);
            menu_image = view.findViewById(R.id.imageFav);
            menu_description = view.findViewById(R.id.txtDescFav);
            menu_price = view.findViewById(R.id.txtPriceFav);
            imageFav = view.findViewById(R.id.imageFav);

        }
    }
}

