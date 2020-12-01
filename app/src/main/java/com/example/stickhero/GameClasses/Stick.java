package com.example.stickhero.GameClasses;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import com.example.stickhero.Utils;

public class Stick {

    private Point start;
    private Point end;

    private int width;

    private double alpha = 0;

    private boolean isGrowing;
    private boolean isFalling;
    private boolean isLyingDown;

    public Stick(Point start, Point end, int width) {
        this.start = start;
        this.end = end;
        this.width = width;

        this.start.x -= width / 2;
        this.end.x -= width / 2;
    }


    public void update(int groundTop, double gravity) {
        if(this.isGrowing)
            this.moveEndY(-20);

        if(this.end.y > groundTop) {
            this.end.y = groundTop;

            this.isFalling = false;
            this.isLyingDown = true;
        }

        if(this.isFalling()) {
            this.rotate(alpha);
            alpha += gravity;
        }
    }

    public void draw(Canvas canvas, Paint paint, int groundTop) {
        paint.setStrokeWidth(this.width);
        canvas.drawLine(this.start.x, groundTop, this.end.x, this.end.y, paint);
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
