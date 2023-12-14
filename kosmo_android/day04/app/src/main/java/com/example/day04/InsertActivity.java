package com.example.day04;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class InsertActivity extends AppCompatActivity {
    EditText name, phone, juso;
    ImageView photo;
    String strPhoto="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        name=findViewById(R.id.name);
        phone=findViewById(R.id.phone);
        juso=findViewById(R.id.juso);
        photo=findViewById(R.id.photo);

        getSupportActionBar().setTitle("주소등록");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button btnInsert = findViewById(R.id.btnUpdate);
        btnInsert.setText("등록");

        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 0);
            }
        });

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strName=name.getText().toString();
                String strPhone=phone.getText().toString();
                String strJuso=juso.getText().toString();
                if(strName.equals("") || strPhone.equals("") || strJuso.equals("")) {
                    new AlertDialog.Builder(InsertActivity.this)
                            .setTitle("알림")
                            .setMessage("이름, 전화번호, 주소를 입력하세요!")
                            .setPositiveButton("확인", null)
                            .show();
                }else{
                    String sql="insert into address(name,phone,juso,photo)";
                    sql+=" values('" + strName + "',";
                    sql+="'" + strPhone + "',";
                    sql+="'" + strJuso + "',";
                    sql+="'" + strPhoto + "')";
                    AddressHelper helper=new AddressHelper(InsertActivity.this);
                    SQLiteDatabase db=helper.getWritableDatabase();
                    db.execSQL(sql);
                    Toast.makeText(InsertActivity.this,"등록성공!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }

    @SuppressLint("Range")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Cursor cursor=getContentResolver().query(data.getData(), null,null,null,null);
        cursor.moveToFirst();
        strPhoto=cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();
        System.out.println("........................" + strPhoto);
        photo.setImageBitmap(BitmapFactory.decodeFile(strPhoto));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}