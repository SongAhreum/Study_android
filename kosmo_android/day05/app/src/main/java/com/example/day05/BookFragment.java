package com.example.day05;

import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.*;

public class BookFragment extends Fragment {
    List<HashMap<String, Object>> array = new ArrayList<>();
    String query = "안드로이드";
    int page =1;
    boolean is_end = false;

    BookAdapter adapter = new BookAdapter();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blog, container, false);
        new BookThread().execute();

        ListView list = view.findViewById(R.id.list);
        list.setAdapter(adapter);

        EditText editQuery = view.findViewById(R.id.query);
        view.findViewById(R.id.search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                query = editQuery.getText().toString();
                page=1;
                array = new ArrayList<>();
                new BookThread().execute();
            }
        });
        view.findViewById(R.id.more).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(is_end){
                    Toast.makeText(getActivity(),"마지막페이지입니다",Toast.LENGTH_SHORT).show();
                } else {
                    page += 1;
                    new BookThread().execute();
                }
            }
        });
        return view;
    }
    //BOOK 스레드
    class BookThread extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... strings) {
            String url ="https://dapi.kakao.com/v3/search/book?target=title&query="+query+"&page="+page;
            String result =  KakaoAPI.connect(url);
            System.out.println("도서갯수:"+array.size());
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            System.out.println("도서결과:"+s);
            bookParser(s);
            adapter.notifyDataSetChanged();

        }
    }
    public void bookParser(String result){
        try {
            JSONObject meta = new JSONObject(result).getJSONObject("meta");
            is_end = meta.getBoolean("is_end");
            JSONArray jArray = new JSONObject(result).getJSONArray("documents");
            for(int i=0;i<jArray.length();i++){
                JSONObject obj = jArray.getJSONObject(i);
                HashMap<String,Object> map = new HashMap<>();
                map.put("title",obj.getString("title"));
                map.put("authors",obj.getString("authors"));
                map.put("price",obj.getString("price"));
                map.put("image",obj.getString("thumbnail"));
                map.put("contents",obj.getString("contents"));
                map.put("isbn",obj.getString("isbn"));
                map.put("publisher",obj.getString("publisher"));
                array.add(map);
            }
        }catch (Exception e){

        }
    }
    //book adapter생성
    class BookAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return array.size();
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
        public View getView(int position, View view, ViewGroup parent) {
            view = getLayoutInflater().inflate(R.layout.item_book,parent,false);
            HashMap<String,Object> map = array.get(position);
            ImageView image = view.findViewById(R.id.image);
            String strImage = map.get("image").toString();
            if(strImage.equals("")){
                image.setImageResource(R.drawable.baseline_collections_bookmark_24);
            } else {
                Picasso.with(getActivity())
                        .load(strImage)
                        .into(image);
            }

            TextView title = view.findViewById(R.id.title);
            String strTitle = map.get("title").toString();
            title.setText(strTitle);

            TextView price = view.findViewById(R.id.price);
            String strPrice = map.get("price").toString();
            int intPrice = Integer.parseInt(strPrice);
            DecimalFormat df = new DecimalFormat("#,###원");
            price.setText(df.format(intPrice));

            TextView authors = view.findViewById(R.id.authors);
            String strAuthors = map.get("authors").toString();
            authors.setText(strAuthors);
            String strContents = map.get("contents").toString();
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    View layout = getLayoutInflater().inflate(R.layout.item_contents,parent,false);
                    ImageView image = layout.findViewById(R.id.image);
                    if(strImage.equals("")){
                        image.setImageResource(R.drawable.baseline_collections_bookmark_24);
                    } else {
                        Picasso.with(getActivity())
                                .load(strImage)
                                .into(image);
                    }
                    TextView contents = layout.findViewById(R.id.contents);
                    contents.setText(strContents);

                    new AlertDialog.Builder(getActivity())
                            .setTitle(strTitle)
                            .setView(layout)
                            .setPositiveButton("확인",null)
                            .show();
                }
            });
            return view ;
        }
    }
}