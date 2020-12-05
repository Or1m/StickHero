package com.example.stickhero.Behaviour;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.stickhero.GameClasses.DestinationGround;
import com.example.stickhero.GameClasses.Stick;

public interface Drawable {
    void update(int deltaTime, Stick stick, DestinationGround dest);
    void draw(Canvas canvas, Paint paint);
}
