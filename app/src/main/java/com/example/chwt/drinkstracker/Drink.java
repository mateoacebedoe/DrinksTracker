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

    public String type;
    public int quantity_in_oz;

    public Drink(){
        type = "water";
        quantity_in_oz = 8;
    }

    public long save(Context context){

        DrinkDbHelper drinkDbHelper = new DrinkDbHelper(context);
        SQLiteDatabase db = drinkDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DrinkContract.Drink.COLUMN_NAME_TYPE, type);
        values.put(DrinkContract.Drink.COLUMN_NAME_QUANTITY, quantity_in_oz);
        values.put(DrinkContract.Drink.COLUMN_NAME_TIMESTAMP,
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

        long newDrinkId = db.insert(
                DrinkContract.Drink.TABLE_NAME,
                null,
                values
        );
        return newDrinkId;
    }

    public static String getLastDrink(Context context){
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
        int quantityColumnIndex =  cursor.getColumnIndex(DrinkContract.Drink.COLUMN_NAME_QUANTITY);
        int timestampColumnIndex = cursor.getColumnIndex(DrinkContract.Drink.COLUMN_NAME_TIMESTAMP);
        int quantityOfLastDrinkInt = convertOzToCups(cursor.getInt(quantityColumnIndex));
        Integer quantityOfLastDrinkInteger = quantityOfLastDrinkInt;
        String quantityOfLastDrink = quantityOfLastDrinkInteger.toString();
        String timeStampOfLastDrink = cursor.getString(timestampColumnIndex);

        return "Quantity: " + quantityOfLastDrink + ", \n time: " + timeStampOfLastDrink;
    }

    private static int convertOzToCups(int oz){
        return oz / 8;
    }
}
