package com.example.stickhero.GameClasses;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.stickhero.Behaviour.IDrawable;
import com.example.stickhero.SettingsManager;

public class Ground implements IDrawable {

    protected int startX, endX, width;

    public Ground() {
        reset();
    }

    public void reset() {
        this.startX = 0;
        this.width = SettingsManager.getInstance().getGroundWidth();

        this.endX = this.startX + width;
    }

    //region Overrides
    @Override
    public void update(int deltaTime, Stick stick, DestinationGround dest) {
        if(Player.getInstance().isInFinish())
            this.startX -= (int)(SettingsManager.getInstance().getMovingSpeed() * deltaTime);
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        Rect rect = new Rect(this.startX, Player.getInstance().getPlayerBottom(), this.endX = this.startX + this.width,
                SettingsManager.getInstance().getScreenY());

        canvas.drawRect(rect, paint);
    }
    //endregion

    //region Getters
    public int getStartX() {
        return startX;
    }

    public int getEndX() {
        return endX;
    }
    //endregion
}
