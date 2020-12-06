package com.example.stickhero.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import com.example.stickhero.Customs.GameView;
import com.example.stickhero.Managers.SaveLoadManager;
import com.example.stickhero.R;
import com.example.stickhero.Customs.ShakeDetector;

public class GameActivity extends BaseActivity {

    //region Private Variables
    private GameView gameView;
    private TextView chocoView, scoreView;

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private ShakeDetector shakeDetector;
    //endregion

    //region Creating and Initialization
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        chocoView = findViewById(R.id.chocoView);
        scoreView = findViewById(R.id.scoreVIew);
        gameView  = findViewById(R.id.gameView);

        gameView.setActivity(this);
        gameView.setMuted(muted);

        initShakeDetector();
    }

    private void initShakeDetector() {
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        shakeDetector = new ShakeDetector();

        shakeDetector.setOnShakeListener(count -> gameView.onShake());
    }
    //endregion

    //region Activity Overrides
    @Override
    protected void onResume() {
        super.onResume();
        gameView.onResume();

        sensorManager.registerListener(shakeDetector, accelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView.onPause();

        sensorManager.unregisterListener(shakeDetector);

        updateSharedPrefs();
    }
    //endregion

    private void updateSharedPrefs() {
        SaveLoadManager.getInstance().updateSharedPrefs(
                Integer.parseInt(scoreView.getText().toString()),
                Integer.parseInt(chocoView.getText().toString()), muted);
    }


    public GameView getGameView() {
        return gameView;
    }
}