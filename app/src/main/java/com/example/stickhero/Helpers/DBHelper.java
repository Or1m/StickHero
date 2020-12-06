package com.example.stickhero.Helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.stickhero.Customs.ShopItem;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "SHOPITEMS.db";
    public static final String TABLE_NAME = "items";

    public static final String ITEM_COLUMN_ID    = "id";
    public static final String ITEM_COLUMN_NAME  = "name";
    public static final String ITEM_COLUMN_PRICE = "price";
    public static final String ITEM_COLUMN_IMGID = "imgID";

    public DBHelper(Context context)  {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" + ITEM_COLUMN_ID + " INTEGER PRIMARY KEY, " + ITEM_COLUMN_NAME + " TEXT, " + ITEM_COLUMN_PRICE + " INTEGER, " + ITEM_COLUMN_IMGID + " INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertItem(ShopItem item)  {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(ITEM_COLUMN_NAME, item.getName());
        contentValues.put(ITEM_COLUMN_PRICE, item.getPrice());
        contentValues.put(ITEM_COLUMN_IMGID, item.getImgID());

        long insertedId = db.insert(TABLE_NAME, null, contentValues);
        return insertedId != -1;
    }

    public boolean deleteItem(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, ITEM_COLUMN_ID + "= " + id, null) > 0;
    }


    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("select * from " + TABLE_NAME + " where " + ITEM_COLUMN_ID + "= " + id + "", null);
    }

    public boolean updateItem(ShopItem item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(ITEM_COLUMN_NAME, item.getName());
        contentValues.put(ITEM_COLUMN_PRICE, item.getPrice());
        contentValues.put(ITEM_COLUMN_IMGID, item.getImgID());

        return db.update(TABLE_NAME, contentValues, ITEM_COLUMN_ID + "= " + item.getID(), null) > 0;
    }

    public ArrayList<ShopItem> getItemList() {
        ArrayList<ShopItem> arrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res =  db.rawQuery( "SELECT * FROM " + TABLE_NAME, null );

        res.moveToFirst();
        while(!res.isAfterLast()){
            ShopItem item = new ShopItem();
            item.setID(res.getInt(0));
            item.setName(res.getString(res.getColumnIndex(ITEM_COLUMN_NAME)));
            item.setPrice(res.getInt(res.getColumnIndex(ITEM_COLUMN_PRICE)));
            item.setImgID(res.getInt(res.getColumnIndex(ITEM_COLUMN_IMGID)));

            arrayList.add(item);
            res.moveToNext();
        }

        res.close();
        return arrayList;
    }

    public int removeAll()  {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "1", null);
    }
}
