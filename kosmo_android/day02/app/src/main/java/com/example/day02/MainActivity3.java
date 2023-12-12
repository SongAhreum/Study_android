package com.example.day02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        getSupportActionBar().setTitle("상품정보");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent=getIntent();

        String strName=intent.getStringExtra("name");
        TextView name=findViewById(R.id.name);
        name.setText(strName);

        int intPhoto=intent.getIntExtra("photo", 0);
        ImageView photo=findViewById(R.id.photo);
        photo.setImageResource(intPhoto);

        int intPrice=intent.getIntExtra("price", 0);
        DecimalFormat df=new DecimalFormat("#,###원");
        TextView price=findViewById(R.id.price);
        price.setText(df.format(intPrice));
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}