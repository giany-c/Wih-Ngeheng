package com.projekmobile.wihngeheng;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
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

public class Profile extends AppCompatActivity {
    private static final String TAG = Profile.class.getSimpleName() ;
    ImageView menubar, homebar, cartbar, profilebar;
    SessionManager sessionManager;
    public EditText name, email, address, phone;
    Button btnLogout, btnUpdate;
    String getId;
    private static String URL_READ = "http://192.168.1.9/projectmobile/mobile_wihNgeheng/login/read_detail.php";
    private static String URL_EDIT = "http://192.168.1.9/projectmobile/mobile_wihNgeheng/login/edit_detail.php";
    private static String URL_CHANGE_PASSWORD = "http://192.168.1.9/projectmobile/mobile_wihNgeheng/login/changepassword.php";
    TextView txtChangePassword, txtLocationProfile;
    private CartHandler cartHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        name = (EditText) findViewById(R.id.edtNameProfile);
        email = (EditText) findViewById(R.id.edtEmailProfile);
        address = (EditText) findViewById(R.id.edtAddressProfile);
        phone = (EditText) findViewById(R.id.edtPhoneProfile);
        txtChangePassword = findViewById(R.id.txtChangePassword);
        txtLocationProfile = findViewById(R.id.txtLocationProfile);
        btnLogout = findViewById(R.id.btnLogout);
        btnUpdate = findViewById(R.id.btnUpdateProfile);
        cartHandler = new CartHandler(this);

        sessionManager = new SessionManager(this);
        HashMap<String, String> user = sessionManager.getUserDetail();
        getId = user.get(sessionManager.ID);

       txtChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View resetpasswordLayout = LayoutInflater.from(Profile.this).inflate(R.layout.change_password, null);
                EditText OldPass = resetpasswordLayout.findViewById(R.id.edtOldPass);
                EditText NewPass = resetpasswordLayout.findViewById(R.id.edtNewPass);
                EditText ConfirmPass = resetpasswordLayout.findViewById(R.id.edtConfirmPass);

                AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);
                builder.setView(resetpasswordLayout);
                builder.setPositiveButton("CHANGE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String oldpass = OldPass.getText().toString().trim();
                        String newpass = NewPass.getText().toString().trim();
                        String confirmpass = ConfirmPass.getText().toString().trim();
                        String mEmail = email.getText().toString().trim();
                        if(oldpass.isEmpty() || newpass.isEmpty() || confirmpass.isEmpty()){
                            message("All fields are required");
                        } else {
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_CHANGE_PASSWORD,
                                    new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    message(response);
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    message(error.getMessage());
                                }
                            }){
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map<String,String> params = new HashMap<>();
                                    params.put("oldpass",oldpass);
                                    params.put("newpass",newpass);
                                    params.put("confirmpass",confirmpass);
                                    params.put("email",mEmail);
                                    return params;
                                }
                            };
                            RequestQueue queue = Volley.newRequestQueue(Profile.this);
                            queue.add(stringRequest);
                        }
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

            btnLogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(Profile.this);
                    dialog.setTitle("Log Out");
                    dialog.setMessage("Are you sure you want to log out?");
                    dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            sessionManager.logout();
                            cartHandler.deleteAll();
                        }
                    });
                    dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    dialog.show();
                }
            });

            btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String namaProfile = name.getText().toString();
                    String emailProfile = email.getText().toString();
                    String addressProfile = address.getText().toString();
                    String phoneProfile = phone.getText().toString();

                    if (name.length() > 0 && email.length() > 0 && address.length() > 0 && phone.length() > 0) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);
                        builder.setTitle("Confirmation");
                        builder.setMessage("Are you sure the data is correct?\n" +
                                "Name    \t\t\t: " +namaProfile+ "\n" +
                                "Email   \t\t\t\t: " +emailProfile+ "\n" +
                                "Address \t\t: " +addressProfile+ "\n" +
                                "Phone   \t\t\t: " +phoneProfile+ "\n");
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SaveEditDetail();
                                Toast.makeText(Profile.this, "Data updated", Toast.LENGTH_SHORT).show();
                            }
                        });
                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        builder.show();
                    } else {
                        Toast.makeText(Profile.this, "All fields are required!", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        homebar = (ImageView)findViewById(R.id.homebar);
        menubar = (ImageView)findViewById(R.id.menubar);
        cartbar = (ImageView)findViewById(R.id.cartbar);
        profilebar = (ImageView)findViewById(R.id.profilebar);

        Intent intent = getIntent();
        String valueDapat = intent.getStringExtra("selectedItemText");
        txtLocationProfile.setText(valueDapat);

        homebar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Home.class);
                String value = txtLocationProfile.getText().toString();
                intent.putExtra("selectedItemText", value);
                startActivity(intent);
            }
        });

        menubar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Menu.class);
                String value = txtLocationProfile.getText().toString();
                intent.putExtra("selectedItemText", value);
                startActivity(intent);
            }
        });

        cartbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Cart.class);
                String value = txtLocationProfile.getText().toString();
                intent.putExtra("selectedItemText", value);
                startActivity(intent);
            }
        });

        profilebar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Profile.class);
                String value = txtLocationProfile.getText().toString();
                intent.putExtra("selectedItemText", value);
                startActivity(intent);
            }
        });

    }

    public void message(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void getUserDetail(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_READ,
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
                                    String strEmail = object.getString("user_email");
                                    String strAddress = object.getString("user_address");
                                    String strPhone = object.getString("user_phone");

                                    name.setText(strName);
                                    email.setText(strEmail);
                                    address.setText(strAddress);
                                    phone.setText(strPhone);
                                }
                            }
                        } catch(JSONException e){
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(Profile.this, "Error reading data"+e.toString(),Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(Profile.this, "Error reading data"+error.toString(),Toast.LENGTH_SHORT).show();
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

    private void SaveEditDetail() {
        final String user_name = this.name.getText().toString().trim();
        final String user_email = this.email.getText().toString().trim();
        final String user_address = this.address.getText().toString().trim();
        final String user_phone = this.phone.getText().toString().trim();
        final String user_id = getId;


        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Saving...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_EDIT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if(success.equals("1")){
                                Toast.makeText(Profile.this,"Edit profile success!",Toast.LENGTH_SHORT).show();
                                sessionManager.createSession(user_name,user_email,user_address,user_phone,user_id);
                            } else {
                                Toast.makeText(Profile.this,"Edit profile failed. " +
                                        "User email has already registered.",Toast.LENGTH_SHORT).show();
                                sessionManager.createSession(user_name,user_email,user_address,user_phone,user_id);
                            }

                        } catch(JSONException e){
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(Profile.this,"Error "+ e.toString(),Toast.LENGTH_SHORT).show();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(Profile.this,"Error "+error.toString(),Toast.LENGTH_SHORT).show();

                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String>params = new HashMap<>();
                params.put("user_name",user_name);
                params.put("user_email",user_email);
                params.put("user_address",user_address);
                params.put("user_phone",user_phone);
                params.put("user_id",user_id);
                //params.put("user_password",user_password);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}
