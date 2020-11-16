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

import com.example.stickhero.GameClasses.Background;
import com.example.stickhero.GameClasses.DestinationGround;
import com.example.stickhero.GameClasses.Player;
import com.example.stickhero.GameClasses.Stick;

@SuppressLint("ViewConstructor")
public class GameView extends SurfaceView implements Runnable {

    private final double gravity = 0.2;
    private final int groundWidth = 200;
    private final int groundTop;
    private final int screenX, screenY;

    private Paint paint;
    private Thread thread;
    private boolean isRunning;

    private Background[] background;
    private Player player;
    private Stick stick;
    private DestinationGround dest;

    private double alpha = 0;


    public GameView(Context context, int screenX, int screenY) {
        super(context);

        this.screenX = screenX;
        this.screenY = screenY;

        this.paint = new Paint();

        this.player = new Player(this.screenY, getResources());
        this.groundTop = player.getPlayerBottom();

        this.stick = new Stick(new Point(groundWidth, groundTop), new Point(groundWidth, groundTop), 10);
        this.dest = new DestinationGround(700, 1000); //TODO bude sa generovat za chodu

        this.background = new Background[] {
                new Background(this.screenX, this.screenY, getResources()),
                new Background(this.screenX, this.screenY, getResources())
        };

        this.background[1].setX(this.screenX);
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
        Point stickEnd = stick.getEnd();

        //TODO cyklus
        if(player.isWalking()) {
            background[0].moveX(-10);
            background[1].moveX(-10);
        }

        if(background[0].isNotVisible())
            background[0].setX(screenX);

        if(background[1].isNotVisible())
            background[1].setX(screenX);

        if(stick.isGrowing())
            stick.moveEndY(-20);

        if(stickEnd.y > groundTop) {
            stick.setEndY(groundTop);

            stick.setFalling(false);
            stick.setLyingDown(true);
        }

        if(stick.isFalling()) {
            stick.rotate(alpha);
            alpha += gravity;
        }

        if(stick.isLyingDown() && dest.isInBounds(stickEnd.x) && player.getX() < stickEnd.x) {
            player.setWalking(true);
            player.moveX(10);
        }

        if(player.getX() >= stickEnd.x)
            player.setWalking(false);
    }



    private void draw() {
        Point stickEnd = stick.getEnd();

        if(getHolder().getSurface().isValid()) {
            Canvas canvas = getHolder().lockCanvas();

            //TODO cyklus
            canvas.drawBitmap(background[0].getBackground(), background[0].getX(), background[0].getY(), paint);
            canvas.drawBitmap(background[1].getBackground(), background[1].getX(), background[1].getY(), paint);

            //TODO lepsie vymysliet rectangle asi posielat canvas a metodu draw v triedach
            Rect rect = new Rect(0, groundTop, groundWidth, screenY);
            canvas.drawRect(rect, paint);

            Rect finish = new Rect(dest.getStartX(), groundTop, dest.getEndX(), screenY);
            canvas.drawRect(finish, paint);

            paint.setStrokeWidth(stick.getWidth());
            canvas.drawLine(stick.getStart().x, groundTop, stickEnd.x, stickEnd.y, paint);

            canvas.drawBitmap(player.getPlayer(), player.getX(), player.getY(), paint);

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


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(!stick.isFalling() && !stick.isLyingDown()) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    Toast.makeText(getContext(), "Down", Toast.LENGTH_SHORT).show();
                    stick.setGrowing(true);
                    break;

                case MotionEvent.ACTION_UP:
                    Toast.makeText(getContext(), "Up", Toast.LENGTH_SHORT).show();
                    stick.setGrowing(false);
                    stick.setFalling(true);
                    break;
            }
        }

        return true;
    }


    public void onResume() {
        isRunning = true;
        thread = new Thread(this);
        thread.start();
    }

    public void onPause() {
        try {
            isRunning = false;
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
