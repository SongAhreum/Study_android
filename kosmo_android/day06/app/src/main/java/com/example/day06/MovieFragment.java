package com.example.day06;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MovieFragment extends Fragment {
    String query = "배트맨";
    List<HashMap<String,Object>> array = new ArrayList<>();
    MovieAdapter adapter = new MovieAdapter();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop, container, false);
        view.findViewById(R.id.more).setVisibility(View.GONE);
        view.findViewById(R.id.query).setVisibility(View.GONE);
        view.findViewById(R.id.search).setVisibility(View.GONE);
        //Thread 실행
        new MovieThread().execute();

        RecyclerView list=view.findViewById(R.id.list);
        list.setAdapter(adapter);
        StaggeredGridLayoutManager manager=new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL);
        list.setLayoutManager(manager);
        return view;

    }

    //네이버서버 접속 쓰레드
    class MovieThread extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            try{
                Document doc = Jsoup.connect("http://www.cgv.co.kr/movies/?lt=1&ft=0").get();
                Elements es = doc.select(".sect-movie-chart ol");
                for(Element e:es.select("li")) {
                    String rank = e.select(".rank").text();
                    String title = e.select(".title").text();
                    String image = e.select("img").attr("src");
                    String link = "http://www.cgv.co.kr/"+e.select(".link-reservation").attr("href");
                    System.out.println(image);
                    HashMap<String,Object> map = new HashMap<>();
                    map.put("rank",rank);
                    map.put("title",title);
                    map.put("image",image);
                    map.put("link",link);
                    array.add(map);

                }
            } catch (Exception e) {
                System.out.println("스크랩핑오류:" + e.toString());
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            adapter.notifyDataSetChanged();
        }
    }
    class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{

        @NonNull
        @Override
        public MovieAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MovieAdapter.ViewHolder holder, int position) {
            HashMap<String,Object> map = array.get(position);
            holder.title.setText(map.get("title").toString());
            holder.rank.setText(map.get("rank").toString());
            Picasso.with(getActivity()).load(map.get("image").toString()).into(holder.image);
            holder.btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getActivity(), WebActivity.class);
                    intent.putExtra("link", map.get("link").toString());
                    startActivity(intent);
                }
            });

        }

        @Override
        public int getItemCount() {
            return array.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView image;
            TextView title, rank;
            Button btn;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                image = itemView.findViewById(R.id.image);
                title=itemView.findViewById(R.id.title);
                rank = itemView.findViewById(R.id.rank);
                btn = itemView.findViewById(R.id.btn);
            }
        }
    }
}