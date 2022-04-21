package com.projekmobile.wihngeheng;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Menu extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MenuAdapter menuAdapter;
    private List<DetailMenu> MenuList;
    private Button btnSteam, btnNoodle, btnFried, btnDrink;
    private EditText search;
    private ImageView menubar, homebar, cartbar, profilebar, imageView4;
    TextView txtLocationMenu;
    private static final String MENU_URL = "http://192.168.1.9/projectmobile/mobile_wihNgeheng/tampilmenu.php";
    DecimalFormat formatter = new DecimalFormat("#,###,###");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        MenuList = new ArrayList<>();
        getMenu();

        btnSteam = findViewById(R.id.btnSteam);
        btnNoodle = findViewById(R.id.btnNoodle);
        btnFried = findViewById(R.id.btnFried);
        btnDrink = findViewById(R.id.btnDrink);
        search = findViewById(R.id.search);
        imageView4 = findViewById(R.id.imageView4);
        txtLocationMenu = findViewById(R.id.txtLocationMenu);


        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Menu.class);
                startActivity(intent);
            }
        });

        btnSteam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.setAdapter(null);
                MenuList.clear();

                getMenuDetail("Steam");
            }
        });

        btnNoodle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.setAdapter(null);
                MenuList.clear();

                getMenuDetail("Noodle");
            }
        });

        btnFried.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.setAdapter(null);
                MenuList.clear();

                getMenuDetail("Fried");
            }
        });

        btnDrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.setAdapter(null);
                MenuList.clear();

                getMenuDetail("Drink");
            }
        });

//        search by menu_name
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                recyclerView.setAdapter(null);
                MenuList.clear();

                StringRequest stringRequest = new StringRequest(Request.Method.GET, MENU_URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONArray array = new JSONArray(response);
                                    for (int i = 0; i < array.length(); i++) {

                                        JSONObject object = array.getJSONObject(i);

                                        String menu_name = object.getString("menu_name");
                                        String menu_description = object.getString("menu_description");
                                        String menu_price = formatter.format(Integer.parseInt(object.getString("menu_price")));
                                        String menu_image = object.getString("menu_image");

                                        DetailMenu detailMenu = new DetailMenu(menu_name, menu_description, menu_price, menu_image);
                                        if (containsIgnoreCase(menu_name, search.getText().toString())){
                                            MenuList.add(detailMenu);
                                        }
                                    }
                                } catch (JSONException e) {
                                }
                                menuAdapter = new MenuAdapter(Menu.this, MenuList);
                                recyclerView.setAdapter(menuAdapter);
                                }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(Menu.this, error.toString(), Toast.LENGTH_LONG).show();
                            }
                        });
                        Volley.newRequestQueue(Menu.this).add(stringRequest);
                        setrecyclerview(MenuList);
            }
        });

        homebar = findViewById(R.id.homebar);
        menubar = findViewById(R.id.menubar);
        cartbar = findViewById(R.id.orderbar);
        profilebar = findViewById(R.id.profilebar);
        Intent intent = getIntent();
        String valueDapat = intent.getStringExtra("selectedItemText");
        txtLocationMenu.setText(valueDapat);

        homebar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Home.class);
                String value = txtLocationMenu.getText().toString();
                intent.putExtra("selectedItemText", value);
                startActivity(intent);
            }
        });

        menubar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Menu.class);
                String value = txtLocationMenu.getText().toString();
                intent.putExtra("selectedItemText", value);
                startActivity(intent);
            }
        });

        cartbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Cart.class);
                String value = txtLocationMenu.getText().toString();
                intent.putExtra("selectedItemText", value);
                startActivity(intent);
            }
        });

        profilebar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Profile.class);
                String value = txtLocationMenu.getText().toString();
                intent.putExtra("selectedItemText", value);
                startActivity(intent);
            }
        });
    }

    private void getMenu() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, MENU_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i <= array.length(); i++) {

                                JSONObject object = array.getJSONObject(i);
                                String menu_category = object.getString("menu_category");
                                String menu_name = object.getString("menu_name");
                                String menu_description = object.getString("menu_description");
                                String menu_price = formatter.format(Integer.parseInt(object.getString("menu_price")));
                                String menu_image = object.getString("menu_image");

                                DetailMenu detailMenu = new DetailMenu(menu_name, menu_description, menu_price, menu_image);
                                MenuList.add(detailMenu);

                            }
                        } catch (JSONException e) {

                        }
                        menuAdapter = new MenuAdapter(Menu.this, MenuList);
                        recyclerView.setAdapter(menuAdapter);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Menu.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        Volley.newRequestQueue(Menu.this).add(stringRequest);
        setrecyclerview(MenuList);
    }

    private void getMenuDetail(String pilih) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, MENU_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {

                                JSONObject object = array.getJSONObject(i);

                                String menu_category = object.getString("menu_category");
                                String menu_name = object.getString("menu_name");
                                String menu_description = object.getString("menu_description");
                                String menu_price = formatter.format(Integer.parseInt(object.getString("menu_price")));
                                String menu_image = object.getString("menu_image");

                                DetailMenu detailMenu = new DetailMenu(menu_name, menu_description, menu_price, menu_image);

                                if (menu_category.equalsIgnoreCase(pilih)){
                                    MenuList.add(detailMenu);
                                }

                            }
                        } catch (JSONException e) {

                        }
                        menuAdapter = new MenuAdapter(Menu.this, MenuList);
                        recyclerView.setAdapter(menuAdapter);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Menu.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        Volley.newRequestQueue(Menu.this).add(stringRequest);
        setrecyclerview(MenuList);
    }

    private void setrecyclerview(List<DetailMenu> MenuList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        menuAdapter = new MenuAdapter(this, MenuList);
        recyclerView.setAdapter(menuAdapter);
    }

    public static boolean containsIgnoreCase(String str, String searchStr)     {
        if(str == null || searchStr == null) return false;

        final int length = searchStr.length();
        if (length == 0)
            return true;

        for (int i = str.length() - length; i >= 0; i--) {
            if (str.regionMatches(true, i, searchStr, 0, length))
                return true;
        }
        return false;
    }

}

