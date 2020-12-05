package com.example.stickhero;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.example.stickhero.Activities.GameActivity;

public class Alerts {

    public static AlertDialog CreateMainMenuAlert(final Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Exit Stick Hero");
        builder.setMessage("Are you sure you wanna to quit this masterpiece?");

        builder.setPositiveButton("Yes, just quit it fast", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                activity.finish();
            }
        });

        builder.setNegativeButton("No, sorry my mistake", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        return builder.create();
    }

    public static AlertDialog CreateGameAlert(final Activity activity, final Thread thread) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("You are dead");
        builder.setMessage("Exit to main menu?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                thread.interrupt();
                activity.finish();
            }
        });

        builder.setNegativeButton("One more try and I'll go to sleep", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                ((GameActivity)activity).getGameView().onResume();
            }
        });

        return builder.create();
    }
}
