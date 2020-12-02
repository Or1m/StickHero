package com.example.stickhero.Activities;

import android.os.Bundle;

import com.example.stickhero.GameView;
import com.example.stickhero.R;

public class GameActivity extends BaseActivity {

    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        gameView = findViewById(R.id.gameView);
        gameView.setActivity(this);
        gameView.setMuted(muted);
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView.onPause();
    }
}