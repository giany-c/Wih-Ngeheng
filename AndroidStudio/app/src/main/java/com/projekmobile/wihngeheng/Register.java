package com.projekmobile.wihngeheng;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class Register extends AppCompatActivity {
    EditText edtNameRegister, edtAddressRegister, edtPhoneRegister, edtEmailRegister,
            edtPasswordRegister, edtConfirmPasswordRegister;
    Button btnRegister;
    TextView txtLogIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtNameRegister = findViewById(R.id.edtNameRegister);
        edtAddressRegister = findViewById(R.id.edtAddressRegister);
        edtPhoneRegister = findViewById(R.id.edtPhoneRegister);
        edtEmailRegister = findViewById(R.id.edtEmailRegister);
        edtPasswordRegister = findViewById(R.id.edtPasswordRegister);
        edtConfirmPasswordRegister = findViewById(R.id.edtConfirmPasswordRegister);
        btnRegister = findViewById(R.id.btnRegister);
        txtLogIn = findViewById(R.id.txtLogIn);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_name, user_address, user_phone, user_email, user_password, user_confirmpassword, user_role;
                user_name = String.valueOf(edtNameRegister.getText());
                user_address = String.valueOf(edtAddressRegister.getText());
                user_phone = String.valueOf(edtPhoneRegister.getText());
                user_email = String.valueOf(edtEmailRegister.getText());
                user_password = String.valueOf(edtPasswordRegister.getText());
                user_confirmpassword = String.valueOf(edtConfirmPasswordRegister.getText());
                user_role = "Customer";

                if (!user_password.equals(user_confirmpassword)){
                    Toast.makeText(Register.this,"Password missmatch",Toast.LENGTH_SHORT).show();
                } else if(!user_name.equals("") && !user_address.equals("") && !user_phone.equals("") && !user_email.equals("")
                        && !user_password.equals("")) {
                    //Start ProgressBar first (Set visibility VISIBLE)
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[6];
                            field[0] = "user_name";
                            field[1] = "user_address";
                            field[2] = "user_phone";
                            field[3] = "user_email";
                            field[4] = "user_password";
                            field[5] = "user_role";

                            //Creating array for data
                            String[] data = new String[6];
                            data[0] = user_name;
                            data[1] = user_address;
                            data[2] = user_phone;
                            data[3] = user_email;
                            data[4] = user_password;
                            data[5] = user_role;

                            PutData putData = new PutData("http://192.168.1.9/projectmobile/mobile_wihNgeheng/register/signup.php",
                                    "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                    if(result.equals("Sign Up Success")){
                                        Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), LogIn.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                       Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
                                             }
                                         }
                                      }
                                    }
                                });
                            } else {
                                Toast.makeText(getApplicationContext(),"All fields are required", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        txtLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),LogIn.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Intent intent = new Intent(getApplicationContext(),LogIn.class);
            startActivity(intent);
        }
        return super.onKeyDown(keyCode, event);
    }
}