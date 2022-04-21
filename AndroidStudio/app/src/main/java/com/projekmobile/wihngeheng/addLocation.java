package com.projekmobile.wihngeheng;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class addLocation extends AppCompatActivity {
    EditText txtLocPlaceName, txtLocAddress, txtLocPhone, txtLocOpeningHours;
    Button btnLocReset, btnLocAddInsert;
    Spinner spinLocation;
    String tempat, location_name, location_address, location_region, location_phone, location_openinghours, wilayah;
    String[] lokasi = {"Choose", "Jakarta", "Tangerang", "Bogor", "Bandung", "Bekasi"};
    String URL_TAMBAH_LOCATION = "http://192.168.1.9/projectmobile/mobile_wihNgeheng/addLocation.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        txtLocPlaceName = findViewById(R.id.txtLocPlaceName);
        txtLocAddress = findViewById(R.id.txtLocAddress);
        spinLocation = findViewById(R.id.spinLocation);
        txtLocPhone = findViewById(R.id.txtLocPhone);
        txtLocOpeningHours = findViewById(R.id.txtLocOpeningHours);
        btnLocReset = findViewById(R.id.btnLocReset);
        btnLocAddInsert = findViewById(R.id.btnLocAddInsert);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, lokasi);
        spinLocation.setAdapter(adapter);

        spinLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("Choose")){
                    wilayah = "Choose";
                } else {
                    tempat = parent.getItemAtPosition(position).toString();
                    location_region = tempat;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btnLocReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtLocPlaceName.setText("");
                txtLocAddress.setText("");
                spinLocation.setSelection(0);
                txtLocPhone.setText("");
                txtLocOpeningHours.setText("");
            }
        });

        btnLocAddInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                location_name = txtLocPlaceName.getText().toString();
                location_address = txtLocAddress.getText().toString();
                location_region = spinLocation.getSelectedItem().toString();
                location_phone = txtLocPhone.getText().toString();
                location_openinghours = txtLocOpeningHours.getText().toString();

                if (txtLocPlaceName.length() > 0 && txtLocAddress.length() > 0  && txtLocPhone.length() > 0 && txtLocOpeningHours.length() > 0  && !spinLocation.getSelectedItem().equals(wilayah)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(addLocation.this);
                    builder.setTitle("Confirmation");
                    builder.setMessage("Are you sure the data is correct?\n" +
                            "Restaurant Name \t\t: " +location_name+ "\n" +
                            "Address  \t\t\t\t\t\t\t\t\t\t: " +location_address+ "\n" +
                            "Region    \t\t\t\t\t\t\t\t\t\t: " +location_region+ "\n" +
                            "Phone    \t\t\t\t\t\t\t\t\t\t\t: " +location_phone+ "\n" +
                            "Opening Hours \t\t\t\t: " +location_openinghours);
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            tambahLokasi(location_name, location_address, location_region, location_phone, location_openinghours);
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.show();
                } else {
                    Toast.makeText(addLocation.this, "Data tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void tambahLokasi(String location_name, String location_address, String location_region, String location_phone, String location_openinghours) {
        RequestQueue queue = Volley.newRequestQueue(addLocation.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_TAMBAH_LOCATION, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int success = jsonObject.getInt("success");
                    if (success == 1){
                        Toast.makeText(addLocation.this, "Lokasi berhasil ditambah", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(addLocation.this, listLocation.class);
                        startActivity(i);
                        finish();
                    } else {
                        Toast.makeText(addLocation.this, "Lokasi gagal ditambah", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(addLocation.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(addLocation.this, "Cek koneksi anda terlebih dahulu!", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("location_name", location_name);
                params.put("location_address", location_address);
                params.put("location_region", location_region);
                params.put("location_phone", location_phone);
                params.put("location_openinghours", location_openinghours);
                return params;
            }
        };
        queue.getCache().clear();
        queue.add(stringRequest);
        txtLocPlaceName.setText("");
        txtLocAddress.setText("");
        spinLocation.setSelection(0);
        txtLocPhone.setText("");
        txtLocOpeningHours.setText("");
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

    public void profileAdminBar(View view) {
        Intent i = new Intent(this, AdminProfile.class);
        startActivity(i);
        finish();
    }

    public void orderAdminBar(View view) {
        Intent i = new Intent(this, orderAdmin.class);
        startActivity(i);
        finish();
    }
}

