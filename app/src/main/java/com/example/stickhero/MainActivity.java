package com.example.stickhero;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends BaseActivity {
    ImageButton play;
    ImageButton about;
    ImageButton sound;
    ImageButton settings;
    ImageButton shop;

    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        play = findViewById(R.id.play);
        about = findViewById(R.id.about);
        sound = findViewById(R.id.sound);
        settings = findViewById(R.id.settings);
        shop = findViewById(R.id.shop);

        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.click);

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
                    mediaPlayer.start();
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
                    mediaPlayer.start();
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
                    mediaPlayer.start();
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
                    mediaPlayer.start();
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
                    mediaPlayer.start();
                }
                return true;
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Exit Stick Hero");
        builder.setMessage("Are you sure you wanna to quit this masterpiece?");

        builder.setPositiveButton("Yes, just quit it fast", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });

        builder.setNegativeButton("No, sorry my mistake", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }
}