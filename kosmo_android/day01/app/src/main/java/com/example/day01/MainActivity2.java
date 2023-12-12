package com.example.day01;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity {

    int count = 0;
    TextView txtCount,txtFruit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        getSupportActionBar().setTitle("연습2");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        txtCount = findViewById(R.id.count);
        txtCount = findViewById(R.id.fruit);
        findViewById(R.id.btnde).setOnClickListener(onClick);
        findViewById(R.id.btnin).setOnClickListener(onClick);

        findViewById(R.id.btnde).setOnLongClickListener(onLongClick);
        findViewById(R.id.btnin).setOnLongClickListener(onLongClick);

        findViewById(R.id.orange).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtFruit.setText("orange");
            }
        });
        findViewById(R.id.apple).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtFruit.setText("apple");
            }
        });
    }

    View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(view.getId()==R.id.btnin){
                count++;

            } else if (view.getId()==R.id.btnde) {
                count--;
            }
            txtCount.setText(String.valueOf(count));
        }
    };
    View.OnLongClickListener onLongClick = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View view) {
            if(view.getId()==R.id.btnin){
                count = 100;

            } else {
                count = 0;
            }
            txtCount.setText(String.valueOf(count));
            return false;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.jjajjang){
            Toast.makeText(this, "짜자자장", Toast.LENGTH_SHORT).show();
        } else if (item.getItemId() == R.id.jjambbong) {
            Toast.makeText(this, "쨤뵹", Toast.LENGTH_SHORT).show();
        } else if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}