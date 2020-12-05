package com.example.stickhero.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Debug;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.stickhero.R;

public class MySettingsFragment extends DialogFragment {

    int hScore;
    TextView score;

    public MySettingsFragment(int score) {
        hScore = score;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_settings, container,false);
        score = view.findViewById(R.id.textHigh);
        score.setText(String.valueOf(hScore));
        return view;
    }

    public void redrawScore(int newScore) {
        hScore = newScore;
        score.setText(String.valueOf(hScore));
    }
}
