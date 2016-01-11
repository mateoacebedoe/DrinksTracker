package com.example.chwt.drinkstracker;

import android.provider.BaseColumns;

/**
 * Created by mateoacebedo on 12/10/15.
 */
public final class DrinkContract {
    public DrinkContract() {}

    /*Drink table*/
    public static abstract class Drink implements BaseColumns{
        public static String TABLE_NAME = "Drink";
        public static String COLUMN_NAME_TYPE = "type";
        public static String COLUMN_NAME_QUANTITY = "quantity";
        public static String COLUMN_NAME_TIMESTAMP = "timestamp";

        public static final String TEXT_TYPE = " TEXT";
        public static final String VARCHAR = " varchar(255) ";
        public static final String REAL = " REAL ";
        public static final String TIME_STAMP_TYPE = " TIMESTAMP DEFAULT CURRENT_TIMESTAMP";
        public static final String COMMA_SEP = ", ";

        public static final String SQL_CREATE_DRINKS =
                "CREATE TABLE " + Drink.TABLE_NAME + " (" + Drink._ID + " INTEGER PRIMARY KEY, " +
                        Drink.COLUMN_NAME_TYPE + TEXT_TYPE + COMMA_SEP +
                        Drink.COLUMN_NAME_QUANTITY + REAL + COMMA_SEP +
                        Drink.COLUMN_NAME_TIMESTAMP + TIME_STAMP_TYPE +
                        ")";

        public static final String SQL_DROP_DRINKS_TABLE = "DROP TABLE IF EXISTS" + Drink.TABLE_NAME;

        public static String[] getAllColumns(){
            String [] columns = {
                    DrinkContract.Drink._ID,
                    DrinkContract.Drink.COLUMN_NAME_TYPE,
                    DrinkContract.Drink.COLUMN_NAME_QUANTITY,
                    DrinkContract.Drink.COLUMN_NAME_TIMESTAMP
            };
            return columns;
        }
    }
}
