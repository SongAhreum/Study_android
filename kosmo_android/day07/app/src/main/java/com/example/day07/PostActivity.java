package com.example.day07;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.ArrayList;
import java.util.List;

public class PostActivity extends AppCompatActivity {
    FirebaseUser user;
    FirebaseFirestore db;
    List<PostVO> array=new ArrayList<>();
    PostAdapter adapter=new PostAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        user= FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();

        getSupportActionBar().setTitle("게시글 :" + user.getEmail());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getList();
        RecyclerView list=findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setLayoutManager(new LinearLayoutManager(this));

        findViewById(R.id.btnWrite).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PostActivity.this, WriteActivity.class);
                startActivity(intent);
            }
        });
    }

    //데이터 읽어오기
    public void getList() {
        db.collection("post")
                .orderBy("date", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        array.clear();
                        for(QueryDocumentSnapshot doc:task.getResult()){
                            PostVO vo=new PostVO();
                            vo.setId(doc.getId());
                            vo.setTitle(doc.getData().get("title").toString());
                            vo.setContents(doc.getData().get("contents").toString());
                            vo.setDate(doc.getData().get("date").toString());
                            vo.setEmail(doc.getData().get("email").toString());
                            array.add(vo);
                        }
                        //System.out.println("데이터갯수:" + array.size());
                        adapter.notifyDataSetChanged();
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

    @Override
    protected void onRestart() {
        super.onRestart();
        getList();
    }

    //Post 어댑터정의
    class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolde> {
        @NonNull
        @Override
        public ViewHolde onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view=getLayoutInflater().inflate(R.layout.item_post, parent, false);
            return new ViewHolde(view);
        }
        @Override
        public void onBindViewHolder(@NonNull ViewHolde holder, int position) {
            PostVO vo=array.get(position);
            holder.title.setText(vo.getTitle());
            holder.email.setText(vo.getEmail());
            holder.date.setText(vo.getDate());
            holder.item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(PostActivity.this, ReadActivity.class);
                    intent.putExtra("id", vo.getId());
                    startActivity(intent);
                }
            });
        }
        @Override
        public int getItemCount() {
            return array.size();
        }
        public class ViewHolde extends RecyclerView.ViewHolder {
            TextView title, email, date;
            RelativeLayout item;
            public ViewHolde(@NonNull View itemView) {
                super(itemView);
                title=itemView.findViewById(R.id.title);
                email=itemView.findViewById(R.id.email);
                date=itemView.findViewById(R.id.date);
                item=itemView.findViewById(R.id.item);
            }
        }
    }

}//Activity










