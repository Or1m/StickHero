package com.example.stickhero.Managers;

import android.content.Context;
import android.content.SharedPreferences;

public class SaveLoadManager {

    //region Static Variables
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String score         = "score";
    public static final String chocolates    = "chocolates";
    public static final String isMuted       = "muted";
    public static final String character     = "character";

    public static SharedPreferences sharedpreferences = null;
    //endregion

    //region Singleton and Constructor
    private static SaveLoadManager instance = null;

    public static SaveLoadManager getInstance(Context context)  {
        if (instance == null)
            instance = new SaveLoadManager(context);

        return instance;
    }

    public static SaveLoadManager getInstance()  {
        if (instance == null)
            instance = new SaveLoadManager(null);

        return instance;
    }

    private SaveLoadManager(Context context) {
        if(context != null)
            sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
    }
    //endregion

    //region SharedPrefs Manipulation Methods
    public void updateSharedPrefs(int newScore, int newChocolates, boolean muted) {
        SharedPreferences.Editor editor = sharedpreferences.edit();

        int oldScore = sharedpreferences.getInt(score, 0);
        if(newScore > oldScore)
            editor.putInt(score, newScore);

        int oldChocolates = sharedpreferences.getInt(chocolates, 0);
        editor.putInt(chocolates, oldChocolates + newChocolates);

        editor.putBoolean(isMuted, muted);
        editor.apply();
    }

    public void clearPrefs() {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.remove(score);
        editor.remove(chocolates);
        editor.remove(character);
        editor.apply();
    }

    public void subtractChocolates(int price) {
        sharedpreferences.edit().putInt(chocolates, this.getChocolates() - price).apply();
    }
    //endregion

    //region Getters and Setters
    public boolean getMuted() {
        return sharedpreferences.getBoolean(isMuted, false);
    }

    public int getChocolates() {
        return sharedpreferences.getInt(chocolates, 0);
    }

    public int getScore() {
        return sharedpreferences.getInt(score, 0);
    }

    public int getCharacter() {
        return sharedpreferences.getInt(character, 0);
    }

    public void setCharacter(int newImgID) {
        sharedpreferences.edit().putInt(character, newImgID).apply();
    }
    //endregion
}
