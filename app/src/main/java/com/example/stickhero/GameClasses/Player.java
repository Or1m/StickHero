package com.example.stickhero.GameClasses;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.stickhero.R;

public class Player {

    Bitmap mainSprite;
    Bitmap[] runAnimation;

    private int x, y, width, height;
    private boolean isWalking;

    public Player(int screenY, Resources res) {
        mainSprite = BitmapFactory.decodeResource(res, R.drawable.idle);

        width = mainSprite.getWidth() / 5;
        height = mainSprite.getHeight() / 5;

        mainSprite = Bitmap.createScaledBitmap(mainSprite, width, height, false);

        y = (int) (screenY / 1.7);
        x = 32;
    }

    //region Getters & Setters
    public Bitmap getPlayer() {
        return mainSprite;
    }

    public int getPlayerBottom() {
        return this.y + this.height - 30;
    }

    //TODO opakujuce sa moveX
    public void moveX(int translation) {
        this.x += translation;
    }

    //generated Getters & Setters
    public boolean isWalking() {
        return isWalking;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setWalking(boolean walking) {
        isWalking = walking;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }
    //endregion
}
