package com.example.stickhero;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.widget.Toast;

@SuppressLint("ViewConstructor")
public class GameView extends SurfaceView implements Runnable {

    private final double gravity = 0.2;
    private final int screenX, screenY;

    private Thread thread;
    private boolean isRunning;

    private Paint paint;
    private Point lineTop;

    private Background[] background;
    private Player player;

    private int groundTop;
    private double alpha = 0;

    boolean isGrowing;
    boolean isFalling;
    boolean isLyingDown;

    public GameView(Context context, int screenX, int screenY) {
        super(context);

        this.screenX = screenX;
        this.screenY = screenY;

        paint = new Paint();
        player = new Player(this.screenY, getResources());

        groundTop = player.y + player.height - 30;

        lineTop = new Point(195, groundTop);

        background = new Background[] {
                new Background(this.screenX, this.screenY, getResources()),
                new Background(this.screenX, this.screenY, getResources())
        };

        background[1].x = this.screenX;
    }

    @Override
    public void run() {
        while (isRunning) {
            update();
            draw();
            sleep();
        }
    }

    private void update() {
        if(player.isWalking()) {
            background[0].x -= 10;
            background[1].x -= 10;
        }

        if(background[0].x + background[0].background.getWidth() < 0) {
            background[0].x = screenX;
        }

        if(background[1].x + background[1].background.getWidth() < 0) {
            background[1].x = screenX;
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
            player.setWalking(true);
            player.x += 10;
        }

        if(player.x >= lineTop.x)
            player.setWalking(false);
    }

    static Point rotateLineClockWise(Point center, Point edge, double angle) {
        double xRot = (int) center.x + Math.cos(Math.toRadians(angle)) * (edge.x - center.x) - Math.sin(Math.toRadians(angle)) * (edge.y - center.y);
        double yRot = (int) center.y + Math.sin(Math.toRadians(angle)) * (edge.x - center.x) + Math.cos(Math.toRadians(angle)) * (edge.y - center.y);
        return new Point((int) xRot, (int) yRot);
    }

    private void draw() {
        if(getHolder().getSurface().isValid()) {
            Canvas canvas = getHolder().lockCanvas();

            canvas.drawBitmap(background[0].background, background[0].x, background[0].y, paint);
            canvas.drawBitmap(background[1].background, background[1].x, background[1].y, paint);

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
        isRunning = true;
        thread = new Thread(this);
        thread.start();
    }

    public void pause() {
        try {
            isRunning = false;
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
