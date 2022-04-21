package com.projekmobile.wihngeheng;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder>{

    private Context context;
    private List<DetailMenu> MenuList;

    public MenuAdapter(Context context, List<DetailMenu> MenuList) {

        this.context = context;
        this.MenuList = MenuList;
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.menu_item, parent, false);

        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        final DetailMenu detailMenu = MenuList.get(position);

        holder.menu_name.setText(MenuList.get(position).getMenu_name());
        holder.menu_description.setText(MenuList.get(position).getMenu_description());
        holder.menu_price.setText(MenuList.get(position).getMenu_price());

        Glide.with(context)
                .load(detailMenu.getMenu_image())
                .into(holder.menu_image);
        holder.btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), MenuDescription.class);
                i.putExtra("menu_image", detailMenu.getMenu_image());
                i.putExtra("menu_name", holder.menu_name.getText().toString());
                i.putExtra("menu_description", holder.menu_description.getText().toString());
                i.putExtra("menu_price", holder.menu_price.getText().toString());
                String valueDapat = "Muara Karang";
                i.putExtra("selectedItemText",valueDapat);
                v.getContext().startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return MenuList.size();
    }

    public class MenuViewHolder extends RecyclerView.ViewHolder {

        private TextView menu_name;
        private TextView menu_description;
        private TextView menu_price;
        private ImageView menu_image;
        private Button btnDetail;

        public MenuViewHolder(View view) {
            super(view);

            menu_name = view.findViewById(R.id.txtMenu);
            menu_description = view.findViewById(R.id.txtDesc);
            menu_price = view.findViewById(R.id.txtPrice);
            menu_image = view.findViewById(R.id.imageView);
            btnDetail = view.findViewById(R.id.btnDetail);
        }
    }
}
