package com.projekmobile.wihngeheng;

public class DetailOrder {

    private String name;
    private String desc;
    private String price;
    private String image;
    private String qty;

    //public DetailMenu(String menu_name, String menu_description, double menu_price, String menu_image){
    public DetailOrder(String name, String desc, String price, String image, String qty) {
        this.name = name;
        this.desc = desc;
        this.price = price;
        this.image = image;
        this.qty = qty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }
}

