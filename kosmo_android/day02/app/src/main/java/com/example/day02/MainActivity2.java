package com.example.day02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity {
    TextView result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        getSupportActionBar().setTitle("연습2");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        result = findViewById(R.id.result);

        Button btn1=findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(MainActivity2.this)
                        .setTitle("알림")
                        .setMessage("대화상자입니다....")
                        .setPositiveButton("확인", null)
                        .show();
            }
        });
        Button btn2=findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(MainActivity2.this)
                        .setTitle("질의")
                        .setMessage("저장하실래요?")
                        .setPositiveButton("저장", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(MainActivity2.this,"저장완료!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("취소", null)
                        .show();
            }
        });

        Button btn3=findViewById(R.id.btn3);
        btn3.setOnClickListener(new View.OnClickListener() {
            int selectIndex=0;
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(MainActivity2.this)
                        .setTitle("음식을 선택하세요!")
                        .setSingleChoiceItems(R.array.foods, selectIndex, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                selectIndex=which;
                            }
                        })
                        .setPositiveButton("선택", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String[] foods=getResources().getStringArray(R.array.foods);
                                Toast.makeText(MainActivity2.this,"선택음식:" + foods[selectIndex], Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("취소", null)
                        .show();
            }
        });

        Button btn4=findViewById(R.id.btn4);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout layout=(LinearLayout)View.inflate(MainActivity2.this,R.layout.custom, null);
                new AlertDialog.Builder(MainActivity2.this)
                        .setTitle("주문정보를 입력하세요!")
                        .setView(layout)
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                EditText name=layout.findViewById(R.id.name);
                                EditText price=layout.findViewById(R.id.price);
                                CheckBox chk=layout.findViewById(R.id.chk);
                                String strName=name.getText().toString();
                                String strPrice=price.getText().toString();
                                String strChk = chk.isChecked() ? "유" :"무";
                                String str="주문상품:" + strName + "\n";
                                str += "주문가격:" + strPrice + "\n";
                                str += "착불결제:" + strChk + "\n";
                                //Toast.makeText(MainActivity2.this, str, Toast.LENGTH_SHORT).show();
                                result.setText(str);
                            }
                        })
                        .setNegativeButton("취소", null)
                        .show();
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