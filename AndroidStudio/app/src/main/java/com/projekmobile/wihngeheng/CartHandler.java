package com.projekmobile.wihngeheng;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CartHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "cart.db";
    private static final String TABLE_NAME = "cart";

    private static final String COLOUMN_NAME = "_name";
    private static final String COLOUMN_DESC = "_desc";
    private static final String COLOUMN_PRICE = "_price";
    private static final String COLOUMN_IMAGE = "_image";
    private static final String COLOUMN_QTY = "_qty";

    public CartHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_NOTES = "CREATE TABLE " + TABLE_NAME +
                "("+COLOUMN_NAME+" VARCHAR (50) PRIMARY KEY, "+
                COLOUMN_DESC+" VARCHAR (50) NOT NULL, "+
                COLOUMN_PRICE+" VARCHAR (50) NOT NULL, "+
                COLOUMN_IMAGE+" VARCHAR (50) NOT NULL, "+
                COLOUMN_QTY+" VARCHAR (50) NOT NULL "+")";
        db.execSQL(CREATE_TABLE_NOTES);


    }

    public void deleteAll() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        //deleting rows
        sqLiteDatabase.delete(TABLE_NAME, null, null);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String drop="DROP TABLE IF EXIST";
        db.execSQL(drop+TABLE_NAME);
        onCreate(db);
    }

    //INSERT SELECT UPDATE DELETE
    private SQLiteDatabase database;

    //Method untuk open database connection
    public void open() throws SQLException {
        database = this.getWritableDatabase();
    }

    private String[] allColumns = {COLOUMN_NAME, COLOUMN_DESC, COLOUMN_PRICE, COLOUMN_IMAGE, COLOUMN_QTY};

    private CartDetail cursorToNotes (Cursor cursor){
        CartDetail cart = new CartDetail();

        cart.set_name(cursor.getString(0));
        cart.set_desc(cursor.getString(1));
        cart.set_price(cursor.getString(2));
        cart.set_image(cursor.getString(3));
        cart.set_qty(cursor.getString(4));

        return cart;
    }

    public void createCart (String name, String desc, String price, String image, String qty){
        ContentValues values = new ContentValues();
        values.put(COLOUMN_NAME, name);
        values.put(COLOUMN_DESC, desc);
        values.put(COLOUMN_PRICE, price);
        values.put(COLOUMN_IMAGE, image);
        values.put(COLOUMN_QTY, qty);

        database.insert(TABLE_NAME, null, values);
    }

    public CartDetail getCartDetail(String name){
        CartDetail cart;

        Cursor cursor = database.query(TABLE_NAME, allColumns, "_name= " +"'"+name + "'", null, null, null, null);
        cursor.moveToFirst();
        cart = cursorToNotes(cursor);
        cursor.close();
        return cart;
    }

    public int getProfilesCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        int count = (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);

        return count;
    }


    public ArrayList<CartDetail> getAllNotes(){
        ArrayList<CartDetail> daftarNotes=new ArrayList<CartDetail>();
        Cursor cursor=database.query(TABLE_NAME,allColumns,null,null,null,null,null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()){
            CartDetail cart =cursorToNotes(cursor);
            daftarNotes.add(cart);
            cursor.moveToNext();
        }
        cursor.close();
        return daftarNotes;
    }

    public void updateCart (CartDetail cart){
        String filter = "_name=" + "'"+cart.get_name()+"'";
        ContentValues args = new ContentValues();
        args.put(COLOUMN_DESC, cart.get_desc());
        args.put(COLOUMN_PRICE, cart.get_price());
        args.put(COLOUMN_IMAGE, cart.get_image());
        args.put(COLOUMN_QTY, cart.get_qty());

        database.update(TABLE_NAME, args, filter, null);
    }

    public void deleteCart (String name){
        String filter = "_name="+"'"+name + "'";
        database.delete(TABLE_NAME, filter, null);
    }
}
