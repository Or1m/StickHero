package com.example.stickhero;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.stickhero.Behaviour.Drawable;
import com.example.stickhero.GameClasses.Background;
import com.example.stickhero.GameClasses.Chocolate;
import com.example.stickhero.GameClasses.DestinationGround;
import com.example.stickhero.GameClasses.Ground;
import com.example.stickhero.GameClasses.Player;
import com.example.stickhero.GameClasses.Stick;

import java.util.Set;

@SuppressLint("ViewConstructor")
public class GameView extends SurfaceView implements Runnable {

    private Paint paint;
    private Paint textPaint;
    private Canvas canvas;

    private Thread thread;
    private boolean isRunning;

    private Background[] backgrounds;
    private Player player;
    private Stick stick;
    private Ground ground;
    private DestinationGround dest;
    private Chocolate chocolate;

    private Activity activity;

    private long lastTime;

    private int score;
    private int chocolates;

    private boolean muted;

    MediaPlayer mp;

    Drawable[] drawables;

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
        this.textPaint = new Paint();
        this.textPaint.setTextSize(150);
        this.textPaint.setColor(Color.WHITE);
        textPaint.setTextAlign(Paint.Align.CENTER);

        this.ground = new Ground();
        this.player = Player.getInstance(ground, getResources());

        this.stick = new Stick(this.ground);

        this.dest = new DestinationGround();

        this.chocolate = new Chocolate(getResources(), dest.getStartX(), ground.getRight());

        this.backgrounds = new Background[] { new Background(getResources()), new Background(getResources()) };
        this.backgrounds[1].setX(SettingsManager.getInstance().getScreenX());

        drawables = new Drawable[]{ backgrounds[0], backgrounds[1], ground, dest, stick, player };
    }

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

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void run() {
        lastTime = System.nanoTime();

        while (isRunning) {
            long time = System.nanoTime();
            int deltaTime = (int) ((time - lastTime) / 1000000);
            lastTime = time;

            update(deltaTime);
            draw();
            sleep();

            if(player.isScored()) {
                score++;
                mp = MediaPlayer.create(getContext(), R.raw.dest);
                mp.start();
                player.setScored(false);

                activity.runOnUiThread(updateScoreRunnable);
            }

            if(player.touch(chocolate) && !player.isChocolated()) {
                chocolates++;
                mp = MediaPlayer.create(getContext(), R.raw.pick);
                mp.start();
                player.setChocolated(true);
                chocolate.remove();
                activity.runOnUiThread(updateChocolateRunnable);
            }
        }
    }

    private void update(int deltaTime) {
        for (Background b : this.backgrounds)
            b.update(deltaTime);

        stick.update(deltaTime);
        ground.update(deltaTime);
        dest.update(deltaTime);

        player.update(stick, dest, backgrounds, deltaTime);
    }

    private void draw() {
        if(getHolder().getSurface().isValid()) {
            canvas = getHolder().lockCanvas();

            for(Drawable d : drawables)
                d.draw(canvas, paint);

            chocolate.draw(canvas, paint);

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
        //if(!stick.isFalling() && !stick.isLyingDown()) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    Toast.makeText(getContext(), "Down", Toast.LENGTH_SHORT).show();

                    if(!player.isWalking())
                        stick.setGrowing(true);
                    else
                        player.flip();
                    return true;

                case MotionEvent.ACTION_UP:
                    Toast.makeText(getContext(), "Up", Toast.LENGTH_SHORT).show();

                    if(!player.isWalking()) {
                        stick.setGrowing(false);
                        stick.setFalling(true);
                    }

                    return true;
            }
        //}

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


    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void setMuted(boolean muted) {
        this.muted = muted;
    }
}
