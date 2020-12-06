package com.example.stickhero.Customs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.stickhero.R;

public class MyListView extends ArrayAdapter<ShopItem> {

    ShopItem[] items;

    private Activity context;

    public MyListView(Activity context, ShopItem[] items) {
        super(context, R.layout.list_element_layout, items);

        this.context = context;
        this.items = items;
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        ViewHolder viewHolder = null;

        if(v == null) {
            LayoutInflater layoutInflater = context.getLayoutInflater();
            v = layoutInflater.inflate(R.layout.list_element_layout, null, true);

            viewHolder = new ViewHolder(v);
            v.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) v.getTag();
        }

        viewHolder.imageView.setImageResource(items[position].getImgID());
        viewHolder.textView1.setText(items[position].getName());
        viewHolder.textView2.setText(String.valueOf(items[position].getPrice()));


        return v;
    }

    public static class ViewHolder {
        ImageView imageView;
        TextView textView1;
        TextView textView2;

        ViewHolder(View v) {
            imageView = v.findViewById(R.id.elementImgView);
            textView1 = v.findViewById(R.id.name);
            textView2 = v.findViewById(R.id.price);
        }
    }
}
