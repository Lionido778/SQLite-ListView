package com.example.listviewdemo.DatabaseDemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 *  这个类用于数据库的增删改查
 */
public class Dao {

    private final DatabaseHelper mhelper;

    public Dao(Context context){
        /** 创建数据库 */
        mhelper = new DatabaseHelper(context);

    }

    public void insert(User user){
        SQLiteDatabase db = mhelper.getWritableDatabase();
        String sql= "insert into "+Constants.TABLE_NAME+"(id,name,age,salary) values(?,?,?,?)";
        db.execSQL(sql,new Object[]{user.getId(),user.getName(),user.getAge(),user.getSalary()});

/*        SQLiteDatabase db = mhelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id",user.getId());
        values.put("name",user.getName());
        values.put("age",user.getAge());
        values.put("salary",user.getSalary());
        db.insert(Constants.TABLE_NAME,null,values);*/

        db.close();
    }

    public void delete(){
        SQLiteDatabase db = mhelper.getWritableDatabase();
        String sql = "delete from "+Constants.TABLE_NAME+" where age = 20";
        db.execSQL(sql);
        db.close();
    }

    public void update(){
        SQLiteDatabase db = mhelper.getWritableDatabase();
        String sql = "update "+Constants.TABLE_NAME+ " set salary =3000 where age = 21 ";
        db.execSQL(sql);
        db.close();
    }

    public List<User> query(){
        List<User> users = new ArrayList<User>();
        SQLiteDatabase db = mhelper.getReadableDatabase();
        String sql = "select * from "+Constants.TABLE_NAME;
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                int age = cursor.getInt(cursor.getColumnIndex("age"));
                int salary = cursor.getInt(cursor.getColumnIndex("salary"));
                users.add(new User(id,name,age,salary));
            }
        }
        cursor.close();
        db.close();
        return users;
    }
}
