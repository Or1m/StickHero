package com.example.stickhero.Customs;

public class ShopItem {

    private int ID;
    private String name;
    private int price;
    private int imgID;

    //region Constructors
    public ShopItem(String name, int price, int imgID) {
        this.name = name;
        this.price = price;
        this.imgID = imgID;
    }

    public ShopItem() { }
    //endregion

    //region Getters & Setters
    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getImgID() {
        return imgID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setImgID(int imgID) {
        this.imgID = imgID;
    }

    //endregion
}
