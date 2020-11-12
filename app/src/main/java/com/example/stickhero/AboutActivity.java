package com.example.stickhero;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;

public class AboutActivity extends BaseActivity {
    ImageButton back;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        back = findViewById(R.id.back);

        back.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (processEvent(view, motionEvent, back)) {
                    back.clearColorFilter();
                    back.invalidate();
                    if(!muted)
                        mediaPlayer.start();

                    finish();
                }
                return true;
            }
        });
    }
}