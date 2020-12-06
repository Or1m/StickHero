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

public class MyListView extends ArrayAdapter<String> {

    private String[] names;
    private int[] prices;
    private int[] imgIDs;

    private Activity context;

    public MyListView(Activity context, String[] names, int[] prices, int[] imgIDs) {
        super(context, R.layout.list_element_layout, names);

        this.context = context;
        this.names = names;
        this.prices = prices;
        this.imgIDs = imgIDs;
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

        viewHolder.imageView.setImageResource(imgIDs[position]);
        viewHolder.textView1.setText(names[position]);
        viewHolder.textView2.setText(String.valueOf(prices[position]));


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

        public ImageView getImageView() {
            return imageView;
        }

        public TextView getTextView1() {
            return textView1;
        }

        public TextView getTextView2() {
            return textView2;
        }
    }
}
