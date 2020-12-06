package com.example.stickhero.Customs;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.stickhero.R;

public class ShopItem {

    private String name;
    private int price;
    private int imgID;

    public ShopItem(String name, int price, int imgID) {
        this.name = name;
        this.price = price;
        this.imgID = imgID;
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
}
