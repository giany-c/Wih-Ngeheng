package com.projekmobile.wihngeheng;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class listReservation extends ListActivity implements AdapterView.OnItemLongClickListener {
    private MyDBHandler dbHandler;
    private ArrayList<Reservation> values;
    private Button btnEdit, btnDelete;
    private ListView list;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_reservation);

        dbHandler = new MyDBHandler(this);
        try {
            dbHandler.open();
        }catch (SQLException e){
            e.printStackTrace();
        }

        values = dbHandler.getAllReservation();

        ArrayAdapter<Reservation> adapter = new ArrayAdapter<Reservation>(this,  android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);

        list = (ListView) findViewById(android.R.id.list);
        list.setOnItemLongClickListener(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabReserve);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), addReservation.class);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog);
        dialog.setTitle("Pilih Aksi!");
        dialog.show();

        final Reservation reservation = (Reservation) getListAdapter().getItem(i);
        final long id = reservation.get_id();

        btnEdit = dialog.findViewById(R.id.btnEdit);
        btnDelete = dialog.findViewById(R.id.btnDelete);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Reservation reservation1 = dbHandler.getReservation(id);
                Intent i = new Intent(getApplicationContext(),editReservation.class);
                Bundle bundle = new Bundle();
                bundle.putLong("id", reservation1.get_id());
                bundle.putString("tanggal", reservation1.get_tanggal());
                bundle.putString("jam", reservation1.get_jam());
                bundle.putString("nama", reservation1.get_nama());
                bundle.putString("telp", reservation1.get_telp());
                bundle.putString("pax", reservation1.get_pax());
                bundle.putString("tujuan", reservation1.get_tujuan());
                i.putExtras(bundle);
                startActivity(i);
                dialog.dismiss();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder delete = new AlertDialog.Builder(context);
                delete.setTitle("Reservation");
                delete.setMessage("Are you sure?");
                delete.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbHandler.deleteReservation(id);
                        finish();
                        startActivity(getIntent());
                        Toast.makeText(listReservation.this, "Reservation Completed", Toast.LENGTH_SHORT).show();
                    }
                });
                delete.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                delete.show();
                dialog.dismiss();

            }
        });

        return true;
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

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Intent i = new Intent(this, orderAdmin.class);
            startActivity(i);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}