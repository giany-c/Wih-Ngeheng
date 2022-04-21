package com.projekmobile.wihngeheng;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Home extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    ImageView menubar, homebar, cartbar, profilebar;
    CardView card1;
    Spinner spinnerLocation;
    ArrayList<String> locationList = new ArrayList<>();
    ArrayAdapter<String> locationAdapter;
    RequestQueue requestQueue;
    SessionManager sessionManager;
    String getId, hello, selectedItemText;
    TextView txtHelloHome, txtLocationHome;

    private RecyclerView recyclerHome;
    private static final String FAV_URL = "http://192.168.1.9/projectmobile/mobile_wihNgeheng/tampilfav.php";
    String URL_READ_PROFILE = "http://192.168.1.9/projectmobile/mobile_wihNgeheng/login/read_detail.php";
    private static final String TAG = Home.class.getSimpleName();
    private List<DetailFav> FavList;
    private FavAdapter favAdapter;
    private RecyclerView.LayoutManager manager;
    DecimalFormat formatter = new DecimalFormat("#,###,###");
    private EditText searchHome;
    private boolean backspace;
    private int lastLength;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        homebar = (ImageView) findViewById(R.id.homebar);
        menubar = (ImageView) findViewById(R.id.menubar);
        cartbar = (ImageView) findViewById(R.id.cartbar);
        profilebar = (ImageView) findViewById(R.id.profilebar);
        txtHelloHome = findViewById(R.id.txtHelloHome);
        txtLocationHome = findViewById(R.id.txtLocationHome);

//        Intent intent = getIntent();
//        String selectedItemText = intent.getStringExtra("selectedItemText");


        homebar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Home.class);
                String value = txtLocationHome.getText().toString();
                intent.putExtra("selectedItemText", value);
                startActivity(intent);
            }
        });

        menubar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Menu.class);
                String value = txtLocationHome.getText().toString();
                intent.putExtra("selectedItemText", value);
                startActivity(intent);
            }
        });

        cartbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Cart.class);
                String value = txtLocationHome.getText().toString();
                intent.putExtra("selectedItemText", value);
                startActivity(intent);
            }
        });

        profilebar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Profile.class);
                String value = txtLocationHome.getText().toString();
                intent.putExtra("selectedItemText", value);
                startActivity(intent);
            }
        });


        //recycler
        recyclerHome = findViewById(R.id.recyclerHome);
        FavList = new ArrayList<>();
        getFav();
        manager = new GridLayoutManager(Home.this, 2);
        recyclerHome.setLayoutManager(manager);

        //search
        searchHome = findViewById(R.id.searchHome);
        searchHome.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                recyclerHome.setAdapter(null);
                FavList.clear();
                StringRequest stringRequest = new StringRequest(Request.Method.GET, FAV_URL,
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
                                        DetailFav detailFav = new DetailFav(menu_name, menu_image, menu_description, menu_price);
                                        if (containsIgnoreCase(menu_name, searchHome.getText().toString())) {
                                            FavList.add(detailFav);
                                        }
                                    }
                                } catch (JSONException e) {

                                }
                                favAdapter = new FavAdapter(Home.this, FavList);
                                recyclerHome.setAdapter(favAdapter);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Home.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                });
                Volley.newRequestQueue(Home.this).add(stringRequest);
                setrecyclerview(FavList);
            }
        });

        //slider
        ImageSlider imageSlider = findViewById(R.id.slider);
        List<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.slidesp, "Enjoy 20% off (deals within 09.00-15.00 WIB)"));
        slideModels.add(new SlideModel(R.drawable.slidesp2, "TUESDAY DEALS : ALL DIMSUM IDR 20.000!"));
        imageSlider.setImageList(slideModels, true);

        //location
        requestQueue = Volley.newRequestQueue(this);
        spinnerLocation = findViewById(R.id.spinner);

        String url = "http://192.168.1.9/projectmobile/mobile_wihNgeheng/location/populate_location.php";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("location");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String locationName = jsonObject.optString("location_name");
                        locationList.add(locationName);
                        locationAdapter = new ArrayAdapter<>(Home.this,
                                android.R.layout.simple_spinner_item, locationList);
                        locationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerLocation.setAdapter(locationAdapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest);
        spinnerLocation.setOnItemSelectedListener(this);

        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();
        HashMap<String, String> user = sessionManager.getUserDetail();
        getId = user.get(sessionManager.ID);
    }

    private void getUserDetail() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_READ_PROFILE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.i(TAG, response.toString());
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("read");

                            if (success.equals("1")) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    //String strId = object.getString("user_id");
                                    String strName = object.getString("user_name");
                                    hello = "Hello, " + strName + "!";
                                    txtHelloHome.setText(hello);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(Home.this, "Error reading data" + e.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Home.this, "Error reading data" + error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", getId);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUserDetail();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedItemText = (String) parent.getItemAtPosition(position);
        txtLocationHome.setText(selectedItemText);
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    private void getFav(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, FAV_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);
                                String menu_name = object.getString("menu_name");
                                String menu_image = object.getString("menu_image");
                                String menu_description = object.getString("menu_description");
                                String menu_price = formatter.format(Integer.parseInt(object.getString("menu_price")));
                                DetailFav detailFav = new DetailFav(menu_name, menu_image, menu_description, menu_price);
                                FavList.add(detailFav);
                            }
                        } catch (JSONException e) {
                        }
                        favAdapter = new FavAdapter(Home.this, FavList);
                        recyclerHome.setAdapter(favAdapter);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Home.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        Volley.newRequestQueue(Home.this).add(stringRequest);
        setrecyclerview(FavList);
    }

    private void setrecyclerview(List<DetailFav> FavList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false);
        recyclerHome.setLayoutManager(layoutManager);
        favAdapter = new FavAdapter(this, FavList);
        recyclerHome.setAdapter(favAdapter);

        manager = new GridLayoutManager(Home.this, 2);
        recyclerHome.setLayoutManager(manager);
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

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            this.finishAffinity();
        }
        return super.onKeyDown(keyCode, event);
    }

}
