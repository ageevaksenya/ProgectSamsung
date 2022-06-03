package com.example.myapplication111;



import android.app.DownloadManager;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

public class DBSecurities implements BaseColumns {

    public static List<String> list=new ArrayList<>();
    public static final String TABLE_NAME6 = "persons";
    public static final String TABLE_NAME = "securities";
    public static final String TABLE_NAME5 = "my_securities";
    public static final String TABLE_NAME2="bonds";
    public static final String TABLE_NAME3="futures";
    public static final String TABLE_NAME4="currency";
    public static final String COLUMN_ID = BaseColumns._ID;
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PRICE ="price";
    public static final String  COLUMN_IMAGE ="image";
    public static final String COLUMN_COMPANY = "company";
    public static final String COLUMN_COUNT="count";
    private static SQLiteDatabase mDataBase;


    private OpenHelper mDbHelper;


    public DBSecurities(Context context) {
        OpenHelper mOpenHelper = new OpenHelper(context);
        mDataBase = mOpenHelper.getWritableDatabase();
    }




    public void close() {
        mDbHelper.close();
    }


    public  static long insert(String company, byte[] image, int price,String name) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_PRICE, price );
        cv.put(COLUMN_IMAGE,image );
        cv.put(COLUMN_COMPANY, company);
        cv.put(COLUMN_NAME, name);
        return mDataBase.insert(TABLE_NAME, null, cv);
    }
    public  static long insert1(String company, byte[] image, int price,String name) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_PRICE, price );
        cv.put(COLUMN_IMAGE,image );
        cv.put(COLUMN_COMPANY, company);
        cv.put(COLUMN_NAME, name);
        return mDataBase.insert(TABLE_NAME2, null, cv);
    }
    public  static long insert2(String company, byte[]  image, int price,String name) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_PRICE, price );
        cv.put(COLUMN_IMAGE,image );
        cv.put(COLUMN_COMPANY, company);
        cv.put(COLUMN_NAME, name);
        return mDataBase.insert(TABLE_NAME3, null, cv);
    }
    public  static long insert3(String company, byte[] image, int price,String name) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_PRICE, price );
        cv.put(COLUMN_IMAGE,image );
        cv.put(COLUMN_COMPANY, company);
        cv.put(COLUMN_NAME, name);
        return mDataBase.insert(TABLE_NAME4, null, cv);
    }
    public  static long insert5(String company, byte[] image, int price,String name,int count) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_PRICE, price );
        cv.put(COLUMN_IMAGE,image );
        cv.put(COLUMN_COMPANY, company);
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_COUNT,count);
        return mDataBase.insert(TABLE_NAME5, null, cv);
    }
    public Cursor fetchRecordsByQuery1(String query) {
        return mDataBase.query(true, TABLE_NAME, new String[] { COLUMN_ID ,COLUMN_COMPANY,
                        COLUMN_NAME, COLUMN_PRICE}, COLUMN_NAME + " LIKE" + "'%" + query + "%'", null,
                null, null, null, null);
    }
    public Cursor fetchRecordsByQuery2(String query) {
        return mDataBase.query(true, TABLE_NAME2, new String[] { COLUMN_ID ,COLUMN_COMPANY,
                        COLUMN_NAME, String.valueOf(COLUMN_PRICE)}, COLUMN_NAME + " LIKE" + "'%" + query + "%'", null,
                null, null, null, null);
    }
    public List<String> fetchRecordsByQuery6(String query) {
        List<String> list_search =new ArrayList<>();
        for (int i = 0; i <list.size() ; i++) {
            if(list.get(i).contains(query)) list_search.add(list.get(i));
        }
        return list_search;
    }
    public Cursor fetchRecordsByQuery3(String query) {
        return mDataBase.query(true, TABLE_NAME3, new String[] { COLUMN_ID ,COLUMN_COMPANY,
                        COLUMN_NAME, COLUMN_PRICE}, COLUMN_NAME + " LIKE" + "'%" + query + "%'", null,
                null, null, null, null);
    }
    public Cursor fetchRecordsByQuery4(String query) {
        return mDataBase.query(true, TABLE_NAME4, new String[] { COLUMN_ID ,COLUMN_COMPANY,
                        COLUMN_NAME, COLUMN_PRICE}, COLUMN_NAME + " LIKE" + "'%" + query + "%'", null,
                null, null, null, null);
    }
    public Cursor fetchRecordsByQuery5(String query) {
        return mDataBase.query(true, TABLE_NAME5, new String[] { COLUMN_ID ,COLUMN_COMPANY,
                        COLUMN_NAME, COLUMN_PRICE}, COLUMN_NAME + " LIKE" + "'%" + query + "%'", null,
                null, null, null, null);
    }



    public static long update(Cell md) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, md.getName());
        cv.put(COLUMN_IMAGE, md.getImage());
        cv.put(COLUMN_COMPANY, md.getCompany());
        cv.put(COLUMN_PRICE, md.getPrice());
        return mDataBase.update(TABLE_NAME, cv, COLUMN_ID + " = ?", new String[]{String.valueOf(md.getId())});

    }
    public static long update2(Cell md) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, md.getName());
        cv.put(COLUMN_IMAGE, md.getImage());
        cv.put(COLUMN_COMPANY, md.getCompany());
        cv.put(COLUMN_PRICE, md.getPrice());
        return mDataBase.update(TABLE_NAME2, cv, COLUMN_ID + " = ?", new String[]{String.valueOf(md.getId())});

    }
    public static long update3(Cell md) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, md.getName());
        cv.put(COLUMN_IMAGE, md.getImage());
        cv.put(COLUMN_COMPANY, md.getCompany());
        cv.put(COLUMN_PRICE, md.getPrice());
        return mDataBase.update(TABLE_NAME3, cv, COLUMN_ID + " = ?", new String[]{String.valueOf(md.getId())});

    }
    public static long update4(Cell md) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, md.getName());
        cv.put(COLUMN_IMAGE, md.getImage());
        cv.put(COLUMN_COMPANY, md.getCompany());
        cv.put(COLUMN_PRICE, md.getPrice());
        return mDataBase.update(TABLE_NAME4, cv, COLUMN_ID + " = ?", new String[]{String.valueOf(md.getId())});

    }
    public static long update5(Cell2 md) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, md.getName());
        cv.put(COLUMN_IMAGE, md.getImage());
        cv.put(COLUMN_COMPANY, md.getCompany());
        cv.put(COLUMN_COUNT,md.getCount());
        cv.put(COLUMN_PRICE, md.getPrice());
        return mDataBase.update(TABLE_NAME5, cv, COLUMN_ID + " = ?", new String[]{String.valueOf(md.getId())});

    }







    public static class OpenHelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "dbsecurities.db";
        private static final int DATABASE_VERSION = 1;

        OpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String query = "CREATE TABLE " + DBSecurities.TABLE_NAME + " (" +
                    DBSecurities.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    DBSecurities.COLUMN_COMPANY + " TEXT NOT NULL, " +
                    DBSecurities.COLUMN_NAME + " TEXT NOT NULL, " +
                    DBSecurities.COLUMN_IMAGE + " BLOB NOT NULL, " +
                    DBSecurities.COLUMN_PRICE + " INTEGER NOT NULL DEFAULT 0);";
            db.execSQL(query);
