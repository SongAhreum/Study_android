package com.example.day01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class StartActivity5 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start5);
    }
    public void onClick(View view){
        Intent intent = null;
        if(view.getId()==R.id.btn1){
            intent = new Intent(this,MainActivity.class);
        } else if (view.getId()==R.id.btn2) {
            intent = new Intent(this,MainActivity2.class);
        } else if (view.getId()==R.id.btn3) {
            intent = new Intent(this,MainActivity3.class);
        } else if (view.getId()==R.id.btn4) {
            intent = new Intent(this,MainActivity4.class);
        }
        startActivity(intent);
    }
}