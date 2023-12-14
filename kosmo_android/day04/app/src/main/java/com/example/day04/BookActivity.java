package com.example.day04;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.AsyncTaskLoader;

import android.app.AlertDialog;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import android.widget.SearchView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class BookActivity extends AppCompatActivity {
    List<HashMap<String, Object>> list = new ArrayList<>();
    BookAdapter adapter;
    String query = "안드로이드";
    int page =1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        getSupportActionBar().setTitle("도서검색");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        new KakaoThread().execute();

        findViewById(R.id.more).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page +=1;
                new KakaoThread().execute();
            }
        });
    }
    //카카오API호출 스래드
    class KakaoThread extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... strings) {
            String url = "https://dapi.kakao.com/v3/search/book?target=title&query="+query+"&page="+page;
            String result = KakaoAPI.connect(url);
            System.out.println(result);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {

            super.onPostExecute(s);
            bookParser(s);
            System.out.println(list.size());
            adapter = new BookAdapter();
            ListView listView = findViewById(R.id.list);
            listView.setAdapter(adapter);
        }
        //결과 파싱
        public void bookParser(String result){
            try{
                JSONArray array = new JSONObject(result).getJSONArray("documents");
                for(int i=0;i<array.length();i++){
                    JSONObject obj = array.getJSONObject(i);
                    HashMap<String,Object> map = new HashMap<>();
                    map.put("title",obj.getString("title"));
                    map.put("image",obj.getString("thumbnail"));
                    map.put("price",obj.getInt("price"));
                    map.put("contents",obj.getString("contents"));
                    map.put("publisher",obj.getString("publisher"));
                    map.put("authors",obj.getString("authors"));
                    list.add(map);
                }
            }catch (Exception e){

            }
        }
    }


        @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    //어댑터생성
    class BookAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.item_book,parent,false);
            HashMap<String,Object> map = list.get(position);

            TextView title = convertView.findViewById(R.id.title);
            title.setText(map.get("title").toString());
            title.setTypeface(null, Typeface.BOLD);

            int intPrice = Integer.parseInt(map.get("price").toString());
            DecimalFormat df = new DecimalFormat("#,###원");
            TextView price = convertView.findViewById(R.id.price);
            price.setText(df.format(intPrice));

            TextView  authors = convertView.findViewById(R.id.authors);
            authors.setText(map.get("authors").toString());

            ImageView image = convertView.findViewById(R.id.image);
            String strImage = map.get("image").toString();
            if(strImage.equals("")){
                image.setImageResource(R.drawable.baseline_book_24);
            } else{
                Picasso.with(BookActivity.this)
                        .load(map.get("image").toString())
                        .into(image );
            }
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    View view = (View)View.inflate(BookActivity.this,R.layout.detail_book,null);

                    TextView title = view.findViewById(R.id.title);
                    title.setText(map.get("title").toString());

                    int intPrice = Integer.parseInt(map.get("price").toString());
                    DecimalFormat df = new DecimalFormat("#,###원");
                    TextView price = view.findViewById(R.id.price);
                    price.setText(df.format(intPrice));

                    TextView authors = view.findViewById(R.id.authors);
                    authors.setText(map.get("authors").toString());

                    TextView contents = view.findViewById(R.id.contents);
                    contents.setText(map.get("contents").toString());

                    ImageView image = view.findViewById(R.id.image);
                    String strImage = map.get("image").toString();
                    if(strImage.equals("")) {
                        image.setImageResource(R.drawable.baseline_book_24);
                    } else {
                        Picasso.with(BookActivity.this)
                                .load(map.get("image").toString())
                                .into(image);
                    }

                    new AlertDialog.Builder(BookActivity.this)
                            .setTitle("도서정보")
                            .setView(view)
                            .setPositiveButton("확인",null)
                            .show();

                }
            });
            return convertView;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.book, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String str) {
                query = str;
                page=1;
                list = new ArrayList<>();
                new KakaoThread().execute();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}