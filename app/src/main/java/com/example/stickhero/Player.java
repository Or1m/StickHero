package com.example.stickhero;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Player {

    int x, y, width, height;
    Bitmap mainSprite;
    Bitmap[] runAnimation;

    Player(int screenY, Resources res) {
        mainSprite = BitmapFactory.decodeResource(res, R.drawable.idle);

        width = mainSprite.getWidth() / 5;
        height = mainSprite.getHeight() / 5;

        mainSprite = Bitmap.createScaledBitmap(mainSprite, width, height, false);

        y = (int) (screenY / 1.7);
        x = 32;
    }

    Bitmap getPlayer() {
        return mainSprite;
    }
}
