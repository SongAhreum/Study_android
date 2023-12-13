package com.example.day03;

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
        db.execSQL("create table address(_id integer primary key autoincrement,name text,phone text,juso text,photo text)");
        db.execSQL("insert into address values(null,'홍길동','010-0000-0000','인천서구','')");
        db.execSQL("insert into address values(null,'강감찬','010-1111-0000','울산 남구 무거동','')");
        db.execSQL("insert into address values(null,'이순신','010-0000-2222','긴해 인제로','')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
