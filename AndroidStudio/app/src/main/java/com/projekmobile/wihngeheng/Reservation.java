package com.projekmobile.wihngeheng;

public class Reservation {

    private long _id;
    private String _tanggal;
    private String _jam;
    private String _nama;
    private String _telp;
    private String _pax;
    private String _tujuan;

    public Reservation(){

    }

    public void set_id(long id) {
        this._id = id;
    }

    public long get_id() {
        return _id;
    }

    public void set_tanggal(String tanggal) {
        this._tanggal = tanggal;
    }

    public String get_tanggal() {
        return _tanggal;
    }

    public void set_jam(String jam) {
        this._jam = jam;
    }

    public String get_jam() {
        return _jam;
    }

    public void set_nama(String nama) {
        this._nama = nama;
    }

    public String get_nama() {
        return _nama;
    }

    public void set_telp(String telp) {
        this._telp = telp;
    }

    public String get_telp() {
        return _telp;
    }

    public void set_pax(String pax) {
        this._pax = pax;
    }

    public String get_pax() {
        return _pax;
    }

    public void set_tujuan(String tujuan) {
        this._tujuan = tujuan;
    }

    public String get_tujuan() {
        return _tujuan;
    }

    @Override
    public String toString()
    {
        return "\nDate\t\t\t\t\t\t\t\t\t\t\t\t: "+ _tanggal + "\nTime\t\t\t\t\t\t\t\t\t\t\t\t: "+
                _jam+ "\nCustomer Name\t: " + _nama+"\nPhone Number\t\t\t: " + _telp+"\nPax\t\t\t\t\t\t\t\t\t\t\t\t\t: "+
                _pax+ "\nEvent\t\t\t\t\t\t\t\t\t\t\t: " + _tujuan+"\n";
    }
}