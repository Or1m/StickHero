package com.example.stickhero.GameClasses;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.stickhero.Behaviour.ICollidable;
import com.example.stickhero.Behaviour.IDrawable;
import com.example.stickhero.R;
import com.example.stickhero.Managers.SettingsManager;

public class Chocolate implements IDrawable, ICollidable {

    Bitmap chocoSprite;

    private int x, y, width, height;

    public Chocolate(Resources res, int maxX) {
        chocoSprite = BitmapFactory.decodeResource(res, R.drawable.chocolate);

        width  = chocoSprite.getWidth() / 4;
        height = chocoSprite.getHeight() / 4;

        chocoSprite = Bitmap.createScaledBitmap(chocoSprite, width, height, false);

        respawn(maxX, SettingsManager.getInstance().getGroundWidth());
    }

    //region Overrides
    @Override
    public void update(int deltaTime, Stick stick, DestinationGround dest) {
        if(Player.getInstance().isChocolated())
            remove();

        if(Player.getInstance().isInFinish())
            remove();

        if(!Player.getInstance().isChocolated() && !Player.getInstance().isInFinish() && !Player.getInstance().isBackOnStart())
            respawn(dest.startX, SettingsManager.getInstance().getGroundWidth());
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        canvas.drawBitmap(this.chocoSprite, this.x, this.y, paint);
    }
    //endregion

    //region Manipulating Methods
    private void respawn(int maxX, int minX) {
        int b = (int)(Math.random() * (100) + 1) > 50 ? -130 : 50;
        this.x = (int)(Math.random() * (maxX - minX + 1) + minX);
        this.y = Player.getInstance().getPlayerBottom() + b;
    }

    private void remove() {
        this.x = -70;
        this.y = 0;
    }
    //endregion

    //region Getters
    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public int getY() {
        return this.y;
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public int getHeight() {
        return this.height;
    }
    //endregion
}
