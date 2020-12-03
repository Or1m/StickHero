package com.example.stickhero.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.example.stickhero.R;

public class BaseActivity extends AppCompatActivity {
    private boolean touchStayedWithinViewBounds;
    MediaPlayer mediaPlayer;

    public static Boolean muted = false;

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String score = "score";
    public static final String chocolates = "chocolates";
    public static final String isMuted = "muted";

    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.click);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
    }

    protected boolean processEvent(View view, MotionEvent motionEvent, ImageView img) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                this.touchStayedWithinViewBounds = true;
                img.setColorFilter(Color.argb(50, 0, 0, 0), PorterDuff.Mode.SRC_ATOP);
                img.invalidate();
                return false;
            }
            case MotionEvent.ACTION_MOVE: {
                if (this.touchStayedWithinViewBounds && !isMotionEventInsideView(view, motionEvent)) {
                    img.clearColorFilter();
                    img.invalidate();
                    this.touchStayedWithinViewBounds = false;
                }
                return false;
            }
            case MotionEvent.ACTION_UP: {
                return this.touchStayedWithinViewBounds;
            }
            case MotionEvent.ACTION_CANCEL:
                img.clearColorFilter();
                img.invalidate();
                return false;
            default:
                return false;
        }
    }
    private boolean isMotionEventInsideView(View view, MotionEvent event) {
        Rect viewRect = new Rect(
                view.getLeft(),
                view.getTop(),
                view.getRight(),
                view.getBottom()
        );
        return viewRect.contains(
                view.getLeft() + (int) event.getX(),
                view.getTop() + (int) event.getY()
        );
    }
}