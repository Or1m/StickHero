package com.example.stickhero.GameClasses;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.stickhero.Behaviour.IDrawable;
import com.example.stickhero.R;
import com.example.stickhero.Managers.SettingsManager;

public class Background implements IDrawable {

    int x = 0;
    int y = 0;

    private Bitmap background;

    public Background(Resources res) {
        this.background = BitmapFactory.decodeResource(res, R.drawable.background2);
        this.background = Bitmap.createScaledBitmap(background,
                SettingsManager.getInstance().getScreenX(),
                SettingsManager.getInstance().getScreenY(), false);
    }

    //region Overrides
    @Override
    public void update(int deltaTime, Stick stick, DestinationGround dest) {
        if (Player.getInstance().isWalking()) {
            this.moveX(SettingsManager.getInstance().getBcgSpeed());
        }

        if (this.isNotVisible())
            this.setX(SettingsManager.getInstance().getScreenX());
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        canvas.drawBitmap(this.background, this.x, this.y, paint);
    }
    //endregion

    public void moveX(int translation) {
        x += translation;
    }


    public boolean isNotVisible() {
        return this.x + this.background.getWidth() <= 0;
    }


    public void setX(int x) {
        this.x = x;
    }
}
