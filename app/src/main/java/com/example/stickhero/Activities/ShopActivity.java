package com.example.stickhero.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.example.stickhero.Customs.MyListView;
import com.example.stickhero.Customs.ShopItem;
import com.example.stickhero.Helpers.Alerts;
import com.example.stickhero.Managers.SaveLoadManager;
import com.example.stickhero.R;

public class ShopActivity extends AppCompatActivity {

    ListView listView;

    ShopItem[] items = {
            new ShopItem("Girl", 0, R.drawable.girl),
            new ShopItem("Ninja", 0, R.drawable.ninja),
            new ShopItem("Zombie boy", 0, R.drawable.zombie_male),
            new ShopItem("Zombie girl", 0, R.drawable.zombie_female),
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        listView = findViewById(R.id.listShop);
        MyListView myListView = new MyListView(this, items);
        listView.setAdapter(myListView);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            ShopItem item = (ShopItem) listView.getItemAtPosition(position);

            AlertDialog alert = Alerts.createShopAlert(item, this);
            alert.show();
        });
    }
}