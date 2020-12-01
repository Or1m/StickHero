package com.example.stickhero.GameClasses;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.stickhero.Behaviour.Drawable;
import com.example.stickhero.SettingsManager;

public class Ground implements Drawable {

    private final int groundWidth = 200;

    private int left = 0;
    private int right;

    public Ground() {
        reset();
    }

    public void update() {
        if(Player.getInstance().isInFinish())
            left -= 20;
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        Rect rect = new Rect(left, Player.getInstance().getPlayerBottom(), this.right = left + this.groundWidth,
                SettingsManager.getInstance().getScreenY()
        );

        canvas.drawRect(rect, paint);
    }

    public int getRight() {
        return this.right;
    }

    public void reset() {
        this.left = 0;

        this.right = left + groundWidth;
    }
}
