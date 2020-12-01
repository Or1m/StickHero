package com.example.stickhero.GameClasses;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import com.example.stickhero.Behaviour.Drawable;
import com.example.stickhero.R;
import com.example.stickhero.SettingsManager;

import java.util.Set;

public class Background implements Drawable {

    int x = 0;
    int y = 0;

    private Bitmap background;

    public Background(Resources res)
    {
        this.background = BitmapFactory.decodeResource(res, R.drawable.background2);
        this.background = Bitmap.createScaledBitmap(background,
                SettingsManager.getInstance().getScreenX(),
                SettingsManager.getInstance().getScreenY(), false);
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        canvas.drawBitmap(this.background, this.x, this.y, paint);
    }

    public void update(int deltaTime) {
        if (Player.getInstance().isWalking()) {
            this.moveX(-24);
        }

        if (this.isNotVisible())
            this.setX(SettingsManager.getInstance().getScreenX());
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
