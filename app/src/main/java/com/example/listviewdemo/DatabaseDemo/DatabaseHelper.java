package com.example.listviewdemo.DatabaseDemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";


    public DatabaseHelper(@Nullable Context context) {
        super(context, Constants.DATABASE_NAME, null, Constants.VERSION_CODE);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建时的回调
        Log.d(TAG,"创建数据库...");
        //创建字段
        //sql: create table table_name(id integer,name varchar,age integer,salary integer);
        String sql = "create table "+Constants.TABLE_NAME+"(id integer,name varchar,age integer,salary integer)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //升级数据库时的回调
        Log.d(TAG,"由于app版本升级，开始升级数据库...");

        String sql;
        switch (oldVersion) {

            case 1:
                //添加phone 字段  add 前面要加空格 否则会报错
                sql = "alter table "+Constants.TABLE_NAME+ " add phone integer";
                db.execSQL(sql);
                Log.d(TAG,"升级到数据库2...");
            case 2:
                //添加address 字段
                sql = "alter table "+Constants.TABLE_NAME+" add address varchar";
                db.execSQL(sql);
                Log.d(TAG,"升级到数据库3...");
            case 3:

        }

    }
}
