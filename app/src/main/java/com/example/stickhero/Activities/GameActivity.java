package com.example.stickhero.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.example.stickhero.GameView;
import com.example.stickhero.R;

import java.util.Objects;

public class GameActivity extends BaseActivity {

    private GameView gameView;

    private TextView chocoView, scoreView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        gameView = findViewById(R.id.gameView);
        gameView.setActivity(this);
        gameView.setMuted(muted);

        chocoView = findViewById(R.id.chocoView);
        scoreView = findViewById(R.id.scoreVIew);
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

        SharedPreferences.Editor editor = sharedpreferences.edit();

        int oldScore = sharedpreferences.getInt(score, 0);
        int newScore = Integer.parseInt(scoreView.getText().toString());
        if(newScore > oldScore)
            editor.putInt(score, newScore);

        int oldChocolates = sharedpreferences.getInt(chocolates, 0);
        int newChocolates = Integer.parseInt(chocoView.getText().toString());
        editor.putInt(chocolates, oldChocolates + newChocolates);

        editor.putBoolean(isMuted, muted);
        editor.apply();
    }
}