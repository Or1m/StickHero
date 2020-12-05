package com.example.stickhero;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.stickhero.Behaviour.Collidable;
import com.example.stickhero.Behaviour.Drawable;
import com.example.stickhero.GameClasses.Background;
import com.example.stickhero.GameClasses.Chocolate;
import com.example.stickhero.GameClasses.DestinationGround;
import com.example.stickhero.GameClasses.Ground;
import com.example.stickhero.GameClasses.Player;
import com.example.stickhero.GameClasses.Stick;

@SuppressLint("ViewConstructor")
public class GameView extends SurfaceView implements Runnable {

    //region Private Variables
    private Paint paint;
    private Activity activity;
    private Thread thread;

    private boolean isRunning;
    private boolean muted;

    private int score;
    private int chocolates;

    Drawable[] drawables;
    //endregion

    //region Constructors & Init
    public GameView(Context context) {
        super(context);
        init(context);
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GameView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }


    private void init(Context context) {
        this.paint = new Paint();

        drawables = new Drawable[7];
        drawables[0] = new Background(getResources());
        drawables[1] = new Background(getResources());
        drawables[2] = new Ground();
        drawables[3] = Player.getInstance((Ground) drawables[2], getResources());
        drawables[4] = new DestinationGround();
        drawables[5] = new Chocolate(getResources(), ((DestinationGround)drawables[4]).getStartX(), ((Ground) drawables[2]).getRight());
        drawables[6] = new Stick((Ground) drawables[2]);

        ((Background) drawables[1]).setX(SettingsManager.getInstance().getScreenX());
    }
    //endregion

    //region Update UI Methods
    final Runnable updateScoreRunnable = new Runnable() {
        @Override
        public void run() {
            TextView t = activity.findViewById(R.id.scoreVIew);
            t.setText(String.valueOf(score));
            thread.interrupt();
        }
    };

    final Runnable updateChocolateRunnable = new Runnable() {
        @Override
        public void run() {
            TextView t = activity.findViewById(R.id.chocoView);
            t.setText(String.valueOf(chocolates));
            thread.interrupt();
        }
    };

    final Runnable showDeadDialog = new Runnable() {
        @Override
        public void run() {
            AlertDialog alert = Alerts.CreateGameAlert(activity, thread);
            alert.show();
        }
    };
    //endregion

    //region GameLoop methods
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void run() {
        long time, lastTime = System.nanoTime();
        int deltaTime;

        while (isRunning) {
            time = System.nanoTime();
            deltaTime = (int) ((time - lastTime) / 1000000);
            lastTime = time;

            update(deltaTime);
            draw();
            sleep();

            if(Player.getInstance().isScored())
                handleScore();

            if(Player.getInstance().isDead())
                handleDead();

            if(Player.getInstance().touch((Collidable) drawables[5]) && !Player.getInstance().isChocolated())
                handleChocos();
        }
    }

    private void update(int deltaTime) {
        for(Drawable d : drawables)
            d.update(deltaTime, (Stick) drawables[6], (DestinationGround) drawables[4]);
    }

    private void draw() {
        if(getHolder().getSurface().isValid()) {
            Canvas canvas = getHolder().lockCanvas();

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
    //endregion

    //region Handlers and Events
    private void handleScore() {
        score++;

        if(!muted)
            MediaPlayer.create(getContext(), R.raw.dest).start();

        Player.getInstance().setScored(false);

        activity.runOnUiThread(updateScoreRunnable);
    }

    private void handleChocos() {
        chocolates++;

        if(!muted)
            MediaPlayer.create(getContext(), R.raw.pick).start();

        Player.getInstance().setChocolated(true);
        ((Chocolate) drawables[5]).remove();
        activity.runOnUiThread(updateChocolateRunnable);
    }

    private void handleDead() {
        activity.runOnUiThread(showDeadDialog);
        onPause();
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //if(!stick.isFalling() && !stick.isLyingDown()) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    Toast.makeText(getContext(), "Down", Toast.LENGTH_SHORT).show();

                    if(!Player.getInstance().isWalking())
                        ((Stick) drawables[6]).setGrowing(true);
                    else
                        Player.getInstance().flip();
                    return true;

                case MotionEvent.ACTION_UP:
                    Toast.makeText(getContext(), "Up", Toast.LENGTH_SHORT).show();

                    if(!Player.getInstance().isWalking()) {
                        ((Stick) drawables[6]).setGrowing(false);
                        ((Stick) drawables[6]).setFalling(true);
                    }

                    return true;
            }
        //}

        return true;
    }
    //endregion

    //region Service Methods
    public void onResume() {
        isRunning = true;
        Player.getInstance().reset();

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

    //region Setters
    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void setMuted(boolean muted) {
        this.muted = muted;
    }
    //endregion
}
