package com.example.stickhero.GameClasses;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class DestinationGround {

    int startX, endX;

    public DestinationGround(int startX, int endX) {
        this.startX = startX;
        this.endX = endX;
    }


    public boolean isInBounds(int pointX) {
        return pointX > startX && pointX < endX;
    }


    public int getStartX() {
        return startX;
    }

    public int getEndX() {
        return endX;
    }

    public void draw(Canvas canvas, Paint paint, int groundTop, int screenY) {
        Rect finish = new Rect(this.startX, groundTop, this.endX, screenY);
        canvas.drawRect(finish, paint);
    }
}
