package com.example.day03;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import de.hdodenhof.circleimageview.CircleImageView;

public class InsertActivity extends AppCompatActivity {

    EditText name, phone, juso;
    AddressHelper helper;
    SQLiteDatabase db;
    CircleImageView photo;
    String strPhoto = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        helper = new AddressHelper(this);
        db = helper.getWritableDatabase();

        getSupportActionBar().setTitle("주소등록");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        juso = findViewById(R.id.juso);
        photo = findViewById(R.id.photo);

        findViewById(R.id.btnInsert).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strName = name.getText().toString();
                String strPhone = phone.getText().toString();
                String strJuso = juso.getText().toString();
                if(strName.equals("") || strPhone.equals("") || strJuso.equals("")) {
                    new AlertDialog.Builder(InsertActivity.this)
                            .setTitle("질의")
                            .setMessage("이름, 전화 ,주소를 입력하세요.")
                            .setPositiveButton("확인", null)
                            .show();
                }else{
                    String sql = "insert into address(name, phone, juso, photo) values(";
                    sql += "'" + strName + "',";
                    sql += "'" + strPhone + "',";
                    sql += "'" + strJuso + "',";
                    sql += "'" + strPhoto + "')";
                    db.execSQL(sql);
                    finish();
                }
            }
        });

        // 앨범으로 이동
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 0);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 0) {
            strPhoto = data.getData().toString();
            System.out.println("..........." + strPhoto);
            photo.setImageURI(Uri.parse(strPhoto));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}