package com.example.stickhero.Helpers;

import android.content.res.Resources;
import android.util.DisplayMetrics;

public class SettingsManager {

    //region Private Final Variables
    private final double gravity     = 0.2;
    private final double movingSpeed = 0.7;

    private final int groundWidth    = 200;
    private final int bcgSpeed       = -24;
    private final int destMinWidth   = 30;
    private final int destMaxWidth   = 250;

    private final int screenX, screenY, destMin;
    //endregion

    //region Singleton and Constructor
    private static SettingsManager instance = null;

    private SettingsManager() {
        DisplayMetrics dm = Resources.getSystem().getDisplayMetrics();
        this.screenX = dm.widthPixels;
        this.screenY = dm.heightPixels;

        destMin = 300;
    }

    public static SettingsManager getInstance()  {
        if (instance == null)
            instance = new SettingsManager();

        return instance;
    }
    //endregion

    //region Getters
    public int getScreenX() {
        return screenX;
    }

    public int getScreenY() {
        return screenY;
    }

    public double getGravity() {
        return gravity;
    }

    public int getDestMin() {
        return destMin;
    }

    public int getDestMinWidth() {
        return destMinWidth;
    }

    public int getDestMaxWidth() {
        return destMaxWidth;
    }

    public double getMovingSpeed() {
        return movingSpeed;
    }

    public int getGroundWidth() {
        return groundWidth;
    }

    public int getBcgSpeed() {
        return bcgSpeed;
    }
    //endregion
}
