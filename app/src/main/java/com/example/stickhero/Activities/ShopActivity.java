package com.example.stickhero.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.example.stickhero.Customs.MyListView;
import com.example.stickhero.R;

public class ShopActivity extends AppCompatActivity {

    ListView listView;

    String[] names  = {"Girl", "Ninja", "Zombie male", "Zombie female"};
    int[] prices    = {15 , 50, 150, 300};
    int[] imgIDs    = {R.drawable.girl, R.drawable.ninja, R.drawable.zombie_male, R.drawable.zombie_female};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        listView = findViewById(R.id.listShop);
        MyListView myListView = new MyListView(this, names, prices, imgIDs);
        listView.setAdapter(myListView);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            String vvv = (String) listView.getItemAtPosition(position);
            Toast.makeText(getApplicationContext(), vvv, Toast.LENGTH_SHORT).show();
        });
    }
}