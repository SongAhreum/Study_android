package com.example.myapplication;

import static com.example.myapplication.RemoteService.BASE_URL;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.JsonArray;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListActivity extends AppCompatActivity {
    Retrofit retrofit=new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    RemoteService service=retrofit.create(RemoteService.class);
    List<UserVO> array=new ArrayList<>();
    UserAdapter adapter=new UserAdapter();
    RecyclerView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        list=findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setLayoutManager(new LinearLayoutManager(this));

        getSupportActionBar().setTitle("사용자목록");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Call<List<UserVO>> call=service.list(1, 10, "");
        call.enqueue(new Callback<List<UserVO>>() {
            @Override
            public void onResponse(Call<List<UserVO>> call, Response<List<UserVO>> response) {
                array=response.body();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<UserVO>> call, Throwable t) {

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

    class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>{
        @NonNull
        @Override
        public UserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view=getLayoutInflater().inflate(R.layout.item_user, parent, false);
            return new ViewHolder(view);
        }
        @Override
        public void onBindViewHolder(@NonNull UserAdapter.ViewHolder holder, int position) {
            UserVO vo=(UserVO)array.get(position);
            holder.uid.setText(vo.getUname() + "(" + vo.getUid() + ")");
            String address1 = vo.getAddress1() == null ? "" : vo.getAddress1();
            String address2 = vo.getAddress2() == null ? "" : vo.getAddress2();
            holder.address.setText(address1 + " " + address2);
            String phone = vo.getPhone() == null ? "" : vo.getPhone();
            holder.phone.setText(vo.getPhone());
            holder.item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ListActivity.this,ReadActivity.class);
                    intent.putExtra("uid",vo.getUid());
                    startActivity(intent);
                }
            });
        }
        @Override
        public int getItemCount() {
            return array.size();
        }
        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView uid, address, phone;
            CardView item;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                uid=itemView.findViewById(R.id.uid);
                address=itemView.findViewById(R.id.address);
                phone=itemView.findViewById(R.id.phone);
                item = itemView.findViewById(R.id.item);
            }
        }
    }
}