package com.projekmobile.wihngeheng;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public class FormOrder extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText txtnamacust, txttelp, txtdate;
    TextView txtlokasi, txttotalharga, txtcode, txtnotes;
    String URL_READ_PROFILE = "http://192.168.1.9/projectmobile/mobile_wihNgeheng/login/read_detail.php";
    RadioGroup radioGroup;
    RadioButton choiceD, choiceT;
    RadioButton radioButton;
    Button btnplaceorder;
    String hasilradio, getId, namacust, telponcust;
    SessionManager sessionManager;

    String url_add_order = "http://192.168.1.9/projectmobile/mobile_wihNgeheng/addFormOrder.php";
    private static final String TAG = FormOrder.class.getSimpleName();

    private String generateString(int lenght){
        char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();

        for(int i = 0; i < lenght; i++){
            char c = chars[random.nextInt(chars.length)];
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_order);
        getSupportActionBar().hide();
        txtnamacust = findViewById(R.id.txtnamacust);
        txttelp = findViewById(R.id.txtTelp);
        txtdate = findViewById(R.id.txtDate);
        txtlokasi = findViewById(R.id.txtLokasi);
        txtnotes = findViewById(R.id.txtnotes);
        txttotalharga = findViewById(R.id.txttotalharga);
        txtcode = findViewById(R.id.txtcode);
        radioGroup = findViewById(R.id.radioGroup);
        btnplaceorder = findViewById(R.id.btnplaceorder);

        txtcode.setText(generateString(6));

        SimpleDateFormat dateF = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String date = dateF.format(Calendar.getInstance().getTime());
        txtdate.setText(date);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioButton = findViewById(checkedId);
                hasilradio = radioButton.getText().toString();
            }
        });

        String txttotalbanget = getIntent().getStringExtra("txttotalbanget");
        txttotalharga.setText(txttotalbanget);

        Intent intent = getIntent();
        String selectedItemText = intent.getStringExtra("selectedItemText");
        String notes = intent.getStringExtra("notes");
        txtlokasi.setText(selectedItemText);
        txtnotes.setText(notes);

        btnplaceorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String hasilradio = parent.getItemAtPosition(position).toString();
                String nama = txtnamacust.getText().toString();
                String telp = txttelp.getText().toString();
                String tanggal = txtdate.getText().toString();
                String lokasi = txtlokasi.getText().toString();
                String total = txttotalharga.getText().toString().replace(",","");
                String notes = txtnotes.getText().toString();
                String coderef = txtcode.getText().toString();

                if (!nama.equals("") && !telp.equals("") && !tanggal.equals("") && !notes.equals("") && !total.equals("0")) {
                    try {
                        AlertDialog.Builder dialog = new AlertDialog.Builder(FormOrder.this);
                        dialog.setMessage("Customer Name \t\t: " +txtnamacust.getText().toString() + "\nPhone Number \t\t\t: " +txttelp.getText().toString() +"\nOrder Date \t\t\t\t\t\t\t: " + txtdate.getText().toString() +
                                "\nLocation \t\t\t\t\t\t\t\t\t: " + txtlokasi.getText().toString() + "\nTotal \t\t\t\t\t\t\t\t\t\t\t\t: " + txttotalharga.getText().toString()
                                + "\nNotes \t\t\t\t\t\t\t\t\t\t\t: " + txtnotes.getText().toString());
                        dialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //intent ke activity munculin referal
                                try{
                                    buatformorder(nama,telp,tanggal,lokasi,hasilradio,total,notes,coderef);
                                    Intent i = new Intent(getApplicationContext(), CodeRef.class);
                                    i.putExtra("coderef", coderef);
                                    startActivity(i);
                                    txtnamacust.setText("");
                                    txttelp.setText("");
                                    txtdate.setText("");
                                    radioButton.setChecked(false);
                                } catch (Exception ex){
                                    Toast.makeText(FormOrder.this, "All fields are required", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        dialog.setNegativeButton("Back", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        dialog.show();

                    } catch (Exception ex) {
                        Toast.makeText(FormOrder.this, "All fields are required", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(FormOrder.this, "All fields are required", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //session
        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();
        HashMap<String, String> user = sessionManager.getUserDetail();
        getId = user.get(sessionManager.ID);
    }

    //session profile nampilin nama
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
                                    String strTelpon = object.getString("user_phone");
                                    namacust = strName;
                                    telponcust = strTelpon;
                                    txtnamacust.setText(namacust);
                                    txttelp.setText(telponcust);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(FormOrder.this, "Error reading data" + e.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(FormOrder.this, "Error reading data" + error.toString(), Toast.LENGTH_LONG).show();
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

    private void buatformorder(String nama, String telp, String tanggal, String lokasi, String hasilradio, String total, String notes, String coderef) {
        Log.d("tag", "onCreate:  "+ nama + telp + tanggal + lokasi + hasilradio + total + notes + coderef);
        RequestQueue queue = Volley.newRequestQueue(FormOrder.this);
        StringRequest request = new StringRequest(Request.Method.POST, url_add_order,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int success = jsonObject.getInt("success");
                            if (success == 1) {
                                Toast.makeText(FormOrder.this, "Data berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(FormOrder.this, "Data gagal ditambahkan", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(FormOrder.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(FormOrder.this, "No internet connection", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("nama", nama);
                params.put("telp", telp);
                params.put("tanggal", tanggal);
                params.put("lokasi", lokasi);
                params.put("layanan", hasilradio);
                params.put("total", total);
                params.put("notes", notes);
                params.put("coderef", coderef);
                return params;
            }
        };
        queue.getCache().clear();
        queue.add(request);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

}