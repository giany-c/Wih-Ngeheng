package com.projekmobile.wihngeheng;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

public class LogIn extends AppCompatActivity {
    private EditText user_email, user_password;
    private Button btnLogIn;
    private TextView txtRegister;
    final String URL_LOGIN = "http://192.168.1.9/projectmobile/mobile_wihNgeheng/login/login.php";
    SessionManager sessionManager;
    String getUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        sessionManager = new SessionManager(this);

        if(sessionManager.isLoggin()==true){
            HashMap<String, String> user = sessionManager.getUserDetail();
            getUser = user.get(sessionManager.NAME);

            if(getUser.equals("Admin")){
                Intent intent = new Intent(getApplicationContext(), AdminHome.class);
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(getApplicationContext(), Home.class);
                startActivity(intent);
                finish();
            }
        }

        user_email = findViewById(R.id.edtEmailLogIn);
        user_password = findViewById(R.id.edtPasswordLogIn);
        btnLogIn = findViewById(R.id.btnLogIn);
        txtRegister = findViewById(R.id.txtRegister);


        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mEmail = user_email.getText().toString().trim();
                String mPass = user_password.getText().toString().trim();

                if(!mEmail.isEmpty() || !mPass.isEmpty()){
                    Login(mEmail,mPass);
                } else {
                    user_email.setError("Please insert email");
                    user_password.setError("Please insert password");
                }
            }
        });

        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Register.class);
                startActivity(intent);
                finish();
            }
        });


    }

    private void Login(String user_email, String user_password) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    String role = jsonObject.getString("role");

                    String user_id = jsonObject.getString("user_id");
                    String user_name = jsonObject.getString("user_name");
                    String user_email = jsonObject.getString("user_email");
                    String user_address = jsonObject.getString("user_address");
                    String user_phone = jsonObject.getString("user_phone");

                    if(success.equals("1") && role.equals("Customer")){
                            Toast.makeText(LogIn.this, "Log In Success",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(),Home.class);
                            sessionManager.createSession(user_name, user_email, user_address, user_phone, user_id);
                            startActivity(intent);
                            finish();
                    } else if(success.equals("1") && role.equals("admin")){
                        Toast.makeText(LogIn.this, "Admin Log In Success",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(),AdminHome.class);
                        sessionManager.createSession(user_name, user_email, user_address, user_phone, user_id);
                        startActivity(intent);
                        finish();
                    } else if (success.equals("0")) {
                        Toast.makeText(LogIn.this, "Wrong Password/Email",Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e){
                    e.printStackTrace();
                    Toast.makeText(LogIn.this, "Wrong Password/Email",Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LogIn.this, "Error "+error.toString(),Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_email",user_email);
                params.put("user_password",user_password);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(LogIn.this);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            this.finishAffinity();
        }
        return super.onKeyDown(keyCode, event);
    }
}