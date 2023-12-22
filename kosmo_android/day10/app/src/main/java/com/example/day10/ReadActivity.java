package com.example.day10;


import static com.example.day10.RemoteService.BASE_URL;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Multipart;

public class ReadActivity extends AppCompatActivity {
    EditText title, price;
    ImageView image;
    ShopVO vo = new ShopVO();
    String strFile = "";
    int pid = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);

        title = findViewById(R.id.title);
        price = findViewById(R.id.price);
        image = findViewById(R.id.image);

        Intent intent = getIntent();
        pid = intent.getIntExtra("pid", 0);

        getSupportActionBar().setTitle("정보수정 | " + pid);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RemoteService service = ShopAPI.call();
        Call<ShopVO> call = service.read(pid);
        call.enqueue(new Callback<ShopVO>() {
            @Override
            public void onResponse(Call<ShopVO> call, Response<ShopVO> response) {
                vo = (ShopVO)response.body();
                System.out.println("결과 : " + vo.toString());
                title.setText(vo.getTitle());
                price.setText(String.valueOf(vo.getLprice()));
                if(vo.getImage().equals("")) {
                    image.setImageResource(R.drawable.baseline_disabled_by_default_24);
                }else {
                    String url = BASE_URL + "/display?file=" + vo.getImage();
                    Picasso.with(ReadActivity.this).load(url).into(image);
                }
            }

            @Override
            public void onFailure(Call<ShopVO> call, Throwable t) {

            }
        });

        findViewById(R.id.btnInfo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder box = new AlertDialog.Builder(ReadActivity.this);
                box.setTitle("질의");
                box.setMessage("상품정보를 수정하시겠습니까?");
                box.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        vo.setTitle(title.getText().toString());
                        vo.setLprice(Integer.parseInt(price.getText().toString()));
                        updateInfo(vo);
                        finish();
                    }
                });
                box.setNegativeButton("아니오", null);
                box.show();
            }
        });

        findViewById(R.id.image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 0);
            }
        });

        findViewById(R.id.btnImage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder box = new AlertDialog.Builder(ReadActivity.this);
                if(strFile.equals("")){
                    box.setTitle("알림");
                    box.setMessage("수정할 이미지를 선택하세요");
                    box.setPositiveButton("확인", null);
                    box.show();
                }else {
                    box.setTitle("질의");
                    box.setMessage("상품 이미지를 수정하시겠습니까?");
                    box.setPositiveButton("예", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            RequestBody reqid = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(pid));
                            File file = new File(strFile);
                            RequestBody reqFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                            MultipartBody.Part partFile = MultipartBody.Part.createFormData(
                                    "file", file.getName(), reqFile);
                            UploadImage(reqid, partFile);
                            finish();
                        }
                    });
                    box.setNegativeButton("아니오", null);
                    box.show();
                }
            }
        });
    } //onCreate

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    //정보 수정 메소드
    public void updateInfo(ShopVO vo) {
        RemoteService service = ShopAPI.call();
        Call<Void> call = service.update(vo);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(ReadActivity.this, "수정이 완료되었습니다.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(ReadActivity.this, "수정이 실패했습니다..", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @SuppressLint("Range")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0) {
            String[] projection = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(data.getData(), projection, null, null, null);
            cursor.moveToFirst();
            strFile = cursor.getString(cursor.getColumnIndex(projection[0]));
            image.setImageBitmap(BitmapFactory.decodeFile(strFile));
            cursor.close();
        }
    }

    public void UploadImage(RequestBody id, MultipartBody.Part file) {
        RemoteService service = ShopAPI.call();
        Call<Void> call = service.upload(id, file);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(ReadActivity.this, "수정 완료", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(ReadActivity.this, "수정 실패", Toast.LENGTH_SHORT).show();
            }
        });
    }
} //Activity