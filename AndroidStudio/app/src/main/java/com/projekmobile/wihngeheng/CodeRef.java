package com.projekmobile.wihngeheng;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CodeRef extends AppCompatActivity {

    TextView txtrefcode;
    Button btnbackhome;
    private CartHandler cartHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_ref);
        getSupportActionBar().hide();
        txtrefcode = findViewById(R.id.txtrefcode);
        btnbackhome = findViewById(R.id.btnbackhome);
        String coderef;

        Intent i = getIntent();
        coderef = i.getStringExtra("coderef");
        txtrefcode.setText(coderef);

        cartHandler = new CartHandler(this);

        try {
            cartHandler.open();
        } catch (SQLException e){
            e.printStackTrace();
        }

        btnbackhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Home.class);
                startActivity(intent);

                cartHandler.deleteAll();
            }
        });

    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Intent intent = new Intent(getApplicationContext(), Home.class);
            startActivity(intent);

            cartHandler.deleteAll();
        }
        return super.onKeyDown(keyCode, event);
    }
}