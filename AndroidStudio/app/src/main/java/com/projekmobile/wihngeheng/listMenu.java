package com.projekmobile.wihngeheng;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class listMenu extends AppCompatActivity{
    ListView daftarMenu;
    ArrayList<HashMap<String, String>> list_menu;
    String menu_id, menu_name, menu_category, menu_price, menu_description, menu_image;
    String URL_LIST_MENU = "http://192.168.1.9/projectmobile/mobile_wihNgeheng/getMenu.php";
    //    menu_id, menu_name, menu_category, menu_price, menu_description, menu_image
    private static final String TAG_MENU = "dataMenu";
    private static final String TAG_ID = "menu_id";
    private static final String TAG_NAME = "menu_name";
    private static final String TAG_CATEGORY = "menu_category";
    private static final String TAG_PRICE = "menu_price";
    private static final String TAG_DESCRIPTION = "menu_description";
    private static final String TAG_IMAGE = "menu_image";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_menu);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        FloatingActionButton fabMenu = findViewById(R.id.fabMenu);
        fabMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(listMenu.this, addMenu.class);
                startActivity(i);
            }
        });

        list_menu = new ArrayList<>();
        daftarMenu = findViewById(R.id.listMenu);

        RequestQueue queue = Volley.newRequestQueue(listMenu.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LIST_MENU, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray menu = jsonObject.getJSONArray(TAG_MENU);
                    for (int i=0;i<menu.length(); i++){
                        JSONObject a = menu.getJSONObject(i);
                        menu_id = a.getString(TAG_ID);
                        menu_name = a.getString(TAG_NAME);
                        menu_category = a.getString(TAG_CATEGORY);
                        menu_price = a.getString(TAG_PRICE);
                        menu_description = a.getString(TAG_DESCRIPTION);
                        menu_image = a.getString(TAG_IMAGE);

                        double hargaMenu = Double.parseDouble(menu_price);
                        Locale locale = new Locale("in", "ID");
                        NumberFormat rupiah = NumberFormat.getCurrencyInstance(locale);
                        String formatHargaMenu = rupiah.format(hargaMenu);

                        HashMap<String, String> map = new HashMap<>();
                        map.put("menu_id", menu_id);
                        map.put("menu_name", menu_name);
                        map.put("menu_category", menu_category);
                        map.put("menu_price", menu_price);
                        map.put("formatHargaMenu", formatHargaMenu);
                        map.put("menu_description", menu_description);
                        map.put("menu_image", menu_image);

                        list_menu.add(map);

                        String[] from = {"menu_id", "menu_name", "menu_category", "menu_price", "formatHargaMenu", "menu_description", "menu_image"};
                        int[] to = {R.id.txtMenuID, R.id.txtMenuName, R.id.txtMenuCategory, R.id.txtMenuPrice, R.id.txtMenuFormatPrice , R.id.txtMenuDesc, R.id.txtMenuImage};

                        ListAdapter adapter = new SimpleAdapter(listMenu.this,
                                list_menu, R.layout.isi_list_menu,from ,to);
                        daftarMenu.setAdapter(adapter);
                    }

                } catch (JSONException e) {
                    Toast.makeText(listMenu.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(listMenu.this, "Cek koneksi anda terlebih dahulu!", Toast.LENGTH_LONG).show();
            }
        });
//        {
//            @Nullable
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("menu_id", menu_id);
//                return params;
//            }
//        };
        queue.getCache().clear();
        queue.add(stringRequest);

        daftarMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String getID = ((TextView)view.findViewById(R.id.txtMenuID)).getText().toString();
                String getMenuName = ((TextView)view.findViewById(R.id.txtMenuName)).getText().toString();
                String getMenuCategory = ((TextView)view.findViewById(R.id.txtMenuCategory)).getText().toString();
                String getMenuPrice = ((TextView)view.findViewById(R.id.txtMenuPrice)).getText().toString();
                String getMenuDesc = ((TextView)view.findViewById(R.id.txtMenuDesc)).getText().toString();
                String getMenuImage = ((TextView)view.findViewById(R.id.txtMenuImage)).getText().toString();

                Intent i = new Intent(listMenu.this, editMenu.class);
                i.putExtra("menu_id", getID);
                i.putExtra("menu_name", getMenuName);
                i.putExtra("menu_category", getMenuCategory);
                i.putExtra("menu_price", getMenuPrice);
                i.putExtra("menu_description", getMenuDesc);
                i.putExtra("menu_image", getMenuImage);
                startActivity(i);
            }
        });

    }


    public void homeAdminBar(View view) {
        Intent i = new Intent(this, AdminHome.class);
        startActivity(i);
        finish();
    }

    public void menuAdminBar(View view) {
        Intent i = new Intent(this, listMenu.class);
        startActivity(i);
        finish();
    }

    public void employeeAdminBar(View view) {
        Intent i = new Intent(this, Employee.class);
        startActivity(i);
        finish();
    }

    public void locationAdminBar(View view) {
        Intent i = new Intent(this, listLocation.class);
        startActivity(i);
        finish();
    }

    public void orderAdminBar(View view) {
        Intent i = new Intent(this, orderAdmin.class);
        startActivity(i);
        finish();
    }

    public void profileAdminBar(View view) {
        Intent i = new Intent(this, AdminProfile.class);
        startActivity(i);
        finish();
    }

}
