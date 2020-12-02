package com.example.stickhero.GameClasses;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.stickhero.Behaviour.Collidable;
import com.example.stickhero.Behaviour.Drawable;
import com.example.stickhero.R;

public class Chocolate implements Drawable, Collidable {

    Bitmap chocoSprite;

    private int x, y, width, height;

    public Chocolate(Resources res, int maxX, int minX) {
        chocoSprite = BitmapFactory.decodeResource(res, R.drawable.chocolate);

        width = chocoSprite.getWidth() / 4;
        height = chocoSprite.getHeight() / 4;

        chocoSprite = Bitmap.createScaledBitmap(chocoSprite, width, height, false);


        int b = (int)(Math.random() * (100) + 1) > 50 ? -130 : 50;
        this.x = (int)(Math.random() * (maxX - minX + 1) + minX);
        this.y = Player.getInstance().getPlayerBottom() + b;
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        canvas.drawBitmap(chocoSprite, this.x, this.y, paint);
    }

    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public int getY() {
        return this.y;
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    public void remove() {
        this.x = -70;
        this.y = 0;
    }
}
