package com.example.stickhero.GameClasses;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.stickhero.Behaviour.Drawable;
import com.example.stickhero.SettingsManager;

public class DestinationGround implements Drawable {

    int startX, endX;

    public DestinationGround(int startX, int endX) {
        this.startX = startX;
        this.endX = endX;
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        Rect finish = new Rect(this.startX, Player.getInstance().getPlayerBottom(), this.endX, SettingsManager.getInstance().getScreenY());
        canvas.drawRect(finish, paint);
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
}
