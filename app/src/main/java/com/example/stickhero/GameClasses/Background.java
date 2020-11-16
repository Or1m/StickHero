package com.example.stickhero.GameClasses;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.stickhero.R;

public class Background {

    int x = 0;
    int y = 0;

    private Bitmap background;

    public Background(int screenX, int screenY, Resources res) {
        this.background = BitmapFactory.decodeResource(res, R.drawable.background2);
        this.background = Bitmap.createScaledBitmap(background, screenX, screenY, false);
    }


    public void moveX(int translation) {
        x += translation;
    }

    public boolean isNotVisible() {
        return this.x + this.background.getWidth() <= 0;
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Bitmap getBackground() {
        return background;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
