package com.example.day01;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.MenuItem;
import android.view.View;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    int count =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main); //R : resource => res/layout/activity_main.xml
        setContentView(R.layout.sub02);
        getSupportActionBar().setTitle("연습1");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    public void onClick(View view){
        if(view.getId()==R.id.btn1){
            Toast.makeText(MainActivity.this,"얀녕하세요",Toast.LENGTH_SHORT).show();
        } else if (view.getId()==R.id.btn2) {
            Toast.makeText(MainActivity.this,"얀녕하세요",Toast.LENGTH_SHORT).show();
        } else if (view.getId()==R.id.btn3) {
            count++;
            Toast.makeText(MainActivity.this,"Count:"+count,Toast.LENGTH_LONG).show();
        } else {
           LinearLayout layout = (LinearLayout) View.inflate(MainActivity.this,R.layout.custom,null);
           Toast toast = new Toast(MainActivity.this);
           toast.setView(layout);
           toast.show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}