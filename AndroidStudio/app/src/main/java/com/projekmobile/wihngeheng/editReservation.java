package com.projekmobile.wihngeheng;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class editReservation extends AppCompatActivity {
    private long id;
    private String tanggal;
    private String jam;
    private String nama;
    private String telp;
    private String pax;
    private String tujuan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_reservation);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        final EditText txtEditDate = (EditText)findViewById(R.id.txtEditDate);
        final EditText txtEditTime = (EditText)findViewById(R.id.txtEditTime);
        final EditText txtEditCustName = (EditText)findViewById(R.id.txtEditCustName);
        final EditText txtEditCustTelp = (EditText)findViewById(R.id.txtEditCustTelp);
        final EditText txtEditPax = (EditText)findViewById(R.id.txtEditPax);
        final EditText txtEditEvent = (EditText)findViewById(R.id.txtEditEvent);
        Button btnCancelEdit = (Button)findViewById(R.id.btnCancelEditReservation);
        Button btnEditReservation = (Button)findViewById(R.id.btnEditReservation);
        //Buat objek untuk Class MyDBHandler
        final MyDBHandler dbHandler = new MyDBHandler(this);
        //Membuka koneksi database
        try {

            dbHandler.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Bundle bundle = this.getIntent().getExtras();
        id = bundle.getLong("id");
        tanggal = bundle.getString("tanggal");
        jam = bundle.getString("jam");
        nama = bundle.getString("nama");
        telp = bundle.getString("telp");
        pax = bundle.getString("pax");
        tujuan = bundle.getString("tujuan");
        txtEditDate.setText(tanggal);
        txtEditTime.setText(jam);
        txtEditCustName.setText(nama);
        txtEditCustTelp.setText(telp);
        txtEditPax.setText(pax);
        txtEditEvent.setText(tujuan);

        btnEditReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Reservation reservation = new Reservation();
                reservation.set_id(id);
                reservation.set_tanggal(txtEditDate.getText().toString());
                reservation.set_jam(txtEditTime.getText().toString());
                reservation.set_nama(txtEditCustName.getText().toString());
                reservation.set_telp(txtEditCustTelp.getText().toString());
                reservation.set_pax(txtEditPax.getText().toString());
                reservation.set_tujuan(txtEditEvent.getText().toString());
                dbHandler.updateReservation(reservation);

                Toast.makeText(editReservation.this, "Reservasi berhasil diedit",Toast.LENGTH_LONG).show();
                Intent i = new Intent(editReservation.this, listReservation.class);
                startActivity(i);
                editReservation.this.finish();
                dbHandler.close();
            }
        });
        btnCancelEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(editReservation.this, listReservation.class);
                startActivity(i);
                editReservation.this.finish();
                dbHandler.close();
            }
        });
    }
}