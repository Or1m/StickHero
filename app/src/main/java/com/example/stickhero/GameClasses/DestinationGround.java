package com.example.stickhero.GameClasses;

public class DestinationGround {

    int startX, endX;

    public DestinationGround(int startX, int endX) {
        this.startX = startX;
        this.endX = endX;
    }


    public boolean isInBounds(int pointX) {
        return pointX > startX && pointX < endX;
    }


    public int getStartX() {
        return startX;
    }

    public int getEndX() {
        return endX;
    }
}
