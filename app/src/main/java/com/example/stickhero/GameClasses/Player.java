package com.example.stickhero.GameClasses;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;

import com.example.stickhero.Behaviour.ICollidable;
import com.example.stickhero.Behaviour.IDrawable;
import com.example.stickhero.Managers.SaveLoadManager;
import com.example.stickhero.R;
import com.example.stickhero.Managers.SettingsManager;

public class Player implements IDrawable, ICollidable {

    //region Private Variables
    private Bitmap mainSprite;

    private int x, y, baseY, width, height, groundDistance, stopPosition, offsetY, offsetX;
    private boolean isWalking, isInFinish, isGoingToFall, backOnStart, scored, chocolated, flipped, dead;

    private double internalFallingSpeed;

    private Resources resources;
    private Ground ground;
    private Stick stick;
    //endregion

    //region Singleton and Constructor
    private static Player instance = null;

    public static Player getInstance(Ground ground, Resources res)  {
        if (instance == null)
            instance = new Player(ground, res);

        return instance;
    }

    public static Player getInstance()  {
        if (instance == null)
            instance = new Player(null,null);

        return instance;
    }

    private Player(Ground ground, Resources res) {
        this.resources = res;

        reset();

        if(ground != null) {
            this.ground = ground;
            this.groundDistance = this.ground.getEndX() - offsetX;
            this.stopPosition = SettingsManager.getInstance().getScreenX();
        }
    }
    //endregion

    //region Overrides
    @Override
    public void update(int deltaTime, Stick stick, DestinationGround dest) {
        this.stick = stick;
        Point stickEnd = stick.getEnd();

        if(!backOnStart) {
            stopPosition = dest.getEndX() - groundDistance;
            backOnStart = true;
        }

        if(isInFinish && this.x > offsetX) {
            this.moveX((int)(-SettingsManager.getInstance().getMovingSpeed() * deltaTime));
            return;
        }

        if(isInFinish) {
            this.x = offsetX;
            this.chocolated = false;
            this.isInFinish = false;
            this.backOnStart = false;

            ground.reset();
            dest.reset();
            stick.reset();
        }

        if(stick.isLyingDown() && !dest.isInBounds(stickEnd.x) && (this.x + width / 3) < stick.getEnd().x) {
            this.setWalking(true);
            this.moveX((int)(SettingsManager.getInstance().getMovingSpeed() * deltaTime));
            isGoingToFall = true;
            return;
        }

        if(this.y >= SettingsManager.getInstance().getScreenY())
            this.dead = true;

        if(isGoingToFall) {
            this.internalFallingSpeed += SettingsManager.getInstance().getGravity() * 30;
            this.y += this.internalFallingSpeed;

            if(isWalking) {
                this.isWalking = false;
                flip();
            }

            return;
        }

        if(stick.isLyingDown() && dest.isInBounds(stickEnd.x) && this.x < stopPosition) {
            this.setWalking(true);
            this.moveX((int)(SettingsManager.getInstance().getMovingSpeed() * deltaTime));
        }

        if(this.x >= stopPosition && !isInFinish) {
            this.setWalking(false);
            this.isInFinish = true;
            this.scored = true;
        }
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        canvas.drawBitmap(this.mainSprite, this.x, this.y + offsetY, paint);
    }
    //endregion

    public void reset() {
        dead = isWalking = isInFinish = isGoingToFall = chocolated = scored = false;

        if(flipped) {
            flip();
            flipped = false;
        }

        this.internalFallingSpeed = SettingsManager.getInstance().getFallingSpeed();

        if(stick != null)
            stick.reset();

        y = (int) (SettingsManager.getInstance().getScreenY() / 1.7);
        baseY = y;
        x = offsetX;

        resetSprite();
    }

    private void resetSprite() {
        if(SaveLoadManager.getInstance().getCharacter() == 0) {
            this.mainSprite = BitmapFactory.decodeResource(this.resources, R.drawable.boy);
            this.offsetY = 0;
            this.offsetX = 32;
        }
        else {
            this.mainSprite = BitmapFactory.decodeResource(this.resources, SaveLoadManager.getInstance().getCharacter());
            this.offsetY = -25;
            this.offsetX = 0;
        }

        this.width  = mainSprite.getWidth() / 5;
        this.height = mainSprite.getHeight() / 5;

        this.mainSprite = Bitmap.createScaledBitmap(mainSprite, width, height, false);
    }

    public void flipIfWalkingOnStick(Stick stick) {
        if(Player.getInstance().isWalking() && stick.isLyingDown())
            Player.getInstance().flip();
    }

    private void flip() {
        Matrix matrix = new Matrix();
        matrix.postScale( 1, -1 , width / 2f, height / 2f);
        mainSprite = Bitmap.createBitmap(mainSprite, 0, 0, width, height, matrix, true);
        flipped = !flipped;
        this.y = flipped ? getPlayerBottom() - 30 : (int) (SettingsManager.getInstance().getScreenY() / 1.7);
    }

    public void moveX(int translation) {
        this.x += translation;
    }

    //region Getters & Setters
    public int getPlayerBottom() {
        return this.baseY + this.height - 30;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isWalking() {
        return isWalking;
    }

    public boolean isInFinish() {
        return isInFinish;
    }

    public boolean isScored() {
        return scored;
    }

    public boolean isChocolated() {
        return chocolated;
    }

    public boolean isBackOnStart() {
        return backOnStart;
    }

    public boolean isDead() {
        return dead;
    }

    public void setScored(boolean scored) {
        this.scored = scored;
    }

    public void setChocolated(boolean chocolated) {
        this.chocolated = chocolated;
    }

    public void setWalking(boolean walking) {
        isWalking = walking;
    }
    //endregion
}