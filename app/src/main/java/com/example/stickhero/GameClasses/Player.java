package com.example.stickhero.GameClasses;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;

import com.example.stickhero.Behaviour.Drawable;
import com.example.stickhero.R;
import com.example.stickhero.SettingsManager;

public class Player implements Drawable {

    Bitmap mainSprite;
    Bitmap[] runAnimation;

    private int x, y, baseY, width, height, groundDistance, offset = 32;
    private boolean isWalking;
    private boolean isInFinish;

    private int stopPosition;
    boolean backOnStart = false;
    boolean isGoingToFall = false;

    private Ground ground;

    private static Player instance = null;

    public static Player getInstance(Ground ground, Resources res)  {
        if (instance == null)
            instance = new Player(ground, res);

        return instance;
    }

    public static Player getInstance()  {
        if (instance == null)
            instance = new Player(null,null);

        return instance;
    }

    private Player(Ground ground, Resources res) {
        mainSprite = BitmapFactory.decodeResource(res, R.drawable.idle);

        width = mainSprite.getWidth() / 5;
        height = mainSprite.getHeight() / 5;

        mainSprite = Bitmap.createScaledBitmap(mainSprite, width, height, false);

        y = (int) (SettingsManager.getInstance().getScreenY() / 1.7);
        baseY = y;
        x = offset;

        this.ground = ground;
        groundDistance = this.ground.getRight() - offset;

        stopPosition = SettingsManager.getInstance().getScreenX();
    }


    public void update(Stick stick, DestinationGround dest, Background[] backgrounds, int deltaTime) {
        Point stickEnd = stick.getEnd();

        if(!backOnStart) {
            stopPosition = dest.getEndX() - groundDistance;
            backOnStart = true;
        }

        if(isInFinish && this.x > offset) {
            this.moveX((int)(-SettingsManager.getInstance().getMovingSpeed() * deltaTime));
            return;
        }

        if(isInFinish) {
            this.x = offset;
            this.isInFinish = false;
            this.backOnStart = false;

            dest.reset();
            ground.reset();

            stick.reset();
        }

        if(stick.isLyingDown() && !dest.isInBounds(stickEnd.x) && (this.x + width / 3) < stick.getEnd().x) {
            this.setWalking(true);
            this.moveX((int)(SettingsManager.getInstance().getMovingSpeed() * deltaTime));
            isGoingToFall = true;
            return;
        }

        if(isGoingToFall) {
            this.y += 20;

            if(isWalking)
                this.isWalking = false;

            return;
        }

        if(stick.isLyingDown() && dest.isInBounds(stickEnd.x) && this.x < stopPosition) {
            this.setWalking(true);
            this.moveX((int)(SettingsManager.getInstance().getMovingSpeed() * deltaTime));
        }

        if(this.x >= stopPosition && !isInFinish) {
            this.setWalking(false);
            this.isInFinish = true;
        }
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
        return this.baseY + this.height - 30;
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

    public boolean isInFinish() {
        return isInFinish;
    }

    //endregion
}
