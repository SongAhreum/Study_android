package com.example.day02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class InsertActivity extends AppCompatActivity {

    DBHelper dbHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        getSupportActionBar().setTitle("상품등록");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();

        EditText name, price;
        name = findViewById(R.id.name);
        price = findViewById(R.id.price);

        findViewById(R.id.btnInsert).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strName = name.getText().toString();
                String strPrice = price.getText().toString();
                if(strName == "" || strPrice == "") {
                    new AlertDialog.Builder(InsertActivity.this)
                            .setTitle("알림")
                            .setMessage("상품명과 가격을 입력하세요.")
                            .setPositiveButton("확인", null)
                            .show();
                }else {
                    String sql = "insert into product(name, price) values(";
                    sql += "'" + strName + "',";
                    sql += strPrice + ")";
                    db.execSQL(sql);
                    finish();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}