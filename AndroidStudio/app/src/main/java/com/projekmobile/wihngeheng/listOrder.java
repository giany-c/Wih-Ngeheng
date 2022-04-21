package com.projekmobile.wihngeheng;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class listOrder extends AppCompatActivity{
    ListView listOrder;
    String nama, telp, tanggal, lokasi, layanan, total, notes,coderef;
    ArrayList<HashMap<String, String>> list_order;
    String URL_GET_ORDER = "http://192.168.1.9/projectmobile/mobile_wihNgeheng/getOrder.php";

    private static final String TAG_ORDER = "dataOrder";
    private static final String TAG_NAMA = "nama";
    private static final String TAG_TELP = "telp";
    private static final String TAG_TANGGAL = "tanggal";
    private static final String TAG_LOKASI = "lokasi";
    private static final String TAG_LAYANAN = "layanan";
    private static final String TAG_TOTAL = "total";
    private static final String TAG_NOTES = "notes";
    private static final String TAG_CODEREF = "coderef";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_order);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        list_order = new ArrayList<>();
        listOrder = findViewById(R.id.listOrder);

        RequestQueue queue = Volley.newRequestQueue(com.projekmobile.wihngeheng.listOrder.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_GET_ORDER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray order = jsonObject.getJSONArray(TAG_ORDER);

                    for (int i=0; i < order.length(); i++){
                        JSONObject a = order.getJSONObject(i);
                        nama = a.getString(TAG_NAMA);
                        telp = a.getString(TAG_TELP);
                        tanggal = a.getString(TAG_TANGGAL);
                        lokasi = a.getString(TAG_LOKASI);
                        layanan = a.getString(TAG_LAYANAN);
                        total = a.getString(TAG_TOTAL);
                        notes = a.getString(TAG_NOTES);
                        coderef = a.getString(TAG_CODEREF);

                        HashMap<String, String> map=new HashMap<>();
                        map.put("nama", nama);
                        map.put("telp", telp);
                        map.put("tanggal", tanggal);
                        map.put("lokasi", lokasi);
                        map.put("layanan", layanan);
                        map.put("total", total);
                        map.put("notes", notes);
                        map.put("coderef", coderef);

                        list_order.add(map);

                        String[] from = {"nama", "telp", "tanggal","lokasi","layanan","total","notes","coderef"};
                        int[] to = {R.id.txtCustomerName, R.id.txtCustomerTelp, R.id.txtTanggalOrder, R.id.txtLokasiOrder,R.id.txtLayanan, R.id.txtTotal, R.id.txtOrder, R.id.txtRefCode};

                        ListAdapter adapter = new SimpleAdapter(com.projekmobile.wihngeheng.listOrder.this,
                                list_order, R.layout.isi_order_list, from, to);
                        listOrder.setAdapter(adapter);
                    }

                } catch (JSONException e) {
                    Toast.makeText(com.projekmobile.wihngeheng.listOrder.this, e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(com.projekmobile.wihngeheng.listOrder.this, "Cek koneksi anda terlebih dahulu!", Toast.LENGTH_SHORT).show();

            }
        });
        queue.getCache().clear();
        queue.add(stringRequest);

        listOrder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String getNama = ((TextView)view.findViewById(R.id.txtCustomerName)).getText().toString();
                String getTelp = ((TextView)view.findViewById(R.id.txtCustomerTelp)).getText().toString();
                String getTanggal = ((TextView)view.findViewById(R.id.txtTanggalOrder)).getText().toString();
                String getLokasi = ((TextView)view.findViewById(R.id.txtLokasiOrder)).getText().toString();
                String getLayanan = ((TextView)view.findViewById(R.id.txtLayanan)).getText().toString();
                String getTotal = ((TextView)view.findViewById(R.id.txtTotal)).getText().toString();
                String getNotes = ((TextView)view.findViewById(R.id.txtOrder)).getText().toString();
                String getRefCode = ((TextView)view.findViewById(R.id.txtRefCode)).getText().toString();

                Intent i = new Intent(com.projekmobile.wihngeheng.listOrder.this, cekOrder.class);
                i.putExtra("nama", getNama);
                i.putExtra("telp", getTelp);
                i.putExtra("tanggal", getTanggal);
                i.putExtra("lokasi", getLokasi);
                i.putExtra("layanan", getLayanan);
                i.putExtra("total", getTotal);
                i.putExtra("notes", getNotes);
                i.putExtra("coderef", getRefCode);
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