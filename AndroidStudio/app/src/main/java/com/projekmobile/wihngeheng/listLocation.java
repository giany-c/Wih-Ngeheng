package com.projekmobile.wihngeheng;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class listLocation extends AppCompatActivity{
    ListView listLoc;
    ArrayList<HashMap<String, String>> list_lokasi;
    TextView title;
    String location_id, location_name, location_address, location_region, location_phone, location_openinghours;
    String URL_LIST_LOCATION = "http://192.168.1.9/projectmobile/mobile_wihNgeheng/getLocation.php";

    private static final String TAG_LOCATION = "dataLocation";
    private static final String TAG_ID = "location_id";
    private static final String TAG_NAME = "location_name";
    private static final String TAG_ADDRESS = "location_address";
    private static final String TAG_REGION = "location_region";
    private static final String TAG_PHONE = "location_phone";
    private static final String TAG_OPENINGHOURS = "location_openinghours";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_location);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        list_lokasi = new ArrayList<>();
        listLoc = findViewById(R.id.listLoc);

        RequestQueue queue = Volley.newRequestQueue(listLocation.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LIST_LOCATION, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray lokasi = jsonObject.getJSONArray(TAG_LOCATION);

                    for (int i=0; i < lokasi.length(); i++){
                        JSONObject a = lokasi.getJSONObject(i);
                        location_id = a.getString(TAG_ID);
                        location_name = a.getString(TAG_NAME);
                        location_address = a.getString(TAG_ADDRESS);
                        location_region = a.getString(TAG_REGION);
                        location_phone = a.getString(TAG_PHONE);
                        location_openinghours = a.getString(TAG_OPENINGHOURS);

                        HashMap<String, String> map=new HashMap<>();
                        map.put("location_id", location_id);
                        map.put("location_name", location_name);
                        map.put("location_address", location_address);
                        map.put("location_region", location_region);
                        map.put("location_phone", location_phone);
                        map.put("location_openinghours", location_openinghours);

                        list_lokasi.add(map);

                        String[] from = {"location_id", "location_name", "location_address", "location_region", "location_phone", "location_openinghours"};
                        int[] to = {R.id.txtID, R.id.txtLocPlaceName, R.id.txtLocAddress, R.id.txtLocRegion, R.id.txtLocPhone, R.id.txtLocOpeningHours};

                        ListAdapter adapter = new SimpleAdapter(listLocation.this,
                                list_lokasi, R.layout.isi_list_location, from, to);
                        listLoc.setAdapter(adapter);
                    }

                } catch (JSONException e) {
                    Toast.makeText(listLocation.this, e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(listLocation.this, "Cek koneksi anda terlebih dahulu!", Toast.LENGTH_SHORT).show();

            }
        });
        queue.getCache().clear();
        queue.add(stringRequest);

        FloatingActionButton fabLocation = findViewById(R.id.fabLocation);
        fabLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(listLocation.this, addLocation.class);
                startActivity(i);
            }
        });

        listLoc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String getLocID = ((TextView)view.findViewById(R.id.txtID)).getText().toString();
                String getLocName = ((TextView)view.findViewById(R.id.txtLocPlaceName)).getText().toString();
                String getLocAddress = ((TextView)view.findViewById(R.id.txtLocAddress)).getText().toString();
                String getLocRegion = ((TextView)view.findViewById(R.id.txtLocRegion)).getText().toString();
                String getLocPhone = ((TextView)view.findViewById(R.id.txtLocPhone)).getText().toString();
                String getLocOpening = ((TextView)view.findViewById(R.id.txtLocOpeningHours)).getText().toString();

                Intent i = new Intent(listLocation.this, editLocation.class);
                i.putExtra("location_id", getLocID);
                i.putExtra("location_name", getLocName);
                i.putExtra("location_address", getLocAddress);
                i.putExtra("location_region", getLocRegion);
                i.putExtra("location_phone", getLocPhone);
                i.putExtra("location_openinghours", getLocOpening);
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