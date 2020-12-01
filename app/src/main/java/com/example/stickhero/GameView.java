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

import com.example.stickhero.Behaviour.Drawable;
import com.example.stickhero.GameClasses.Background;
import com.example.stickhero.GameClasses.DestinationGround;
import com.example.stickhero.GameClasses.Ground;
import com.example.stickhero.GameClasses.Player;
import com.example.stickhero.GameClasses.Stick;

@SuppressLint("ViewConstructor")
public class GameView extends SurfaceView implements Runnable {

    private Paint paint;
    private Canvas canvas;

    private Thread thread;
    private boolean isRunning;

    private Background[] backgrounds;
    private Player player;
    private Stick stick;
    private Ground ground;
    private DestinationGround dest;

    Drawable[] drawables;


    public GameView(Context context) {
        super(context);

        this.paint = new Paint();

        this.ground = new Ground();
        this.player = Player.getInstance(ground, getResources());


        this.stick = new Stick(this.ground);

        this.dest = new DestinationGround();


        this.backgrounds = new Background[] { new Background(getResources()), new Background(getResources()) };
        this.backgrounds[1].setX(SettingsManager.getInstance().getScreenX());

        drawables = new Drawable[]{ backgrounds[0], backgrounds[1], ground, dest, stick, player };
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
            b.update();

        stick.update();
        ground.update();
        dest.update();

        player.update(stick, dest);
    }

    private void draw() {
        if(getHolder().getSurface().isValid()) {
            canvas = getHolder().lockCanvas();

            for(Drawable d : drawables)
                d.draw(canvas, paint);

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
