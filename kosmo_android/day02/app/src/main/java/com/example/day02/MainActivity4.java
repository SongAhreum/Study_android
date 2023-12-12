package com.example.day02;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

public class MainActivity4 extends AppCompatActivity {
    DBHelper helper;
    SQLiteDatabase sql;
    Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        getSupportActionBar().setTitle("SQLite 데이터베이스");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        helper = new DBHelper(this);
        sql = helper.getReadableDatabase();
        cursor = sql.rawQuery("select * from product",null);
    }

}