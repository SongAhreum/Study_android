package com.example.day08;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class ReadActivity extends AppCompatActivity {

    FirebaseFirestore db;
    TextView title, price, email, date;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);

        getSupportActionBar().setTitle("상품정보");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        title = findViewById(R.id.title);
        price = findViewById(R.id.price);
        email = findViewById(R.id.email);
        date = findViewById(R.id.date);
        image = findViewById(R.id.image);

        db = FirebaseFirestore.getInstance();

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        db.collection("shop").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot doc) {
                title.setText(doc.getData().get("title").toString());
                String strPrice = doc.getData().get("price").toString();
                DecimalFormat df = new DecimalFormat("#,###원");
                price.setText(df.format(Integer.parseInt(strPrice)));
                email.setText(doc.getData().get("email").toString());
                date.setText(doc.getData().get("date").toString());
                String strImage = doc.getData().get("image").toString();
                Picasso.with(ReadActivity.this).load(strImage).into(image);
            }
        });

        findViewById(R.id.btnChat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReadActivity.this, ChatActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("title", title.getText());
                startActivity(intent);
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
}//Activity