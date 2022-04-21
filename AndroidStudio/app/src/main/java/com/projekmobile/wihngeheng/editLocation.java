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

public class editLocation extends AppCompatActivity {
    EditText txtLocEditPlaceName, txtLocEditAddress, txtLocEditPhone, txtLocEditOpeningHours;
    Spinner spinLocationEdit;
    Button btnLocEditSave, btnLocEditDelete, btnLocEditCancel;
    String tempat, location_id, location_name, location_address, location_region, location_phone, location_openinghours, kosong;
    String[] lokasiEdit = {"Choose", "Jakarta", "Tangerang", "Bogor", "Bandung", "Bekasi"};
    String URL_EDIT_LOCATION = "http://192.168.1.9/projectmobile/mobile_wihNgeheng/updateLocation.php";
    String URL_DELETE_LOCATION = "http://192.168.1.9/projectmobile/mobile_wihNgeheng/deleteLocation.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_location);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        txtLocEditPlaceName = findViewById(R.id.txtLocEditPlaceName);
        txtLocEditAddress = findViewById(R.id.txtLocEditAddress);
        spinLocationEdit = findViewById(R.id.spinLocationEdit);
        txtLocEditPhone = findViewById(R.id.txtLocEditPhone);
        txtLocEditOpeningHours = findViewById(R.id.txtLocEditOpeningHours);
        btnLocEditSave = findViewById(R.id.btnLocEditSave);
        btnLocEditDelete = findViewById(R.id.btnLocEditDelete);
        btnLocEditCancel = findViewById(R.id.btnLocEditCancel);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, lokasiEdit);
        spinLocationEdit.setAdapter(adapter);

        Intent i = getIntent();
        location_id = i.getStringExtra("location_id");
        location_name = i.getStringExtra("location_name");
        location_address = i.getStringExtra("location_address");
        location_region = i.getStringExtra("location_region");
        location_phone = i.getStringExtra("location_phone");
        location_openinghours = i.getStringExtra("location_openinghours");

        txtLocEditPlaceName.setText(location_name);
        txtLocEditAddress.setText(location_address);
//        txtLocEditRegion.setText(location_region);
        txtLocEditPhone.setText(location_phone);
        txtLocEditOpeningHours.setText(location_openinghours);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinLocationEdit.setAdapter(adapter);
        if (location_region != null) {
            int spinnerPosition = adapter.getPosition(location_region);
            spinLocationEdit.setSelection(spinnerPosition);
        }

        spinLocationEdit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("Choose")){
                    kosong = "Choose";
                } else {
                    tempat = parent.getItemAtPosition(position).toString();
                    location_region = tempat;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnLocEditCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnLocEditDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(editLocation.this);
                dialog.setMessage("Are you sure want to delete?");
                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteLokasi(location_id);
                        Intent i = new Intent(editLocation.this, listLocation.class);
                        startActivity(i);
                        finish();
                    }
                });
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog.show();
            }
        });

        btnLocEditSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String namaLoc = txtLocEditPlaceName.getText().toString();
                String addressLoc = txtLocEditAddress.getText().toString();
                String regionLoc = spinLocationEdit.getSelectedItem().toString();
                String phoneLoc = txtLocEditPhone.getText().toString();
                String openingLoc = txtLocEditOpeningHours.getText().toString();
                if (txtLocEditPlaceName.length() > 0 && txtLocEditAddress.length() > 0 && !spinLocationEdit.getSelectedItem().equals(kosong) && txtLocEditPhone.length() > 0 && txtLocEditOpeningHours.length() > 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(editLocation.this);
                    builder.setTitle("Confirmation");
                    builder.setMessage("Are you sure the data is correct?\n" +
                            "Restaurant Name \t\t: " +namaLoc+ "\n" +
                            "Address  \t\t\t\t\t\t\t\t\t\t: " +addressLoc+ "\n" +
                            "Region    \t\t\t\t\t\t\t\t\t\t: " +regionLoc+ "\n" +
                            "Phone    \t\t\t\t\t\t\t\t\t\t\t: " +phoneLoc+ "\n" +
                            "Opening Hours \t\t\t\t: " +openingLoc);
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            editLokasi(location_id, namaLoc, addressLoc, regionLoc, phoneLoc, openingLoc);
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.show();
                } else {
                    Toast.makeText(editLocation.this, "Data tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void editLokasi(String location_id, String namaLoc, String addressLoc, String regionLoc, String phoneLoc, String openingLoc) {
        RequestQueue queue = Volley.newRequestQueue(editLocation.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_EDIT_LOCATION, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int success = jsonObject.getInt("success");
                    if (success == 1) {
                        Toast.makeText(editLocation.this, "Lokasi berhasil diupdate", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(editLocation.this, listLocation.class);
                        startActivity(i);
                        finish();
                    } else {
                        Toast.makeText(editLocation.this, "Lokasi gagal diupdate", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    Toast.makeText(editLocation.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(editLocation.this, "Cek koneksi anda terlebih dahulu!", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("location_id", location_id);
                params.put("location_name", namaLoc);
                params.put("location_address", addressLoc);
                params.put("location_region", regionLoc);
                params.put("location_phone", phoneLoc);
                params.put("location_openinghours", openingLoc);
                return params;
            }
        };
        queue.getCache().clear();
        queue.add(stringRequest);
    }

    private void deleteLokasi(String location_id) {
        RequestQueue queue = Volley.newRequestQueue(editLocation.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_DELETE_LOCATION, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int success = jsonObject.getInt("success");
                    if (success == 1) {
                        Toast.makeText(editLocation.this, "Lokasi berhasil dihapus", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(editLocation.this, "Lokasi gagal dihapus", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(editLocation.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(editLocation.this, "Cek koneksi anda terlebih dahulu!", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("location_id", location_id);
                return params;
            }
        };
        queue.getCache().clear();
        queue.add(stringRequest);
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