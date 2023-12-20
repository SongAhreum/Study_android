package com.example.day08;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ShopActivity extends AppCompatActivity {
    FirebaseUser user;
    FirebaseFirestore db;
    List<ShopVO> array = new ArrayList<>();
    ShopAdapter adapter = new ShopAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        user = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();


        getSupportActionBar().setTitle("상품목록 | "+user.getEmail());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        findViewById(R.id.btnInsert).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShopActivity.this,InsertActivity.class);
                startActivity(intent);
            }
        });
        getList();
        RecyclerView list = findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getList();
    }

    public void getList(){
        db.collection("shop")
                .orderBy("date", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            array.clear(); //!!!!! 누적방지
                            for(QueryDocumentSnapshot doc : task.getResult()){
                                ShopVO vo = new ShopVO();
                                vo.setId(doc.getId());
                                vo.setTitle(doc.getData().get("title").toString());
                                vo.setPrice(Integer.parseInt( doc.getData().get("price").toString()));
                                vo.setDate(doc.getData().get("date").toString());
                                vo.setEmail(doc.getData().get("email").toString());
                                vo.setImage(doc.getData().get("image").toString());

                                array.add(vo);
                            }
                            //System.out.println("data size!!!! array.size() ->"+array.size());
                            adapter.notifyDataSetChanged();
                        }else{
                            System.out.println("......... db reading실패 .........");
                        }
                    }
                });
    }
    class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ViewHolder>{

        @NonNull
        @Override
        public ShopAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.item_shop,parent,false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ShopAdapter.ViewHolder holder, int position) {
            ShopVO vo = array.get(position);
            holder.title.setText(vo.getTitle());
            holder.email.setText(vo.getEmail());
            DecimalFormat df = new DecimalFormat("#,###원");
            holder.price.setText(df.format(vo.getPrice()) );
            holder.date.setText(vo.getDate());
            Picasso.with(ShopActivity.this).load(vo.getImage()).into(holder.image);
            holder.item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ShopActivity.this,ReadActivity.class);
                    intent.putExtra("id", vo.getId());
                    startActivityForResult(intent, 0);
                }
            });
        }

        @Override
        public int getItemCount() {
            return array.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView title,price,date,email;
            ImageView image;
            RelativeLayout item;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                title = itemView.findViewById(R.id.title);
                price = itemView.findViewById(R.id.price);
                date = itemView.findViewById(R.id.date);
                email = itemView.findViewById(R.id.email);
                image = itemView.findViewById(R.id.image);
                item = itemView.findViewById(R.id.item);
            }
        }
    }
}