package com.projekmobile.wihngeheng;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class MyDBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "dataReservation.db";
    private static final String TABLE_NAME = "reservasi";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TANGGAL = "tanggal";
    private static final String COLUMN_JAM = "jam";
    private static final String COLUMN_NAMA = "nama";
    private static final String COLUMN_TELP = "telp";
    private static final String COLUMN_PAX = "pax";
    private static final String COLUMN_TUJUAN = "tujuan";

    //Constructor untuk Class MyDBHandler
    public MyDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    //Method untuk Create Database
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE_RESERVATION = "CREATE TABLE " + TABLE_NAME + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TANGGAL + " VARCHAR(50) NOT NULL, " +
                COLUMN_JAM + " VARCHAR(50) NOT NULL, " +
                COLUMN_NAMA + " VARCHAR(50) NOT NULL, " +
                COLUMN_TELP + " VARCHAR(50) NOT NULL, " +
                COLUMN_PAX + " INTEGER NOT NULL, " +
                COLUMN_TUJUAN + " VARCHAR(50) NOT NULL)";
        sqLiteDatabase.execSQL(CREATE_TABLE_RESERVATION);
    }

    //Method yang dipakai untuk upgrade tabel
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
    /*---- Insert, Select, Update, Delete ----*/

    private SQLiteDatabase database;
    //Method untuk open database connection
    public void open() throws SQLException {
        database = this.getWritableDatabase();
    }
    //Inisialisasi semua kolom di tabel database
    private String[] allColumns =
            {COLUMN_ID,COLUMN_TANGGAL,COLUMN_JAM,COLUMN_NAMA,COLUMN_TELP,COLUMN_PAX,COLUMN_TUJUAN};
    //Method untuk memindahkan isi cursor ke objek barang
    private Reservation cursorToReservation(Cursor cursor) {
        Reservation reservation = new Reservation();
        reservation.set_id(cursor.getLong(0));
        reservation.set_tanggal(cursor.getString(1));
        reservation.set_jam(cursor.getString(2));
        reservation.set_nama(cursor.getString(3));
        reservation.set_telp(cursor.getString(4));
        reservation.set_pax(cursor.getString(5));
        reservation.set_tujuan(cursor.getString(6));
        return reservation;
    }
    //Method untuk menambahkan barang baru
    public void createReservation(String tanggal, String jam, String nama, String telp, String pax, String tujuan){
        ContentValues values = new ContentValues();
        values.put(COLUMN_TANGGAL, tanggal);
        values.put(COLUMN_JAM, jam);
        values.put(COLUMN_NAMA, nama);
        values.put(COLUMN_TELP, telp);
        values.put(COLUMN_PAX, pax);
        values.put(COLUMN_TUJUAN, tujuan);
        database.insert(TABLE_NAME, null, values);
    }
    //Method untuk mendapatkan detail per barang
    public Reservation getReservation(long id){
        Reservation reservation = new Reservation();
        Cursor cursor = database.query(TABLE_NAME,allColumns,"_id="+id,null,null,null,null);
        cursor.moveToFirst();
        reservation = cursorToReservation(cursor);
        cursor.close();
        return reservation;
    }

    //Method untuk mendapatkan data semua Location di tabel Location
    public ArrayList<Reservation> getAllReservation(){
        ArrayList<Reservation> daftarReservasi = new ArrayList<Reservation>();
        Cursor cursor = database.query(TABLE_NAME,allColumns,null,null,null,null,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Reservation reservation = cursorToReservation(cursor);
            daftarReservasi.add(reservation);
            cursor.moveToNext();
        }
        cursor.close();
        return daftarReservasi;
    }
    //Method untuk mengupdate data Location
    public void updateReservation(Reservation reservation){
        String filter = "_id="+reservation.get_id();
        ContentValues args = new ContentValues();
        args.put(COLUMN_TANGGAL, reservation.get_tanggal());
        args.put(COLUMN_JAM, reservation.get_jam());
        args.put(COLUMN_NAMA, reservation.get_nama());
        args.put(COLUMN_TELP, reservation.get_telp());
        args.put(COLUMN_PAX, reservation.get_pax());
        args.put(COLUMN_TUJUAN, reservation.get_tujuan());
        database.update(TABLE_NAME, args, filter, null);
    }
    //Method untuk menghapus data Location
    public void deleteReservation(long id){
        String filter = "_id="+id;
        database.delete(TABLE_NAME, filter, null);
    }
}