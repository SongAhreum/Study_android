package com.example.day04;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class AddressHelper extends SQLiteOpenHelper {
    public AddressHelper(@Nullable Context context) {
        super(context, "address.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table address(_id integer primary key autoincrement,name text,phone text,juso text, photo text)");
        db.execSQL("insert into address values(null,'홍길동', '010-1234-9678', '인천 서구', '')");
        db.execSQL("insert into address values(null,'성춘향', '010-1235-8678', '인천 부평구', '')");
        db.execSQL("insert into address values(null,'이순신', '010-1236-7678', '서울 강남구', '')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
