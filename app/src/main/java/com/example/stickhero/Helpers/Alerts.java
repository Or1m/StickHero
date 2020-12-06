package com.example.stickhero.Helpers;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import com.example.stickhero.Activities.GameActivity;
import com.example.stickhero.Customs.ShopItem;
import com.example.stickhero.Managers.SaveLoadManager;

public class Alerts {

    public static AlertDialog createMainMenuAlert(final Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Exit Stick Hero");
        builder.setMessage("Are you sure you wanna to quit this masterpiece?");

        builder.setPositiveButton("Yes, just quit it fast", (dialog, which) -> {
            dialog.dismiss();
            activity.finish();
        });

        builder.setNegativeButton("No, sorry my mistake", (dialog, which) -> dialog.dismiss());

        return builder.create();
    }

    public static AlertDialog createGameAlert(final Activity activity, final Thread thread) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("You are dead");
        builder.setMessage("Exit to main menu?");

        builder.setPositiveButton("Yes", (dialog, which) -> {
            dialog.dismiss();
            thread.interrupt();
            activity.finish();
        });

        builder.setNegativeButton("One more try and I'll go to sleep", (dialog, which) -> {
            dialog.dismiss();
            ((GameActivity)activity).getGameView().onResume();
        });

        return builder.create();
    }

    public static AlertDialog createShopAlert(ShopItem item, Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Buy " + item.getName());
        builder.setMessage("Are you sure you wanna buy " + item.getName() + "? \nPrice is: " + item.getPrice());

        builder.setPositiveButton("Yes", (dialog, which) -> {
            dialog.dismiss();

            if(SaveLoadManager.getInstance().getChocolates() < item.getPrice())
                Toast.makeText(context, "Not enough chocolates", Toast.LENGTH_LONG).show();
            else {
                SaveLoadManager.getInstance().subtractChocolates(item.getPrice());
                SaveLoadManager.getInstance().setCharacter(item.getImgID());

                Toast.makeText(context, "Congratulation, you have new character", Toast.LENGTH_LONG).show();
            }
        });

        builder.setNegativeButton("I changed my mind", (dialog, which) -> dialog.dismiss());

        return builder.create();
    }
}