//            String query6 = "CREATE TABLE " + DBSecurities.TABLE_NAME6 + " (" +
//                    DBSecurities.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                    DBSecurities.COLUMN_NAME + " TEXT NOT NULL," +
//                    DBSecurities.COLUMN_IMAGE + " BLOB NOT NULL, " +
//                    DBSecurities.COLUMN_PRICE + "INTEGER NOT NULL DEFAULT 0);";
//            db.execSQL(query);
            String query2 = "CREATE TABLE " + DBSecurities.TABLE_NAME2 + " (" +
                    DBSecurities.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    DBSecurities.COLUMN_COMPANY + " TEXT NOT NULL, " +
                    DBSecurities.COLUMN_NAME + " TEXT NOT NULL," +
                    DBSecurities.COLUMN_IMAGE + " BLOB NOT NULL, " +
                    DBSecurities.COLUMN_PRICE + "INTEGER NOT NULL DEFAULT 0);";
            db.execSQL(query2);
            String query3 = "CREATE TABLE " + DBSecurities.TABLE_NAME3 + " (" +
                    DBSecurities.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    DBSecurities.COLUMN_COMPANY + " TEXT NOT NULL, " +
                    DBSecurities.COLUMN_NAME + " TEXT NOT NULL," +
                    DBSecurities.COLUMN_IMAGE + " BLOB NOT NULL, " +
                    DBSecurities.COLUMN_PRICE + "INTEGER NOT NULL DEFAULT 0);";
            db.execSQL(query3);
            String query4 = "CREATE TABLE " + DBSecurities.TABLE_NAME4 + " (" +
                    DBSecurities.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    DBSecurities.COLUMN_COMPANY + " TEXT NOT NULL, " +
                    DBSecurities.COLUMN_NAME + " TEXT NOT NULL," +
                    DBSecurities.COLUMN_IMAGE + " BLOB NOT NULL, " +
                    DBSecurities.COLUMN_PRICE + " INTEGER NOT NULL DEFAULT 0);";
            db.execSQL(query4);
            String my_query = "CREATE TABLE " + DBSecurities.TABLE_NAME5 + " (" +
                    DBSecurities.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    DBSecurities.COLUMN_NAME + " TEXT NOT NULL," +
                    DBSecurities.COLUMN_IMAGE + " BLOB NOT NULL, " +
                    DBSecurities.COLUMN_PRICE + " INTEGER NOT NULL DEFAULT 0,"+
                    DBSecurities.COLUMN_COUNT + " INTEGER NOT NULL DEFAULT 0);";;
            db.execSQL(my_query);


        }



        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w("SQLite", "Обновляемся с версии " + oldVersion + " на версию " + newVersion);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }
}
