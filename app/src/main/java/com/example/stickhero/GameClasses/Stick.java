package com.example.stickhero.GameClasses;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import com.example.stickhero.Behaviour.Drawable;
import com.example.stickhero.SettingsManager;
import com.example.stickhero.Utils;

public class Stick implements Drawable {

    private Point start;
    private Point end;

    private final int width = 10;

    private double alpha = 0;

    private boolean isGrowing;
    private boolean isFalling;
    private boolean isLyingDown;

    public Stick() {
        this.start = new Point(SettingsManager.getInstance().getGroundWidth(), Player.getInstance().getPlayerBottom());
        this.end = new Point(this.start);

        this.start.x -= width / 2;
        this.end.x -= width / 2;
    }


    public void update() {
        if(this.isGrowing)
            this.moveEndY(-20);

        int groundTop = Player.getInstance().getPlayerBottom();
        if(this.end.y > groundTop) {
            this.end.y = groundTop;

            this.isFalling = false;
            this.isLyingDown = true;
        }

        if(this.isFalling()) {
            this.rotate(alpha);
            alpha += SettingsManager.getInstance().getGravity();
        }
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        paint.setStrokeWidth(this.width);
        canvas.drawLine(this.start.x, Player.getInstance().getPlayerBottom(), this.end.x, this.end.y, paint);
    }


    public void moveEndY(int translation) {
        this.end.y += translation;
    }

    public void rotate(double alpha) {
        this.end = Utils.rotateLineClockWise(start, end, alpha);
    }


    //region Getters & Setters
    public void setEndY(int newEndY) {
        this.end.y = newEndY;
    }

    //generated Getters & Setters
    public boolean isGrowing() {
        return isGrowing;
    }

    public boolean isFalling() {
        return isFalling;
    }

    public boolean isLyingDown() {
        return isLyingDown;
    }

    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }

    public int getWidth() {
        return width;
    }

    public void setGrowing(boolean growing) {
        isGrowing = growing;
    }

    public void setFalling(boolean falling) {
        isFalling = falling;
    }

    public void setLyingDown(boolean lyingDown) {
        isLyingDown = lyingDown;
    }
    //endregion
}
