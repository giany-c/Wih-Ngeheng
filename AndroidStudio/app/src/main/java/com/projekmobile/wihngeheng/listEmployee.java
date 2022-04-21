package com.projekmobile.wihngeheng;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

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

public class listEmployee extends AppCompatActivity {
    ListView listEmployee;
    String employee_id, employee_name, employee_address, employee_phone, employee_email, employee_position;
    ArrayList<HashMap<String, String>> list_karyawan;
    String URL_LIST_EMPLOYEE = "http://192.168.1.9/projectmobile/mobile_wihNgeheng/getEmployee.php";

    private static final String TAG_EMPLOYEE = "dataEmployee";
    private static final String TAG_ID = "employee_id";
    private static final String TAG_NAME = "employee_name";
    private static final String TAG_ADDRESS = "employee_address";
    private static final String TAG_PHONE = "employee_phone";
    private static final String TAG_EMAIL = "employee_email";
    private static final String TAG_POSITION = "employee_position";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_employee);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        list_karyawan = new ArrayList<>();
        listEmployee = findViewById(R.id.listEmployee);

        RequestQueue queue = Volley.newRequestQueue(com.projekmobile.wihngeheng.listEmployee.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LIST_EMPLOYEE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray karyawan = jsonObject.getJSONArray(TAG_EMPLOYEE);

                    for (int i=0; i < karyawan.length(); i++){
                        JSONObject a = karyawan.getJSONObject(i);
                        employee_id = a.getString(TAG_ID);
                        employee_name = a.getString(TAG_NAME);
                        employee_address = a.getString(TAG_ADDRESS);
                        employee_phone = a.getString(TAG_PHONE);
                        employee_email = a.getString(TAG_EMAIL);
                        employee_position = a.getString(TAG_POSITION);

                        HashMap<String, String> map=new HashMap<>();
                        map.put("employee_id", employee_id);
                        map.put("employee_name", employee_name);
                        map.put("employee_address", employee_address);
                        map.put("employee_phone", employee_phone);
                        map.put("employee_email", employee_email);
                        map.put("employee_position", employee_position);

                        list_karyawan.add(map);

                        String[] from = {"employee_id", "employee_name", "employee_address", "employee_phone", "employee_email", "employee_position"};
                        int[] to = {R.id.txtEmpID, R.id.txtEmployeeName, R.id.txtEmployeeAddress, R.id.txtEmployeePhone, R.id.txtEmployeeEmail, R.id.txtEmployeePosition};

                        ListAdapter adapter = new SimpleAdapter(com.projekmobile.wihngeheng.listEmployee.this,
                                list_karyawan, R.layout.isi_list_employee, from, to);
                        listEmployee.setAdapter(adapter);
                    }

                } catch (JSONException e) {
                    Toast.makeText(com.projekmobile.wihngeheng.listEmployee.this, e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(com.projekmobile.wihngeheng.listEmployee.this, "Cek koneksi anda terlebih dahulu!", Toast.LENGTH_SHORT).show();

            }
        });
        queue.getCache().clear();
        queue.add(stringRequest);

        listEmployee.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String getEmployeeID = ((TextView)view.findViewById(R.id.txtEmpID)).getText().toString();
                String getEmployeeName = ((TextView)view.findViewById(R.id.txtEmployeeName)).getText().toString();
                String getEmployeeAddress = ((TextView)view.findViewById(R.id.txtEmployeeAddress)).getText().toString();
                String getEmployeePhone = ((TextView)view.findViewById(R.id.txtEmployeePhone)).getText().toString();
                String getEmployeeEmail = ((TextView)view.findViewById(R.id.txtEmployeeEmail)).getText().toString();
                String getEmployeePosition = ((TextView)view.findViewById(R.id.txtEmployeePosition)).getText().toString();

                Intent i = new Intent(com.projekmobile.wihngeheng.listEmployee.this, editEmployee.class);
                i.putExtra("employee_id", getEmployeeID);
                i.putExtra("employee_name", getEmployeeName);
                i.putExtra("employee_address", getEmployeeAddress);
                i.putExtra("employee_phone", getEmployeePhone);
                i.putExtra("employee_email", getEmployeeEmail);
                i.putExtra("employee_position", getEmployeePosition);
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