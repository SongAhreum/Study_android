package com.example.myapplication;

import static com.example.myapplication.RemoteService.BASE_URL;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    Retrofit retrofit=new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    RemoteService service = retrofit.create(RemoteService.class);

    EditText uid,upass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        uid = findViewById(R.id.uid);
        upass = findViewById(R.id.upass);

        findViewById(R.id.btnLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserVO vo = new UserVO();
                vo.setUid(uid.getText().toString());
                vo.setUpass(upass.getText().toString());
                Call<Integer> call = service.login(vo);
                call.enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        int result = response.body();
                        String message ="";
                        switch (result){
                            case 0:
                                message="아이디가 존재하지 않습니다";
                                break;
                            case 1:
                                message="로그인성공";
                                break;
                            case 2:
                                message = "비밀번호가 일치하지 않습니다.";
                                break;
                            default:
                                message = "error";
                                break;
                        }
                        AlertDialog.Builder box = new AlertDialog.Builder(MainActivity.this);
                        box.setTitle("알림");
                        box.setMessage(message);
                        box.setPositiveButton("확인",null);
                        box.setNegativeButton("취소",null);
                        box.show();

                        if(result == 1){
                            Intent intent = new Intent(MainActivity.this,ListActivity.class);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {
                        System.out.println(".............."+t.toString());
                    }
                });
            }
        });
    }
}