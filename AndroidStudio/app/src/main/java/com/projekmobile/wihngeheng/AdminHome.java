package com.projekmobile.wihngeheng;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
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

import java.util.HashMap;
import java.util.Map;

public class AdminHome extends AppCompatActivity {
    TextView txtHelloAdmin;
    String namaAdmin, hello, getId;
    SessionManager sessionManager;
    String URL_READ_PROFILE = "http://192.168.1.9/projectmobile/mobile_wihNgeheng/login/read_detail.php";
    private static final String TAG = AdminHome.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        sessionManager = new SessionManager(this);
        HashMap<String, String> user = sessionManager.getUserDetail();
        getId = user.get(sessionManager.ID);
        txtHelloAdmin = findViewById(R.id.txtHelloAdmin);
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

                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("read");

                            if(success.equals("1")){
                                for(int i=0;i<jsonArray.length();i++){
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    //String strId = object.getString("user_id");
                                    String strName = object.getString("user_name");

                                    hello = "Hello, "+strName+"!";
                                    txtHelloAdmin.setText(hello);

                                }
                            }
                        } catch(JSONException e){
                            e.printStackTrace();
                            Toast.makeText(AdminHome.this, "Error reading data"+e.toString(),Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AdminHome.this, "Error reading data"+error.toString(),Toast.LENGTH_LONG).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id",getId);
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

    public void menuAdmin(View view) {
        Intent i = new Intent(AdminHome.this, listMenu.class);
        startActivity(i);
    }

    public void employeeAdmin(View view) {
        Intent i = new Intent(AdminHome.this, Employee.class);
        startActivity(i);
    }

    public void locationAdmin(View view) {
        Intent i = new Intent(AdminHome.this, listLocation.class);
        startActivity(i);
    }

    public void profileAdmin(View view) {
        Intent i = new Intent(AdminHome.this, AdminProfile.class);
        startActivity(i);
    }

    public void orderAdmin(View view) {
        Intent i = new Intent(this, orderAdmin.class);
        startActivity(i);
        finish();
    }

    public void logoutAdmin(View view) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(AdminHome.this);
        dialog.setTitle("Log Out");
        dialog.setMessage("Are you sure you want to log out?");
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sessionManager.logout();
            }
        });
        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        dialog.show();

    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            this.finishAffinity();
        }
        return super.onKeyDown(keyCode, event);
    }
}