package com.projekmobile.wihngeheng;

import androidx.annotation.NonNull;

public class CartDetail {

    private String _name;
    private String _desc;
    private String _price;
    private String _image;
    private String _qty;

    public CartDetail() {

    }

//    @Override
//    public String toString() {
//        return "Menu name: " + _name + "\n" +
//                "Menu desc: " + _desc + "\n" +
//                "Menu price: " + _price + "\n" +
//                "Menu image: " + _image + "\n" +
//                "Menu qty: " + _qty ;
//    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_desc() {
        return _desc;
    }

    public void set_desc(String _desc) {
        this._desc = _desc;
    }

    public String get_price() {
        return _price;
    }

    public void set_price(String _price) {
        this._price = _price;
    }

    public String get_image() {
        return _image;
    }

    public void set_image(String _image) {
        this._image = _image;
    }

    public String get_qty() {
        return _qty;
    }

    public void set_qty(String _qty) {
        this._qty = _qty;
    }
}
