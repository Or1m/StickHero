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
    private Canvas canvas;

    private Thread thread;
    private boolean isRunning;

    private Background[] backgrounds;
    private Player player;
    private Stick stick;
    private DestinationGround dest;


    public GameView(Context context, int screenX, int screenY) {
        super(context);

        this.screenX = screenX;
        this.screenY = screenY;

        this.paint = new Paint();

        this.player = new Player(this.screenY, getResources());
        this.groundTop = player.getPlayerBottom();

        this.stick = new Stick(new Point(groundWidth, groundTop), new Point(groundWidth, groundTop), 10);
        this.dest = new DestinationGround(700, 1000); //TODO bude sa generovat za chodu

        this.backgrounds = new Background[] {
                new Background(this.screenX, this.screenY, getResources()),
                new Background(this.screenX, this.screenY, getResources())
        };

        this.backgrounds[1].setX(this.screenX);
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
        for (Background b : this.backgrounds)
            b.update(player, screenX);

        stick.update(groundTop, gravity);
        player.update(stick, dest);
    }

    private void draw() {
        Point stickEnd = stick.getEnd();

        if(getHolder().getSurface().isValid()) {
            canvas = getHolder().lockCanvas();

            for(Background b : backgrounds) {
                b.draw(canvas, this.paint);
            }

            //TODO lepsie vymysliet rectangle asi posielat canvas a metodu draw v triedach
            //TODO interface na draw a update, zjednotit parametre, mozno singleton na top a y aby sa dali volat v cykle
            //TODO Predok Ground
            Rect rect = new Rect(0, groundTop, groundWidth, screenY);
            canvas.drawRect(rect, paint);

            dest.draw(canvas, paint, groundTop, screenY);

            stick.draw(canvas, paint, groundTop);

            player.draw(canvas, paint);


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

    //region Service Methods
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
    //endregion
}
