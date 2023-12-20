package com.example.day08;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.*;

public class ChatActivity extends AppCompatActivity {
    FirebaseUser user;
    FirebaseDatabase db;
    List<ChatVO> array=new ArrayList<>();
    ChatAdapter adapter=new ChatAdapter();
    EditText contents;
    RecyclerView list;

    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        user = FirebaseAuth.getInstance().getCurrentUser();
        db=FirebaseDatabase.getInstance();

        Intent intent=getIntent();
        String title=intent.getStringExtra("title");
        id=intent.getStringExtra("id");

        getSupportActionBar().setTitle("채팅 | " + title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        contents = findViewById(R.id.contents);
        findViewById(R.id.btnSend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strContents = contents.getText().toString();
                if(strContents.equals("")) {
                    Toast.makeText(ChatActivity.this, "내용을 입력하세요!", Toast.LENGTH_SHORT).show();
                }else{
                    ChatVO vo=new ChatVO();
                    vo.setId(id);
                    vo.setEmail(user.getEmail());
                    vo.setContents(contents.getText().toString());
                    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    vo.setDate(sdf.format(new Date()));

                    DatabaseReference ref=db.getReference("shop_chat/" + id).push();
                    vo.setKey(ref.getKey());
                    ref.setValue(vo);
                    contents.setText("");
                }
            }
        });

        getList(db, id);
        list=findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setLayoutManager(new LinearLayoutManager(this));
    }//onCreate

    public void getList(FirebaseDatabase db, String id){
        DatabaseReference ref=db.getReference("shop_chat/" + id);
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                ChatVO vo=(ChatVO)snapshot.getValue(ChatVO.class);
                array.add(vo);
                adapter.notifyDataSetChanged();
                list.scrollToPosition(array.size()-1);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                ChatVO vo=(ChatVO)snapshot.getValue(ChatVO.class);
                for(ChatVO chat:array){
                    if(chat.getKey().equals(vo.getKey())){
                        array.remove(chat);
                        break;
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
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

    class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder>{
        @NonNull
        @Override
        public ChatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.item_chat, parent, false);
            return new ViewHolder(view);
        }
        @Override
        public void onBindViewHolder(@NonNull ChatAdapter.ViewHolder holder, int position) {
            ChatVO vo=array.get(position);
            holder.contents.setText(vo.getContents());
            holder.email.setText(vo.getEmail());
            holder.date.setText(vo.getDate());

            LinearLayout.LayoutParams cparms=(LinearLayout.LayoutParams)holder.contents.getLayoutParams();
            LinearLayout.LayoutParams dparms=(LinearLayout.LayoutParams)holder.date.getLayoutParams();
            if(vo.getEmail().equals(user.getEmail())){
                cparms.gravity = Gravity.RIGHT;
                dparms.gravity = Gravity.RIGHT;
                holder.email.setVisibility(View.GONE);


            }else{
                cparms.gravity = Gravity.LEFT;
                dparms.gravity = Gravity.LEFT;
                holder.email.setVisibility(View.VISIBLE);
            }
            //내용을 길게 눌렀을때 - 삭제
            holder.contents.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if(vo.getEmail().equals(user.getEmail())){
                        AlertDialog.Builder box = new AlertDialog.Builder(ChatActivity.this);
                        box.setTitle("메뉴를 선택하세요.");
                        box.setItems(new String[]{"삭제", "취소"}, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(which == 0) {
                                    DatabaseReference ref = db.getReference("shop_chat/" + id);
                                    ref.child(vo.getKey()).removeValue();
                                }
                            }
                        });
                        box.show();
                    }
                    return false;
                }
            });
        }

        @Override
        public int getItemCount() {
            return array.size();
        }
        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView contents, email, date;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                contents=itemView.findViewById(R.id.contents);
                email=itemView.findViewById(R.id.email);
                date=itemView.findViewById(R.id.date);
            }
        }
    }
}//activity