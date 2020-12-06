package com.example.stickhero.GameClasses;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.stickhero.Managers.SettingsManager;

public class DestinationGround extends Ground {

    public DestinationGround() {
        reset();
    }

    //region Overrides
    @Override
    public void reset() {
        int widthMin = SettingsManager.getInstance().getMinWidthDependingOnDifficulty();
        int widthMax = SettingsManager.getInstance().getDestMaxWidth();

        width = (int) (Math.random() * (widthMax - widthMin + 1) + widthMin);

        int min = SettingsManager.getInstance().getDestMin();
        int max = SettingsManager.getInstance().getScreenX() - width;

        this.startX = (int)(Math.random() * (max - min + 1) + min);
        this.endX = startX + width;
    }

    @Override
    public void update(int deltaTime, Stick stick, DestinationGround dest) {
        if(Player.getInstance().isInFinish()) {
            startX -= (int)(SettingsManager.getInstance().getMovingSpeed() * deltaTime);
            endX   -= (int)(SettingsManager.getInstance().getMovingSpeed() * deltaTime);
        }
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        Rect finish = new Rect(this.startX, Player.getInstance().getPlayerBottom(), this.endX, SettingsManager.getInstance().getScreenY());
        canvas.drawRect(finish, paint);
    }
    //endregion

    public boolean isInBounds(int pointX) {
        return pointX > startX && pointX < endX;
    }
}
