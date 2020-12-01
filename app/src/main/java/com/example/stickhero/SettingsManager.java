package com.example.stickhero;

import android.content.res.Resources;
import android.util.DisplayMetrics;

public class SettingsManager {

    private static SettingsManager instance = null;

    private final double gravity = 0.2;
    private final int screenX, screenY;

    private final int destMin;
    private final int destMinWidth = 30;
    private final int destMaxWidth = 250;

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
}
