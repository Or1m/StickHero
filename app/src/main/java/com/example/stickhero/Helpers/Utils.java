package com.example.stickhero.Helpers;

import android.graphics.Point;

public class Utils {

    public static Point rotateLineClockWise(Point center, Point edge, double angle) {
        double xRot = center.x + Math.cos(Math.toRadians(angle)) * (edge.x - center.x) - Math.sin(Math.toRadians(angle)) * (edge.y - center.y);
        double yRot = center.y + Math.sin(Math.toRadians(angle)) * (edge.x - center.x) + Math.cos(Math.toRadians(angle)) * (edge.y - center.y);
        return new Point((int) xRot, (int) yRot);
    }
}
