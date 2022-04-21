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

public class addEmployee extends AppCompatActivity {
    EditText txtEmployeeName, txtEmployeeAddress, txtEmployeePhone, txtEmployeeEmail;
    Spinner spinEmployeePosition;
    Button btnEmployeeReset, btnEmployeeAddInsert;
    String jabatan, employee_name, employee_address, employee_phone, employee_email, employee_position, posisi;
    String[] position = {"Choose", "Cashier", "Sous Chef", "Executive Chef", "Pastry Chef", "Delivery Man", "Kitchen Manager"
            , "General Manager", "Waiter"};
    String URL_TAMBAH_EMPLOYEE = "http://192.168.1.9/projectmobile/mobile_wihNgeheng/addEmployee.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        txtEmployeeName = findViewById(R.id.txtEmployeeName);
        txtEmployeeAddress = findViewById(R.id.txtEmployeeAddress);
        txtEmployeePhone = findViewById(R.id.txtEmployeePhone);
        txtEmployeeEmail = findViewById(R.id.txtEmployeeEmail);
        btnEmployeeReset = findViewById(R.id.btnEmployeeReset);
        btnEmployeeAddInsert = findViewById(R.id.btnEmployeeAddInsert);
        spinEmployeePosition = findViewById(R.id.spinEmployeePosition);
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, position);
        spinEmployeePosition.setAdapter(adapter);


        btnEmployeeReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtEmployeeName.setText("");
                txtEmployeeAddress.setText("");
                txtEmployeeEmail.setText("");
                txtEmployeePhone.setText("");
                txtEmployeeName.requestFocus();
                spinEmployeePosition.setSelection(0);
            }
        });

        btnEmployeeAddInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                employee_name = txtEmployeeName.getText().toString();
                employee_address = txtEmployeeAddress.getText().toString();
                employee_phone = txtEmployeePhone.getText().toString();
                employee_email = txtEmployeeEmail.getText().toString();
                employee_position = spinEmployeePosition.getSelectedItem().toString();
                if (txtEmployeeName.length() > 0 && txtEmployeeAddress.length() > 0 && txtEmployeePhone.length() > 0 && txtEmployeeEmail.length() > 0 && !spinEmployeePosition.getSelectedItem().equals(posisi)){
                    AlertDialog.Builder builder = new AlertDialog.Builder(addEmployee.this);
                    builder.setTitle("Confirmation");
                    builder.setMessage("Are you sure the data is correct?\n" +
                            "Name \t\t\t\t: " +employee_name+ "\n" +
                            "Address  \t\t: " +employee_address+ "\n" +
                            "Phone     \t\t: " +employee_phone+ "\n" +
                            "Email    \t\t\t: " +employee_email+ "\n" +
                            "Position \t\t: " +employee_position);
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            tambahEmployee(employee_name, employee_address, employee_phone, employee_email, employee_position);
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.show();
                } else{
                    Toast.makeText(addEmployee.this, "Data tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        spinEmployeePosition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("Choose")){
                    posisi = "Choose";
                } else {
                    jabatan = parent.getItemAtPosition(position).toString();
                    employee_position = jabatan;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void tambahEmployee(String employee_name, String employee_address, String employee_phone, String employee_email, String employee_position) {
        RequestQueue queue = Volley.newRequestQueue(addEmployee.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_TAMBAH_EMPLOYEE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int success = jsonObject.getInt("success");
                    if (success == 1) {
                        Toast.makeText(addEmployee.this, "Data karyawan berhasil ditambah", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(addEmployee.this, listEmployee.class);
                        startActivity(i);
                        finish();
                    } else {
                        Toast.makeText(addEmployee.this, "Data karyawan gagal ditambah", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    Toast.makeText(addEmployee.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(addEmployee.this, "Cek koneksi anda terlebih dahulu!", Toast.LENGTH_SHORT).show();
            }
        })
        {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("employee_name", employee_name);
                params.put("employee_address", employee_address);
                params.put("employee_phone", employee_phone);
                params.put("employee_email", employee_email);
                params.put("employee_position", employee_position);
                return params;
            }
        };
        queue.getCache().clear();
        queue.add(stringRequest);
        txtEmployeeName.setText("");
        txtEmployeeAddress.setText("");
        txtEmployeeEmail.setText("");
        txtEmployeePhone.setText("");
        txtEmployeeName.requestFocus();
        spinEmployeePosition.setSelection(0);
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
