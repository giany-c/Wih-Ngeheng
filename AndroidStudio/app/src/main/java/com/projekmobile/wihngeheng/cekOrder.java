package com.projekmobile.wihngeheng;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class cekOrder extends AppCompatActivity {
    TextView cekNama, cekTelp, cekTanggal, cekLokasi, cekLayanan, cekTotal, cekNotes, cekCoderef;
    Button btnBack;
    Button btnDone;
    String nama, telp, tanggal, lokasi, layanan, total, notes, coderef;
    String URL_ORDER_DONE = "http://192.168.1.9/projectmobile/mobile_wihNgeheng/deleteCekOrder.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cek_order);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        cekNama = findViewById(R.id.cekCustomerName);
        cekTelp = findViewById(R.id.cekCustomerTelp);
        cekTanggal = findViewById(R.id.cekTanggalOrder);
        cekLokasi = findViewById(R.id.cekLokasiOrder);
        cekLayanan = findViewById(R.id.cekLayanan);
        cekTotal = findViewById(R.id.cekTotal);
        cekNotes = findViewById(R.id.cekOrder);
        cekCoderef = findViewById(R.id.cekRefCode);

        btnBack = (Button)findViewById(R.id.btnCekBack);
        btnDone = (Button)findViewById(R.id.btnCekDone);

        Intent i = getIntent();
        nama = i.getStringExtra("nama");
        telp = i.getStringExtra("telp");
        tanggal = i.getStringExtra("tanggal");
        lokasi = i.getStringExtra("lokasi");
        layanan = i.getStringExtra("layanan");
        total = i.getStringExtra("total");
        notes = i.getStringExtra("notes");
        coderef = i.getStringExtra("coderef");

        cekNama.setText(nama);
        cekTelp.setText(telp);
        cekTanggal.setText(tanggal);
        cekLokasi.setText(lokasi);
        cekLayanan.setText(layanan);
        cekTotal.setText(total);
        cekNotes.setText(notes);
        cekCoderef.setText(coderef);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                Intent intent = new Intent(cekOrder.this, listOrder.class);
                startActivity(intent);
                Toast.makeText(cekOrder.this, "BACK", Toast.LENGTH_LONG).show();
            }
        });

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(cekOrder.this);
                dialog.setMessage("Are you sure?");
                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteOrder(coderef);
                        Intent i = new Intent(cekOrder.this, listOrder.class);
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
    }

    private void deleteOrder(String employee_id) {
        RequestQueue queue = Volley.newRequestQueue(cekOrder.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ORDER_DONE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int success = jsonObject.getInt("success");
                    if (success == 1) {
                        Toast.makeText(cekOrder.this, "DONE", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(cekOrder.this, "GAGAL", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(cekOrder.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(cekOrder.this, "Cek koneksi anda terlebih dahulu!", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("coderef", coderef);
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