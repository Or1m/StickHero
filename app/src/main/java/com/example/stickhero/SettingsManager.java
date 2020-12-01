package com.example.stickhero;

import android.content.res.Resources;
import android.util.DisplayMetrics;

public class SettingsManager {

    private static SettingsManager instance = null;

    private final double gravity = 0.2;
    private final int groundWidth = 200;
    private final int screenX, screenY;

    private SettingsManager() {
        DisplayMetrics dm = Resources.getSystem().getDisplayMetrics();
        this.screenX = dm.widthPixels;
        this.screenY = dm.heightPixels;
    }

    public static SettingsManager getInstance()  {
        if (instance == null)
            instance = new SettingsManager();

        return instance;
    }

    public int getScreenX() {
        return screenX;
    }

    public int getScreenY() {
        return screenY;
    }

    public double getGravity() {
        return gravity;
    }

    public int getGroundWidth() {
        return groundWidth;
    }
}
