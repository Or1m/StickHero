package com.example.stickhero.GameClasses;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.stickhero.Behaviour.Drawable;
import com.example.stickhero.SettingsManager;

public class Ground implements Drawable {

    @Override
    public void draw(Canvas canvas, Paint paint) {
        Rect rect = new Rect(0,
                Player.getInstance().getPlayerBottom(),
                SettingsManager.getInstance().getGroundWidth(),
                SettingsManager.getInstance().getScreenY()
        );

        canvas.drawRect(rect, paint);
    }
}
