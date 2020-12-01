package com.example.stickhero.GameClasses;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import com.example.stickhero.Behaviour.Drawable;
import com.example.stickhero.R;
import com.example.stickhero.SettingsManager;

public class Player implements Drawable {

    Bitmap mainSprite;
    Bitmap[] runAnimation;

    private int x, y, width, height;
    private boolean isWalking;

    private static Player instance = null;

    public static Player getInstance(Resources res)  {
        if (instance == null)
            instance = new Player(res);

        return instance;
    }

    public static Player getInstance()  {
        if (instance == null)
            instance = new Player(null);

        return instance;
    }

    private Player(Resources res) {
        mainSprite = BitmapFactory.decodeResource(res, R.drawable.idle);

        width = mainSprite.getWidth() / 5;
        height = mainSprite.getHeight() / 5;

        mainSprite = Bitmap.createScaledBitmap(mainSprite, width, height, false);

        y = (int) (SettingsManager.getInstance().getScreenY() / 1.7);
        x = 32;
    }


    public void update(Stick stick, DestinationGround dest) {
        Point stickEnd = stick.getEnd();

        if(stick.isLyingDown() && dest.isInBounds(stickEnd.x) && this.x < stickEnd.x) {
            this.setWalking(true);
            this.moveX(10);
        }

        if(this.x >= stickEnd.x)
            this.setWalking(false);
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        canvas.drawBitmap(this.mainSprite, this.x, this.y, paint);
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
