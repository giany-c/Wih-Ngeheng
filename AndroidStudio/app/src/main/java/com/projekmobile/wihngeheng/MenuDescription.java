package com.projekmobile.wihngeheng;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class MenuDescription extends AppCompatActivity {

    Button btnAddToCart;
    ImageView imgkurang, imgtambah, imagecart;
    TextView txtJumlah, txtLocationMenuDescription;
    private CartHandler cartHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_description);

        Intent i = getIntent();
        String menu_name, menu_description, menu_price, menu_image;
        TextView txtMenuName, txtMenuDesc, txtMenuPrice;
        ImageView imageOrder;

        txtMenuName = findViewById(R.id.txtMenuName);
        txtMenuDesc = findViewById(R.id.txtMenuDesc);
        txtMenuPrice = findViewById(R.id.txtMenuPrice);
        txtLocationMenuDescription = findViewById(R.id.txtLocationMenuDescription);
        imageOrder = findViewById(R.id.imageOrder);
        btnAddToCart = findViewById(R.id.btnAddToCart);
        imagecart = findViewById(R.id.imageCart);

        menu_image = i.getStringExtra("menu_image");
        menu_name = i.getStringExtra("menu_name");
        menu_description = i.getStringExtra("menu_description");
        menu_price = i.getStringExtra("menu_price");

        Log.d("tes", "onCreate: " + menu_image);

        cartHandler = new CartHandler(this);

        try {
            cartHandler.open();
        } catch (SQLException e){
            e.printStackTrace();
        }

        Glide.with(getApplicationContext())
                .load(menu_image)
                .into(imageOrder);

        txtMenuName.setText(menu_name);
        txtMenuDesc.setText(menu_description);
        txtMenuPrice.setText(menu_price);

        imgkurang = findViewById(R.id.imgkurang);
        imgtambah = findViewById(R.id.imgtambah);
        txtJumlah = findViewById(R.id.txtJumlah);

        imgtambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView view = (TextView) findViewById(R.id.txtJumlah);
                String text = view.getText().toString();
                int n = Integer.parseInt(text);
                n++;
                String cur = String.valueOf(n);

                view.setText(cur);
            }
        });

        imgkurang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView view = (TextView) findViewById(R.id.txtJumlah);
                String text = view.getText().toString();
                int n = Integer.parseInt(text);
                if (n == 0) {
                    Toast.makeText(getApplicationContext(), "Tidak boleh kurang dari 0", Toast.LENGTH_LONG).show();
                } else {
                    n--;
                }

                String cur = String.valueOf(n);

                view.setText(cur);
            }
        });

        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartHandler.createCart(menu_name, menu_description, menu_price, menu_image, txtJumlah.getText().toString());
                Intent i = new Intent(getApplicationContext(),Cart.class);
                String value = txtLocationMenuDescription.getText().toString();
                i.putExtra("selectedItemText", value);
                startActivity(i);
            }
        });

        imagecart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),Cart.class);
                startActivity(i);
            }
        });

        Intent intent = getIntent();
        String valueDapat = intent.getStringExtra("selectedItemText");
        txtLocationMenuDescription.setText(valueDapat);

    }
}