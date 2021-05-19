package com.example.mymovies.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class API {
    public static final String base_url = "http://www.omdbapi.com/";
    private static API instance = null;

    private MovieApi movieApi;

    public static API getInstance() {
        if(instance == null) instance = new API();
        return instance;
    }

    public API() {
        buildRetrofit();
    }

    private void buildRetrofit() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.movieApi = retrofit.create(MovieApi.class);
    }

    public MovieApi getMovieInterface() {
        return this.movieApi;
    }
}
