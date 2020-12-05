package com.example.stickhero.GameClasses;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import com.example.stickhero.Behaviour.IDrawable;
import com.example.stickhero.SettingsManager;
import com.example.stickhero.Utils;

public class Stick implements IDrawable {

    //region Private Variables
    private Point start, end;

    private final int width = 10;
    private double alpha = 0;

    private boolean isGrowing;
    private boolean isFalling;
    private boolean isLyingDown;

    private Ground ground;
    //endregion

    public Stick(Ground ground) {
        this.ground = ground;

        reset();
    }

    //region Overrides
    @Override
    public void update(int deltaTime, Stick stick, DestinationGround dest) {
        if(Player.getInstance().isInFinish()) {
            this.start.x -= (int)(SettingsManager.getInstance().getMovingSpeed() * deltaTime);
            this.end.x   -= (int)(SettingsManager.getInstance().getMovingSpeed() * deltaTime);
        }

        if(this.isGrowing)
            this.moveEndY(-(int)((SettingsManager.getInstance().getMovingSpeed() + 0.1) * deltaTime));

        int groundTop = Player.getInstance().getPlayerBottom();
        if(this.end.y > groundTop) {
            this.end.y = groundTop;

            this.isFalling   = false;
            this.isLyingDown = true;
        }

        if(this.isFalling()) {
            this.rotate(alpha);
            alpha += SettingsManager.getInstance().getGravity() * ((double)deltaTime / 30);
        }
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        paint.setStrokeWidth(this.width);
        canvas.drawLine(this.start.x, Player.getInstance().getPlayerBottom(), this.end.x, this.end.y, paint);
    }
    //endregion

    //region Manipulating Methods
    public void moveEndY(int translation) {
        this.end.y += translation;
    }

    public void rotate(double alpha) {
        this.end = Utils.rotateLineClockWise(start, end, alpha);
    }

    public void reset() {
        isFalling = isGrowing = isLyingDown = false;
        alpha = 0;

        this.start = new Point(ground.getEndX(), Player.getInstance().getPlayerBottom());
        this.end   = new Point(this.start);

        this.start.x -= width / 2;
        this.end.x   -= width / 2;
    }
    //endregion

    //region Getters & Setters
    public boolean isFalling() {
        return isFalling;
    }

    public boolean isLyingDown() {
        return isLyingDown;
    }

    public Point getEnd() {
        return end;
    }

    public void setGrowing(boolean growing) {
        isGrowing = growing;
    }

    public void setFalling(boolean falling) {
        isFalling = falling;
    }
    //endregion
}
