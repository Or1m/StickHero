package com.example.stickhero.Activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageButton;

import com.example.stickhero.R;

public class AboutActivity extends BaseActivity {

    ImageButton back;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        back = findViewById(R.id.back);

        back.setOnTouchListener((view, motionEvent) -> {
            if (processEvent(view, motionEvent, back)) {
                back.clearColorFilter();
                back.invalidate();
                if(!muted)
                    mediaPlayer.start();

                finish();
            }
            return true;
        });
    }
}