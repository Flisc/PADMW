package com.example.mymovies.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.mymovies.R;
import com.example.mymovies.adapters.SearchMovieAdapter;
import com.example.mymovies.api.API;
import com.example.mymovies.data.JsonSearch;
import com.example.mymovies.data.Movie;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {
    SearchMovieAdapter searchMovieAdapter;
    List<Movie> movieList;
    RecyclerView recyclerView;
    TextView title ;
    API api = new API();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        handleIntent(getIntent());
        recyclerView = findViewById(R.id.recyclerSearch);
        title = findViewById(R.id.search_title);

    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }
    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);


            Call<JsonSearch> call  = api.getMovieInterface().searchMovie(query);
            call.enqueue(new Callback<JsonSearch>() {
                @Override
                public void onResponse(Call<JsonSearch> call, Response<JsonSearch> response) {
                    if(response.code()!= 200){
                        Log.d("Error_response", response.message());
                        return;
                    }
                    title.append("'"+query+"'");
                    JsonSearch jsonSearch = response.body();
                    movieList = Arrays.asList(jsonSearch.getMoviesList());
//                    if(jsonSearch.getTotalResults() >= 20){
//                        loadMore(query,2);
//                    }
                    dataToRecycler();
                }

                @Override
                public void onFailure(Call<JsonSearch> call, Throwable t) {

                }
            });

        }
    }
    public void loadMore(String s,int page){
        Call<JsonSearch> call  = api.getMovieInterface().searchMoviePage(s,page);
        call.enqueue(new Callback<JsonSearch>() {
            @Override
            public void onResponse(Call<JsonSearch> call, Response<JsonSearch> response) {
                if(response.code()!= 200){
                    Log.d("Error_response", response.message());
                    return;
                }

                JsonSearch jsonSearch = response.body();
                movieList.addAll(movieList.size(),Arrays.asList(jsonSearch.getMoviesList()));

            }

            @Override
            public void onFailure(Call<JsonSearch> call, Throwable t) {

              }
        });

    }
    public void dataToRecycler(){
        searchMovieAdapter = new SearchMovieAdapter(SearchActivity.this,movieList);
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        recyclerView.setAdapter(searchMovieAdapter);

    }
}