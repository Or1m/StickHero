package com.example.stickhero.Behaviour;

import android.os.Build;

import androidx.annotation.RequiresApi;

public interface Collidable {
    int getX();
    int getY();
    int getWidth();
    int getHeight();

    @RequiresApi(api = Build.VERSION_CODES.N)
    default boolean touch(Collidable collidableObject) {

        return  this.getX() + getWidth() / 2 > collidableObject.getX() &&
                this.getX() < collidableObject.getX() &&
                collidableObject.getY() > this.getY() &&
                collidableObject.getY() + collidableObject.getHeight() < this.getY() + this.getHeight();
    }
}
