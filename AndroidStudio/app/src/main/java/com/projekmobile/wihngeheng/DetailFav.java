package com.projekmobile.wihngeheng;

public class DetailFav {
    private int menu_id;
    private String menu_name;
    private String menu_image;
    private String menu_category;
    private String menu_price;
    private String menu_description;

    //public DetailMenu(String menu_name, String menu_description, double menu_price, String menu_image){
    public DetailFav(String menu_name, String menu_image, String menu_description, String menu_price) {
        this.menu_name = menu_name;
        this.menu_image = menu_image;
        this.menu_description = menu_description;
        this.menu_price = menu_price;
    }

    public int getMenu_id() {
        return menu_id;
    }

    public void setMenu_id(int menu_id) {
        this.menu_id = menu_id;
    }

    public String getMenu_name() {
        return menu_name;
    }

    public void setMenu_name(String menu_name) {
        this.menu_name = menu_name;
    }

    public String getMenu_image() {
        return menu_image;
    }

    public void setMenu_image(String menu_image) {
        this.menu_image = menu_image;
    }

    public String getMenu_category() {
        return menu_category;
    }

    public void setMenu_category(String menu_category) {
        this.menu_category = menu_category;
    }

    public String getMenu_price() {
        return menu_price;
    }

    public void setMenu_price(String menu_price) {
        this.menu_price = menu_price;
    }

    public String getMenu_description() {
        return menu_description;
    }

    public void setMenu_description(String menu_description) {
        this.menu_description = menu_description;
    }
}


