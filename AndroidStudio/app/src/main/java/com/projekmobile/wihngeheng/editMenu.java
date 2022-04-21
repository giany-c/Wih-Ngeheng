package com.projekmobile.wihngeheng;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class editMenu extends AppCompatActivity  {
    EditText txtMenuEditName, txtMenuEditPrice, txtMenuEditDesc;
    Button btnMenuEditUpload, btnMenuEditCancel, btnMenuEditSave, btnMenuEditDelete;
    ImageView imageEditMenu;
    Bitmap bitmap;
    final int CODE_GALLERY_REQUEST = 999;
    Boolean inputFoto = false;
    Spinner spinMenuEditCategory;
    String kategoriMakanan, menu_id, menu_name, menu_category, menu_price, menu_description, menu_image, categoriesEdit, URL_FOTO_MENU;
    String[] kategori = {"Choose", "Steam", "Fried", "Rice", "Noodle", "Soup", "Drink"};
    String URL_EDIT_MENU = "http://192.168.1.9/projectmobile/mobile_wihNgeheng/updateMenu.php";
    String URL_DELETE_MENU = "http://192.168.1.9/projectmobile/mobile_wihNgeheng/deleteMenu.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_menu);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        txtMenuEditName = (EditText)findViewById(R.id.txtMenuEditName);
        txtMenuEditPrice = (EditText)findViewById(R.id.txtMenuEditPrice);
        txtMenuEditDesc = (EditText)findViewById(R.id.txtMenuEditDesc);
        imageEditMenu = (ImageView) findViewById(R.id.imageEditMenu);
        btnMenuEditCancel = (Button) findViewById(R.id.btnMenuEditCancel);
        btnMenuEditDelete = (Button) findViewById(R.id.btnMenuEditDelete);
        btnMenuEditSave = (Button) findViewById(R.id.btnMenuEditSave);
        imageEditMenu = (ImageView) findViewById(R.id.imageEditMenu);
        spinMenuEditCategory = (Spinner) findViewById(R.id.spinMenuEditCategory);
//        spinMenuEditCategory.setOnItemSelectedListener(this);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, kategori);
        spinMenuEditCategory.setAdapter(adapter);

        //menu_id, menu_name, menu_category, menu_price, menu_description, menu_image
        Intent i = getIntent();
        menu_id = i.getStringExtra("menu_id");
        menu_name = i.getStringExtra("menu_name");
        menu_category = i.getStringExtra("menu_category");
        menu_price = i.getStringExtra("menu_price");
        menu_description = i.getStringExtra("menu_description");
        menu_image = i.getStringExtra("menu_image");

        txtMenuEditName.setText(menu_name);
        txtMenuEditPrice.setText(menu_price);
        txtMenuEditDesc.setText(menu_description);

//        URL_FOTO_MENU = "http://192.168.1.9/projectmobile/mobile_wihNgeheng/uploads/food/" + menu_image;
        Picasso.get().load(menu_image).into(imageEditMenu);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinMenuEditCategory.setAdapter(adapter);
        if (menu_category != null){
            int spinnerPosition = adapter.getPosition(menu_category);
            spinMenuEditCategory.setSelection(spinnerPosition);
        }

        btnMenuEditCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnMenuEditDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(editMenu.this);
                dialog.setMessage("Are you sure want to delete?");
                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        hapusMenu(menu_id);
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

        btnMenuEditSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String namaMenu = txtMenuEditName.getText().toString();
                    String kategoriMenu = spinMenuEditCategory.getSelectedItem().toString();
                    String hargaMenu = txtMenuEditPrice.getText().toString();
                    String deskripsiMenu = txtMenuEditDesc.getText().toString();
                    menu_image = "";

                    if (inputFoto == false){
                        menu_image = "";
                    } else {
                        menu_image = imageToString(bitmap);
                    }

                    double hargaEditMenu = Double.parseDouble(hargaMenu);
                    Locale locale = new Locale("in", "ID");
                    NumberFormat rupiah = NumberFormat.getCurrencyInstance(locale);
                    String formatHargaEditMenu = rupiah.format(hargaEditMenu);

                    if (txtMenuEditName.length()>0 && !spinMenuEditCategory.getSelectedItem().equals(categoriesEdit) && txtMenuEditPrice.length() > 0 && txtMenuEditDesc.length()>0){
                        AlertDialog.Builder builder = new AlertDialog.Builder(editMenu.this);
                        builder.setTitle("Confirmation");
                        builder.setMessage("Are you sure the data is correct?\n" +
                                "Name \t\t\t\t\t\t\t: " +namaMenu+ "\n" +
                                "Category  \t\t\t\t: " +kategoriMenu+ "\n" +
                                "Price     \t\t\t\t\t\t: " +formatHargaEditMenu+ "\n" +
                                "Description   \t: " +deskripsiMenu);
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                updateMenu(menu_id, namaMenu, kategoriMenu, hargaMenu, deskripsiMenu, menu_image);
                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        builder.show();
                    } else {
                        Toast.makeText(editMenu.this, "Data tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e){
                    Toast.makeText(editMenu.this, "Data tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        spinMenuEditCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("Choose")){
                    categoriesEdit = "Choose";
                } else {
                    kategoriMakanan = parent.getItemAtPosition(position).toString();
                    menu_category = kategoriMakanan;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    public void imageChoose(View view) {
        ActivityCompat.requestPermissions(
                editMenu.this, new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE},
                CODE_GALLERY_REQUEST
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CODE_GALLERY_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select Image"), CODE_GALLERY_REQUEST);
            }
            else {
                Toast.makeText(getApplicationContext(), "Don't have permission!", Toast.LENGTH_LONG).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == CODE_GALLERY_REQUEST && resultCode == RESULT_OK && data != null){
            Uri filePath = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(filePath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                imageEditMenu.setImageBitmap(bitmap);
                inputFoto = true;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private String imageToString(Bitmap bitmap){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        byte[] imageBytes = outputStream.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }


    private void updateMenu(String menu_id, String namaMenu, String kategoriMenu, String hargaMenu, String deskripsiMenu, String menu_image) {
        RequestQueue queue = Volley.newRequestQueue(editMenu.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_EDIT_MENU, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int success = jsonObject.getInt("success");
                    if (success == 1) {
                        Toast.makeText(editMenu.this, "Data menu berhasil diupdate", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(editMenu.this, listMenu.class);
                        startActivity(i);
                        finish();
                    } else {
                        Toast.makeText(editMenu.this, "Data menu gagal diupdate", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(editMenu.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(editMenu.this, "Cek koneksi anda terlebih dahulu!", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param = new HashMap<>();
                param.put("menu_id", menu_id);
                param.put("menu_name", namaMenu);
                param.put("menu_category", kategoriMenu);
                param.put("menu_price", hargaMenu);
                param.put("menu_description", deskripsiMenu);
                param.put("menu_image", menu_image);
                return param;
            }
        };
        queue.getCache().clear();
        queue.add(stringRequest);

    }

    private void hapusMenu(String menu_id) {
        RequestQueue queue = Volley.newRequestQueue(editMenu.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_DELETE_MENU, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int success = jsonObject.getInt("success");
                    if (success == 1){
                        Toast.makeText(editMenu.this, "Menu berhasil dihapus", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(editMenu.this, listMenu.class);
                        startActivity(i);
                        finish();
                    } else {
                        Toast.makeText(editMenu.this, "Menu gagal dihapus", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(editMenu.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(editMenu.this, "Cek koneksi anda terlebih dahulu!", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("menu_id", menu_id);
                return map;
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