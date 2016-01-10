package com.example.chwt.drinkstracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.security.Timestamp;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by mateoacebedo on 12/10/15.
 */
public class Drink {

    public int id;
    public String type;
    public int quantity_in_oz;
    public String timestamp;

    public Drink(){
        id = -1;
        type = "water";
        quantity_in_oz = 8;
        timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    public Drink(int id, String type, int quantity_in_oz, String timestamp){
        this.type = type;
        this.id = id;
        this.quantity_in_oz = quantity_in_oz;
        this.timestamp = timestamp;
    }

    public long save(Context context){

        DrinkDbHelper drinkDbHelper = new DrinkDbHelper(context);
        SQLiteDatabase db = drinkDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DrinkContract.Drink.COLUMN_NAME_TYPE, type);
        values.put(DrinkContract.Drink.COLUMN_NAME_QUANTITY, quantity_in_oz);
        values.put(DrinkContract.Drink.COLUMN_NAME_TIMESTAMP, timestamp
                );

        long newDrinkId = db.insert(
                DrinkContract.Drink.TABLE_NAME,
                null,
                values
        );

        this.id = (int)newDrinkId;
        return this.id;
    }

    public static Drink getLastDrink(Context context){
        DrinkDbHelper drinkDbHelper = new DrinkDbHelper(context);
        SQLiteDatabase db = drinkDbHelper.getReadableDatabase();

        String[] projection = {
                DrinkContract.Drink._ID,
                DrinkContract.Drink.COLUMN_NAME_TYPE,
                DrinkContract.Drink.COLUMN_NAME_QUANTITY,
                DrinkContract.Drink.COLUMN_NAME_TIMESTAMP
        };

        String selection = null;
        String[] selectionArgs = null;


        Cursor cursor = db.query(
                DrinkContract.Drink.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        cursor.moveToLast();
        int idColumnIndex = cursor.getColumnIndex(DrinkContract.Drink._ID);
        int typeColumnIndex = cursor.getColumnIndex((DrinkContract.Drink.COLUMN_NAME_TYPE));
        int quantityColumnIndex =  cursor.getColumnIndex(DrinkContract.Drink.COLUMN_NAME_QUANTITY);
        int timestampColumnIndex = cursor.getColumnIndex(DrinkContract.Drink.COLUMN_NAME_TIMESTAMP);

        int idOfLastDrink = cursor.getInt(idColumnIndex);
        String typeOfLastDrink = cursor.getString(typeColumnIndex);
        String timestampOfLastDrink = cursor.getString(timestampColumnIndex);
        int quantityOfLastDrink = cursor.getInt(quantityColumnIndex);

        Drink lastDrink = new Drink(idOfLastDrink, typeOfLastDrink, quantityOfLastDrink, timestampOfLastDrink);

        return lastDrink;
        //return "Quantity: " + quantityOfLastDrink + ", \n time: " + timeStampOfLastDrink;
    }

    private static int convertOzToCups(int oz){
        return oz / 8;
    }

    public String toString(){
        Integer id = this.id;
        Integer quantity = this.quantity_in_oz;

        return "id: " + id.toString() + ", type: " + this.type + ", quantity: " + quantity.toString() + ", timestamp: " + this.timestamp;
    }
}
