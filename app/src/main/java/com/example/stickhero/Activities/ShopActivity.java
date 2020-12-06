package com.example.stickhero.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.example.stickhero.Customs.MyListView;
import com.example.stickhero.Customs.ShopItem;
import com.example.stickhero.R;

public class ShopActivity extends AppCompatActivity {

    ListView listView;

    ShopItem[] items = {
            new ShopItem("Girl", 15, R.drawable.girl),
            new ShopItem("Ninja", 50, R.drawable.ninja),
            new ShopItem("Zombie male", 150, R.drawable.zombie_male),
            new ShopItem("Zombie female", 300, R.drawable.zombie_female),
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
            Toast.makeText(getApplicationContext(), String.valueOf(item.getPrice()), Toast.LENGTH_SHORT).show();

            //TODO zistit pocet cokoladiek u seba asi z preferences nech to netreba posielat a ked je dost tak ulozit do prefs novy sprite ten nacitavat pri vytvarani playera
        });
    }
}