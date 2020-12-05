package com.example.stickhero.GameClasses;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.stickhero.Behaviour.Drawable;
import com.example.stickhero.SettingsManager;

import java.util.Set;

public class DestinationGround implements Drawable {

    int startX, endX;

    public DestinationGround() {
        reset();
    }

    public void update(int deltaTime, Stick stick, DestinationGround dest) {
        if(Player.getInstance().isInFinish()) {
            startX -= (int)(SettingsManager.getInstance().getMovingSpeed() * deltaTime);
            endX -= (int)(SettingsManager.getInstance().getMovingSpeed() * deltaTime);
        }
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

    public void reset() {
        int widthMin = SettingsManager.getInstance().getDestMinWidth();
        int widthMax = SettingsManager.getInstance().getDestMaxWidth();

        int width = (int)(Math.random() * (widthMax - widthMin + 1) + widthMin);

        int min = SettingsManager.getInstance().getDestMin();
        int max = SettingsManager.getInstance().getScreenX() - width;

        this.startX = (int)(Math.random() * (max - min + 1) + min);
        this.endX = startX + width;
    }
}
