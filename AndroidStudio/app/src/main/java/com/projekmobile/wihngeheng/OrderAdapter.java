package com.projekmobile.wihngeheng;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder>{
    private Context context;
    private List<DetailOrder> OrderList;
    private CartHandler cartHandler;


    public OrderAdapter(Context context, List<DetailOrder> OrderList) {

        this.context = context;
        this.OrderList = OrderList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.cart_item, parent, false);

        return new OrderViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        final DetailOrder detailOrder = OrderList.get(position);

        holder.name.setText(OrderList.get(position).getName());
        holder.desc.setText(OrderList.get(position).getDesc());
        holder.price.setText(OrderList.get(position).getPrice());
        Glide.with(context)
                .load(detailOrder.getImage())
                .into(holder.image);
        holder.qty.setText(OrderList.get(position).getQty());

        holder.btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartHandler = new CartHandler(v.getContext());

                try {
                    cartHandler.open();
                } catch (SQLException e){
                    e.printStackTrace();
                }
                cartHandler.deleteCart(holder.name.getText().toString());
                Intent i = new Intent(v.getContext(),Cart.class);
                String valueDapat = "Muara Karang";
                i.putExtra("selectedItemText",valueDapat);
                v.getContext().startActivity(i);
                ((Activity)context).finish();
            }
        });

        holder.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = holder.qty.getText().toString();
                cartHandler = new CartHandler(v.getContext());
                try {
                    cartHandler.open();
                } catch (SQLException e){
                    e.printStackTrace();
                }

                int n = Integer.parseInt(text);
                if (n == 0) {
                    Toast.makeText(v.getContext(), "Tidak boleh kurang dari 0", Toast.LENGTH_LONG).show();
                } else {
                    n--;
                }

                String cur = String.valueOf(n);

                holder.qty.setText(cur);
                CartDetail cartDetail = new CartDetail();
                cartDetail.set_name(holder.name.getText().toString());
                cartDetail.set_desc(holder.desc.getText().toString());
                cartDetail.set_price(holder.price.getText().toString());
                cartDetail.set_image(detailOrder.getImage());
                cartDetail.set_qty(holder.qty.getText().toString());
                Log.d("tag", "onCreate:  "+holder.name.getText().toString());
                Log.d("tag", "onCreate:  "+holder.desc.getText().toString());
                Log.d("tag", "onCreate:  "+holder.price.getText().toString());
                Log.d("tag", "onCreate:  "+detailOrder.getImage());
                Log.d("tag", "onCreate:  "+holder.qty.getText().toString());


                cartHandler.updateCart(cartDetail);

                ((Cart)context).gettotal();
            }
        });

        holder.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = holder.qty.getText().toString();
                cartHandler = new CartHandler(v.getContext());
                try {
                    cartHandler.open();
                } catch (SQLException e){
                    e.printStackTrace();
                }

                int n = Integer.parseInt(text);
                n++;
                String cur = String.valueOf(n);

                holder.qty.setText(cur);
                CartDetail cartDetail = new CartDetail();
                cartDetail.set_name(holder.name.getText().toString());
                cartDetail.set_desc(holder.desc.getText().toString());
                cartDetail.set_price(holder.price.getText().toString());
                cartDetail.set_image(detailOrder.getImage());
                cartDetail.set_qty(holder.qty.getText().toString());
                Log.d("tag", "onCreate:  "+holder.name.getText().toString());
                Log.d("tag", "onCreate:  "+holder.desc.getText().toString());
                Log.d("tag", "onCreate:  "+holder.price.getText().toString());
                Log.d("tag", "onCreate:  "+detailOrder.getImage());
                Log.d("tag", "onCreate:  "+holder.qty.getText().toString());

                cartHandler.updateCart(cartDetail);

                ((Cart)context).gettotal();
            }
        });


    }

    @Override
    public int getItemCount() {
        return OrderList.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {
        private Button btnHapus, btnMinus, btnPlus;
        private TextView name;
        private TextView desc;
        private TextView price;
        private ImageView image;
        private TextView qty;
        private TextView txtJumlah,tulisanrp;

        public OrderViewHolder(View view) {
            super(view);

            txtJumlah = view.findViewById(R.id.txtJumlah);
            btnMinus = view.findViewById(R.id.btnMinus);
            btnPlus = view.findViewById(R.id.btnPlus);
            btnHapus = view.findViewById(R.id.btnHapus);
            name = view.findViewById(R.id.txtMenuO);
            desc = view.findViewById(R.id.txtDescO);
            price = view.findViewById(R.id.txtPriceO);
            image = view.findViewById(R.id.imageorder);
            qty = view.findViewById(R.id.txtJumlah);
            tulisanrp  = view.findViewById(R.id.tulisanrp2);

            Typeface fontbold = Typeface.createFromAsset(itemView.getContext().getAssets(), "Quicksand-Bold.ttf");
            Typeface fontmedium = Typeface.createFromAsset(itemView.getContext().getAssets(), "Quicksand-Medium.ttf");
            name.setTypeface(fontbold);
            desc.setTypeface(fontmedium);
            price.setTypeface(fontbold);
            qty.setTypeface(fontmedium);
            tulisanrp.setTypeface(fontbold);
        }
    }
}
