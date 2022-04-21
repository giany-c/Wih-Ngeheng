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

//public class editEmployee extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
public class editEmployee extends AppCompatActivity{
    EditText txtEmployeeEditName, txtEmployeeEditAddress, txtEmployeeEditPhone, txtEmployeeEditEmail;
    Spinner spinEmployeeEditPosition;
    Button btnEmployeeEditSave, btnEmployeeEditDelete, btnEmployeeEditCancel;
    String jabatanEdit, employee_id, employee_name, employee_address, employee_phone, employee_email, employee_position, posisiEdit;
    String[] positionEdit = {"Choose", "Cashier", "Sous Chef", "Executive Chef", "Pastry Chef", "Delivery Man", "Kitchen Manager"
            , "General Manager", "Waiter"};
    String URL_EDIT_EMPLOYEE = "http://192.168.1.9/projectmobile/mobile_wihNgeheng/updateEmployee.php";
    String URL_DELETE_EMPLOYEE = "http://192.168.1.9/projectmobile/mobile_wihNgeheng/deleteEmployee.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_employee);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        txtEmployeeEditName = findViewById(R.id.txtEmployeeEditName);
        txtEmployeeEditAddress = findViewById(R.id.txtEmployeeEditAddress);
        txtEmployeeEditPhone = findViewById(R.id.txtEmployeeEditPhone);
        txtEmployeeEditEmail = findViewById(R.id.txtEmployeeEditEmail);
        spinEmployeeEditPosition = findViewById(R.id.spinEmployeeEditPosition);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, positionEdit);
        spinEmployeeEditPosition.setAdapter(adapter);


        btnEmployeeEditSave = findViewById(R.id.btnEmployeeEditSave);
        btnEmployeeEditDelete = findViewById(R.id.btnEmployeeEditDelete);
        btnEmployeeEditCancel = findViewById(R.id.btnEmployeeEditCancel);

        Intent i = getIntent();
        //employee_name, employee_address, employee_phone, employee_email, employee_position
        employee_id = i.getStringExtra("employee_id");
        employee_name = i.getStringExtra("employee_name");
        employee_address = i.getStringExtra("employee_address");
        employee_phone = i.getStringExtra("employee_phone");
        employee_email = i.getStringExtra("employee_email");
        employee_position = i.getStringExtra("employee_position");

        txtEmployeeEditName.setText(employee_name);
        txtEmployeeEditAddress.setText(employee_address);
        txtEmployeeEditPhone.setText(employee_phone);
        txtEmployeeEditEmail.setText(employee_email);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinEmployeeEditPosition.setAdapter(adapter);
        if (employee_position != null) {
            int spinnerPosition = adapter.getPosition(employee_position);
            spinEmployeeEditPosition.setSelection(spinnerPosition);
        }


        btnEmployeeEditSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameEmployee = txtEmployeeEditName.getText().toString();
                String addressEmployee = txtEmployeeEditAddress.getText().toString();
                String phoneEmployee = txtEmployeeEditPhone.getText().toString();
                String emailEmployee = txtEmployeeEditEmail.getText().toString();
                String positionEmployee = spinEmployeeEditPosition.getSelectedItem().toString();
                if (txtEmployeeEditName.length() > 0 && txtEmployeeEditAddress.length() > 0 && txtEmployeeEditPhone.length() > 0 && txtEmployeeEditEmail.length() > 0 && !spinEmployeeEditPosition.getSelectedItem().equals(posisiEdit)) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(editEmployee.this);
                    dialog.setTitle("Confirmation");
                    dialog.setMessage("Are you sure the data is correct?\n" +
                            "Name \t\t\t\t: " +nameEmployee+ "\n" +
                            "Address  \t\t: " +addressEmployee+ "\n" +
                            "Phone     \t\t: " +phoneEmployee+ "\n" +
                            "Email    \t\t\t: " +emailEmployee+ "\n" +
                            "Position \t\t: " +positionEmployee);
                    dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            editEmployee(employee_id, nameEmployee, addressEmployee, phoneEmployee, emailEmployee, positionEmployee);
                        }
                    });
                    dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    dialog.show();
                }else{
                    Toast.makeText(editEmployee.this, "Data tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnEmployeeEditCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnEmployeeEditDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(editEmployee.this);
                dialog.setMessage("Are you sure want to delete?");
                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteEmployee(employee_id);
                        Intent i = new Intent(editEmployee.this, listEmployee.class);
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

        spinEmployeeEditPosition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("Choose")){
                    posisiEdit = "Choose";
                } else {
                    jabatanEdit = parent.getItemAtPosition(position).toString();
                    employee_position = jabatanEdit;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void editEmployee(String employee_id, String nameEmployee, String addressEmployee, String phoneEmployee, String emailEmployee, String positionEmployee) {
        RequestQueue queue = Volley.newRequestQueue(editEmployee.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_EDIT_EMPLOYEE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int success = jsonObject.getInt("success");
                    if (success == 1) {
                        Toast.makeText(editEmployee.this, "Data karyawan berhasil diupdate", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(editEmployee.this, listEmployee.class);
                        startActivity(i);
                        finish();
                    } else {
                        Toast.makeText(editEmployee.this, "Data karyawan gagal diupdate", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(editEmployee.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(editEmployee.this, "Cek koneksi anda terlebih dahulu!", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("employee_id", employee_id);
                params.put("employee_name", nameEmployee);
                params.put("employee_address", addressEmployee);
                params.put("employee_phone", phoneEmployee);
                params.put("employee_email", emailEmployee);
                params.put("employee_position", positionEmployee);
                return params;
            }
        };
        queue.getCache().clear();
        queue.add(stringRequest);

    }

    private void deleteEmployee(String employee_id) {
        RequestQueue queue = Volley.newRequestQueue(editEmployee.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_DELETE_EMPLOYEE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int success = jsonObject.getInt("success");
                    if (success == 1) {
                        Toast.makeText(editEmployee.this, "Data karyawan berhasil dihapus", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(editEmployee.this, "Data karyawan gagal dihapus", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(editEmployee.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(editEmployee.this, "Cek koneksi anda terlebih dahulu!", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("employee_id", employee_id);
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