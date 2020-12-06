package com.example.stickhero.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.ListView;

import com.example.stickhero.Customs.MyListView;
import com.example.stickhero.Customs.ShopItem;
import com.example.stickhero.Helpers.Alerts;
import com.example.stickhero.Helpers.DBHelper;
import com.example.stickhero.R;

public class ShopActivity extends AppCompatActivity {

    private ListView listView;

    private ShopItem[] defaultShopItems = {
            new ShopItem("Girl", 15, R.drawable.girl),
            new ShopItem("Ninja", 30, R.drawable.ninja),
            new ShopItem("Zombie boy", 100, R.drawable.zombie_male),
            new ShopItem("Zombie girl", 150, R.drawable.zombie_female),
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        DBHelper db = new DBHelper(this);

        if(db.getItemList().size() == 0) {
            for(ShopItem item : defaultShopItems)
                db.insertItem(item);
        }

        ShopItem[] items = db.getItemList().toArray(new ShopItem[0]);

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