package com.b1.sagar.newsapitest.Interface;

import com.b1.sagar.newsapitest.Model.News;

import retrofit2.Call;
import retrofit2.http.GET;


public interface NewsService {
    @GET ("v2/top-headlines?country=us&category=business&apiKey=dd8e22b7f9ed442fa64f8411b29a1ec0")
    Call<News> getNews();
}
