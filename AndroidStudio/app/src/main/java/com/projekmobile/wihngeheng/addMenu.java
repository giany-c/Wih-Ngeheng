package com.projekmobile.wihngeheng;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
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
import com.bumptech.glide.load.engine.Resource;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class addMenu extends AppCompatActivity{
    EditText txtMenuName, txtMenuPrice, txtMenuDesc;
    Button btnMenuUpload, btnMenuReset, btnMenuAdd;
    ImageView imageMenu;
    Bitmap bitmap;
    Spinner spinMenuCategory;
    final int CODE_GALLERY_REQUEST = 999;
    Boolean inputFoto = false;
    String kategoriMakanan, menu_name, menu_category, menu_price, menu_description, menu_image, categories;
    String[] kategori = {"Choose", "Steam", "Fried", "Noodle", "Soup", "Drink"};
    String URL_TAMBAH_MENU = "http://192.168.1.9/projectmobile/mobile_wihNgeheng/addMenu.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_menu);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        txtMenuName = (EditText)findViewById(R.id.txtMenuName);
        txtMenuPrice = (EditText)findViewById(R.id.txtMenuPrice);
        txtMenuDesc = (EditText)findViewById(R.id.txtMenuDesc);
        imageMenu = (ImageView) findViewById(R.id.imageMenu);
        btnMenuReset = (Button) findViewById(R.id.btnMenuReset);
        btnMenuAdd = (Button) findViewById(R.id.btnMenuAdd);
        spinMenuCategory = (Spinner) findViewById(R.id.spinMenuCategory);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, kategori);
        spinMenuCategory.setAdapter(adapter);
//
//        if (inputFoto == false){
//            Resources res = getResources();
//            Drawable drawable = res.getDrawable(R.drawable.photo);
//            //Drawable menu_image = getResources().getDrawable(R.drawable.photo);
//        } else {
//            menu_image = imageToString(bitmap);
//        }

        btnMenuReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtMenuName.setText("");
                txtMenuPrice.setText("");
                txtMenuDesc.setText("");
                spinMenuCategory.setSelection(0);
                txtMenuName.requestFocus();
                imageMenu.setImageResource(R.drawable.photo);
            }
        });

        btnMenuAdd.setOnClickListener(new View.OnClickListener() {
            //            menu_name, menu_category, menu_price, menu_description, menu_image
            @Override
            public void onClick(View v) {
                try {
                    menu_name = txtMenuName.getText().toString();
                    menu_category = spinMenuCategory.getSelectedItem().toString();
                    menu_price = txtMenuPrice.getText().toString();
                    menu_description = txtMenuDesc.getText().toString();
                    menu_image = "";

                    if (inputFoto == false){
                        menu_image = "";
                    } else {
                        menu_image = imageToString(bitmap);
                    }

                    double hargaMenu = Double.parseDouble(menu_price);
                    Locale locale = new Locale("in", "ID");
                    NumberFormat rupiah = NumberFormat.getCurrencyInstance(locale);
                    String formatHargaMenu = rupiah.format(hargaMenu);

                    if (txtMenuName.length()>0 && !spinMenuCategory.getSelectedItem().equals(categories) && txtMenuPrice.length()>0 && txtMenuDesc.length()>0){

                        AlertDialog.Builder builder = new AlertDialog.Builder(addMenu.this);
                        builder.setTitle("Confirmation");
                        builder.setMessage("Are you sure the data is correct?\n" +
                                "Name \t\t\t\t\t\t\t: " +menu_name+ "\n" +
                                "Category  \t\t\t\t: " +menu_category+ "\n" +
                                "Price     \t\t\t\t\t\t: " +formatHargaMenu+ "\n" +
                                "Description   \t: " +menu_description);
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                tambahMenu(menu_name, menu_category, menu_price, menu_description, menu_image);
                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        builder.show();
                    } else {
                        Toast.makeText(addMenu.this, "Data tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(addMenu.this, "Data tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        spinMenuCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("Choose")){
                    categories = "Choose";
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

    private void tambahMenu(String menu_name, String menu_category, String menu_price, String menu_description, String menu_image) {
//    private void tambahMenu(String menu_name, String menu_category, String menu_price, String menu_description) {
        RequestQueue queue = Volley.newRequestQueue(addMenu.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_TAMBAH_MENU, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int success = jsonObject.getInt("success");
                    if (success == 1) {
                        Toast.makeText(addMenu.this, "Data menu berhasil ditambah", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(addMenu.this, listMenu.class);
                        startActivity(i);
                        finish();
                    } else {
                        Toast.makeText(addMenu.this, "Data menu gagal ditambah", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(addMenu.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(addMenu.this, "Cek koneksi anda terlebih dahulu!", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("menu_name", menu_name);
                param.put("menu_category", menu_category);
                param.put("menu_price", menu_price);
                param.put("menu_description", menu_description);
                param.put("menu_image", menu_image);
                return param;
            }
        };
        queue.getCache().clear();
        queue.add(stringRequest);
        txtMenuName.setText("");
        spinMenuCategory.setSelection(0);
        txtMenuPrice.setText("");
        txtMenuDesc.setText("");
        imageMenu.setImageResource(R.drawable.photo);
    }

    public void imageChoose(View view) {
        ActivityCompat.requestPermissions(
                addMenu.this, new String[]{
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
                imageMenu.setImageBitmap(bitmap);
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