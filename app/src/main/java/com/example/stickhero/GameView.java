package com.example.stickhero;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.widget.Toast;

public class GameView extends SurfaceView implements Runnable {

    private Thread thread;
    private boolean isPlaying;
    private int screenX, screenY;
    private Paint paint;
    private Player player;
    private Background backgroundOne, backgroundTwo;

    public Point lineTop;
    public int groundTop;

    boolean isGrowing;
    boolean isFalling;
    boolean isLyingDown;
    boolean isWalking;

    double alpha = 0;

    double gravity = 0.2;

    public GameView(Context context, int screenX, int screenY) {
        super(context);

        this.screenX = screenX;
        this.screenY = screenY;

        paint = new Paint();
        player = new Player(this.screenY, getResources());

        groundTop = player.y + player.height - 30;

        lineTop = new Point(195, groundTop);

        this.backgroundOne = new Background(this.screenX, this.screenY, getResources());
        this.backgroundTwo = new Background(this.screenX, this.screenY, getResources());

        this.backgroundTwo.x = this.screenX;
    }

    @Override
    public void run() {
        while (isPlaying) {
            update();
            draw();
            sleep();
        }
    }

    private void update() {
        if(isWalking) {
            backgroundOne.x -= 10;
            backgroundTwo.x -= 10;
        }

        if(backgroundOne.x + backgroundOne.background.getWidth() < 0) {
            backgroundOne.x = screenX;
        }

        if(backgroundTwo.x + backgroundTwo.background.getWidth() < 0) {
            backgroundTwo.x = screenX;
        }

        if(isGrowing)
            lineTop.y -= 20;

        if(lineTop.y > groundTop) {
            lineTop.y = groundTop;

            isFalling = false;
            isLyingDown = true;
        }

        if(isFalling) {
            lineTop = rotateLineClockWise(new Point(195, groundTop), lineTop, alpha);
            alpha += gravity;
        }

        if(isLyingDown && lineTop.x > 700 && lineTop.x < 1000 && player.x < lineTop.x) {
            isWalking = true;
            player.x += 10;
        }

        if(player.x >= lineTop.x)
            isWalking = false;
    }

    static Point rotateLineClockWise(Point center, Point edge, double angle) {
        double xRot = (int) center.x + Math.cos(Math.toRadians(angle)) * (edge.x - center.x) - Math.sin(Math.toRadians(angle)) * (edge.y - center.y);
        double yRot = (int) center.y + Math.sin(Math.toRadians(angle)) * (edge.x - center.x) + Math.cos(Math.toRadians(angle)) * (edge.y - center.y);
        return new Point((int) xRot, (int) yRot);
    }

    private void draw() {
        if(getHolder().getSurface().isValid()) {
            Canvas canvas = getHolder().lockCanvas();

            canvas.drawBitmap(backgroundOne.background, backgroundOne.x, backgroundOne.y, paint);
            canvas.drawBitmap(backgroundTwo.background, backgroundTwo.x, backgroundTwo.y, paint);

            groundTop = player.y + player.height - 30;
            Rect rect = new Rect(0, groundTop, 200, screenY);
            canvas.drawRect(rect, paint);

            Rect finish = new Rect(700, groundTop, 1000, screenY);
            canvas.drawRect(finish, paint);

            paint.setStrokeWidth(10);
            canvas.drawLine(195, groundTop, lineTop.x, lineTop.y, paint);

            canvas.drawBitmap(player.getPlayer(), player.x, player.y, paint);

            getHolder().unlockCanvasAndPost(canvas);
        }
    }

    private void sleep() {
        try {
            Thread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume() {
        isPlaying = true;
        thread = new Thread(this);
        thread.start();
    }

    public void pause() {
        try {
            isPlaying = false;
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(!isFalling && !isLyingDown) {

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    Toast.makeText(getContext(), "Hello Down", Toast.LENGTH_SHORT).show();
                    isGrowing = true;
                    break;

                case MotionEvent.ACTION_UP:
                    Toast.makeText(getContext(), "Hello UP", Toast.LENGTH_SHORT).show();
                    isGrowing = false;
                    isFalling = true;
                    break;
            }

        }

        return true;
    }
}
