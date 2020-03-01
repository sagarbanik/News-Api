package com.b1.sagar.newsapitest;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.b1.sagar.newsapitest.Adapter.RecyclerListAdapter;
import com.b1.sagar.newsapitest.Interface.NewsService;
import com.b1.sagar.newsapitest.Model.Article;
import com.b1.sagar.newsapitest.Model.News;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    SwipeRefreshLayout swipeRefreshLayout;
    Retrofit retrofit;
    RecyclerView recyclerview;

    String country = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipeRefreshLayout = findViewById(R.id.swiperRefLayout);
        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setOnRefreshListener(this::onRefresh);


        recyclerview = findViewById(R.id.recyclerview);
        recyclerview.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerview.setLayoutManager(linearLayoutManager);

       country = "us";

        retrofit = new Retrofit.Builder()
                .baseUrl("https://newsapi.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        loadNews(retrofit);

    }

    private void loadNews(Retrofit retrofit) {
        NewsService newsService = retrofit.create(NewsService.class);

        Call<News> newsCall =newsService.getNews();

        newsCall.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                Log.d("Hello", "onResponse: ONTO it");

                swipeRefreshLayout.setRefreshing(false);

                if (response.isSuccessful()){
                    News news = response.body();
                    Log.d("Myyyy", "onResponse: Im in response");
                    Log.d("Myyyy1", "onResponse: "+news.getStatus());

                    List<Article> articleList = news.getArticles();
                    Log.d("Myyyy1", "onResponse: " + articleList.size());

                    RecyclerListAdapter adapter = new RecyclerListAdapter(articleList,MainActivity.this);
                    recyclerview.setAdapter(adapter);
                }

            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                Log.d("Myyyy", "onFailure: "+ t.getMessage());
            }
        });
    }

    @Override
    public void onRefresh() {
        loadNews(retrofit);
    }

}
