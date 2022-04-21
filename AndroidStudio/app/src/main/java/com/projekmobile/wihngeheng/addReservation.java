package com.projekmobile.wihngeheng;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class addReservation extends AppCompatActivity {

    final Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reservation);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        final EditText txtDate = (EditText)findViewById(R.id.txtDate);
        final EditText txtTime = (EditText)findViewById(R.id.txtTime);
        final EditText txtCustName = (EditText)findViewById(R.id.txtCustName);
        final EditText txtCustTelp = (EditText)findViewById(R.id.txtCustTelp);
        final EditText txtPax = (EditText)findViewById(R.id.txtPax);
        final EditText txtEvent = (EditText)findViewById(R.id.txtEvent);

        Button btnCancel = (Button)findViewById(R.id.btnCancel);
        Button btnAdd = (Button)findViewById(R.id.btnAddReservation);
        //Buat objek untuk Class MyDBHandler
        final MyDBHandler dbHandler = new MyDBHandler(this);
        //Membuka koneksi database
        try {
            dbHandler.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Reservation reservation = new Reservation();
                String tanggal = txtDate.getText().toString();
                String jam = txtTime.getText().toString();
                String nama = txtCustName.getText().toString();
                String telp = txtCustTelp.getText().toString();
                String pax = txtPax.getText().toString();
                String tujuan = txtEvent.getText().toString();
                if(!tanggal.isEmpty() && !jam.isEmpty() && !nama.isEmpty() && !telp.isEmpty() && !pax.isEmpty() && !tujuan.isEmpty()){
                    dbHandler.createReservation(tanggal, jam, nama, telp, pax, tujuan);
                    Toast.makeText(addReservation.this, "Reservasi berhasil ditambahkan",Toast.LENGTH_LONG).show();
                    txtDate.setText("");
                    txtTime.setText("");
                    txtCustName.setText("");
                    txtCustTelp.setText("");
                    txtPax.setText("");
                    txtEvent.setText("");
                    txtDate.requestFocus();
                    Intent i = new Intent(addReservation.this, listReservation.class);
                    startActivity(i);
                    addReservation.this.finish();
                    dbHandler.close();
                } else {
                    Toast.makeText(addReservation.this, "Silakan lengkapi kembali data anda",Toast.LENGTH_LONG).show();
                }

            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(addReservation.this, listReservation.class);
                startActivity(i);
                addReservation.this.finish();
                dbHandler.close();
            }
        });

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "dd/MM/yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                txtDate.setText(sdf.format(myCalendar.getTime()));
            }
        };

        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(addReservation.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        txtTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(addReservation.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        txtTime.setText(String.format("%02d:%02d", selectedHour, selectedMinute));
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
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