package com.example.stickhero.Activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stickhero.Alerts;
import com.example.stickhero.Fragments.MySettingsFragment;
import com.example.stickhero.R;

public class MainActivity extends BaseActivity {
    ImageButton play;
    ImageButton about;
    ImageButton sound;
    ImageButton settings;
    ImageButton shop;
    ImageButton reset;

    TextView counter;

    MySettingsFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        play = findViewById(R.id.play);
        about = findViewById(R.id.about);
        sound = findViewById(R.id.sound);
        settings = findViewById(R.id.settings);
        shop = findViewById(R.id.shop);
        counter = findViewById(R.id.counter);
        reset = findViewById(R.id.resetButton);

        muted = sharedpreferences.getBoolean(isMuted, false);
        counter.setText(String.valueOf(sharedpreferences.getInt(chocolates, 0)));

        if(muted)
            sound.setColorFilter(Color.argb(100, 0, 0, 0), PorterDuff.Mode.SRC_ATOP);

        setUpListeners();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setUpListeners() {

        play.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (processEvent(view, motionEvent, play)) {
                    play.clearColorFilter();
                    play.invalidate();
                    if (!muted)
                        mediaPlayer.start();

                    Intent gameIntent = new Intent(MainActivity.this, GameActivity.class);
                    startActivity(gameIntent);
                }
                return true;
            }
        });

        about.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (processEvent(view, motionEvent, about)) {
                    about.clearColorFilter();
                    about.invalidate();
                    if(!muted)
                        mediaPlayer.start();

                    Intent aboutIntent = new Intent(MainActivity.this, AboutActivity.class);
                    startActivity(aboutIntent);
                }
                return true;
            }
        });

        sound.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (processEvent(view, motionEvent, sound)) {
                    sound.clearColorFilter();
                    sound.invalidate();

                    muted = !muted;

                    if(!muted)
                        mediaPlayer.start();
                    else
                        sound.setColorFilter(Color.argb(100, 0, 0, 0), PorterDuff.Mode.SRC_ATOP);

                }
                return true;
            }
        });

        settings.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (processEvent(view, motionEvent, settings)) {
                    settings.clearColorFilter();
                    settings.invalidate();
                    if(!muted)
                        mediaPlayer.start();

                    fragment = new MySettingsFragment(sharedpreferences.getInt(score, 0));
                    fragment.show(getSupportFragmentManager(), "MyFragment");
                }
                return true;
            }
        });
        shop.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (processEvent(view, motionEvent, shop)) {
                    shop.clearColorFilter();
                    shop.invalidate();
                    if(!muted)
                        mediaPlayer.start();
                }
                return true;
            }
        });
}

    @Override
    public void onBackPressed() {
        AlertDialog alert = Alerts.CreateMainMenuAlert( this);

        alert.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        counter.setText(String.valueOf(sharedpreferences.getInt(chocolates, 0)));
    }

    public void setUpReset(View view) {
        sharedpreferences.edit().remove(score).apply();
        fragment.redrawScore(sharedpreferences.getInt(score, 0));
    }
}