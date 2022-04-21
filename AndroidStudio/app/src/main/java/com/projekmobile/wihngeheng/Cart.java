package com.projekmobile.wihngeheng;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

public class Cart extends ListActivity {
    ImageView menubar, homebar, cartbar, profilebar,logowih;
    private CartHandler cartHandler;
    private ArrayList<CartDetail> values, getAllElements;
    private List<DetailOrder> DetailList;
    private RecyclerView rvOrder;
    private OrderAdapter orderAdapter;
    private ListView list;
    Context context = this;
    Button btncheckout;
    TextView txtitem, txttotal, tulisanjudul, tulisanitem, tulisantotal, tulisanrp,tulisanyourcart;
    DecimalFormat formatter = new DecimalFormat("#,###,###");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        txtitem = findViewById(R.id.txtitem);
        txttotal = findViewById(R.id.txttotal);
        tulisanjudul = findViewById(R.id.txtJudul);
        tulisanitem = findViewById(R.id.items);
        tulisantotal = findViewById(R.id.total);
        tulisanrp = findViewById(R.id.textView8);
        tulisanyourcart = findViewById(R.id.txtyourcart);
        btncheckout = findViewById(R.id.btncheckout);
        logowih = findViewById(R.id.imageView4);
        rvOrder = findViewById(R.id.rvOrder);
        DetailList = new ArrayList<>();

        cartHandler = new CartHandler(this);

        try {
            cartHandler.open();
        } catch (SQLException e){
            e.printStackTrace();
        }

        values = cartHandler.getAllNotes();

        ArrayAdapter<CartDetail> adapter = new ArrayAdapter<CartDetail>(this,android.R.layout.simple_list_item_1,values);

        setListAdapter(adapter);
        list = findViewById(android.R.id.list);

        final int a = cartHandler.getProfilesCount();

        txtitem.setText(String.valueOf(a));
        gettotal();


        for(int i = 0; i <= a-1 ; i++){
            final CartDetail log = (CartDetail) getListAdapter().getItem(i);
            final String name = log.get_name();
            CartDetail Name = cartHandler.getCartDetail(name);
            final String stringname = Name.get_name();
            final String stringdesc = Name.get_desc();
            final String stringprice = Name.get_price();
            final String stringimage = Name.get_image();
            final String stringqty = Name.get_qty();
            DetailOrder detailOrder = new DetailOrder(stringname, stringdesc, stringprice, stringimage, stringqty);
            DetailList.add(detailOrder);

        }

        orderAdapter = new OrderAdapter(Cart.this, DetailList);
        rvOrder.setAdapter(orderAdapter);

        //atur font dan format
        btncheckout.setBackgroundColor(Cart.this.getResources().getColor(R.color.kuning));
        Typeface fontbold = Typeface.createFromAsset(getAssets(), "Quicksand-Bold.ttf");
        Typeface fontmedium = Typeface.createFromAsset(getAssets(), "Quicksand-Medium.ttf");
        btncheckout.setTypeface(fontbold);
        tulisanjudul.setTypeface(fontbold);
        tulisanitem.setTypeface(fontmedium);
        tulisantotal.setTypeface(fontmedium);
        txttotal.setTypeface(fontmedium);
        tulisanrp.setTypeface(fontmedium);
        txtitem.setTypeface(fontmedium);
        tulisanyourcart.setTypeface(fontbold);

        setrecyclerview(DetailList);

        String selectedspinner = getIntent().getStringExtra("selectedspinner");

        Intent intent = getIntent();
        String selectedItemText = intent.getStringExtra("selectedItemText");
        intent.putExtra("selectedItemText", selectedItemText);

        btncheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a = cartHandler.getProfilesCount();
                String j = "";
                for(int i = 0; i <= a-1 ; i++) {
                    final CartDetail log = (CartDetail) getListAdapter().getItem(i);
                    final String name = log.get_name();
                    CartDetail Name = cartHandler.getCartDetail(name);
                    final String stringname = Name.get_name();
                    final String stringqty = Name.get_qty();
                    if( i==a-1){
                        j=j+stringqty + " " + stringname;
                    } else {
                        j=j+stringqty + " " + stringname + ", ";
                    }
                }
                Log.d("tag", "onCreate:  "+ j );
                String txttotalbanget = txttotal.getText().toString();
                if(txttotalbanget.equals("0")){
                    Toast.makeText(Cart.this, "You haven't ordered anything yet", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(Cart.this, FormOrder.class);
                    intent.putExtra("notes",j);
                    intent.putExtra("selectedItemText", selectedItemText);
                    intent.putExtra("txttotalbanget",txttotalbanget);
                    startActivity(intent);
                }
            }
        });
    }

    public void gettotal(){
        try {
            cartHandler.open();
        } catch (SQLException e){
            e.printStackTrace();
        }

        int a = cartHandler.getProfilesCount();
        int total = 0;

        for(int i = 0; i <= a-1 ; i++){
            final CartDetail log = (CartDetail) getListAdapter().getItem(i);
            final String name = log.get_name();
            CartDetail Name = cartHandler.getCartDetail(name);
            final String stringprice = Name.get_price();
            final String stringqty = Name.get_qty();

            int harga = Integer.parseInt(stringprice.replaceAll(",", ""));
            int kuantitas = Integer.parseInt(stringqty);
            int subtotal = harga * kuantitas;

            total = total + subtotal;
        }
        txttotal.setText(formatter.format(total));
    }

    private void setrecyclerview(List<DetailOrder> DetailList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvOrder.setLayoutManager(layoutManager);
        orderAdapter = new OrderAdapter(this, DetailList);
        rvOrder.setAdapter(orderAdapter);
    }
}