package com.example.mymovies.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.example.mymovies.R;
import com.example.mymovies.adapters.GenresAdapter;
import com.example.mymovies.api.API;
import com.example.mymovies.data.FavMovie;
import com.example.mymovies.data.Movie;
import com.example.mymovies.data.MovieDetailed;
import com.example.mymovies.listener.FavListListener;
import com.example.mymovies.roomEntities.MovieDataBase;
import com.google.android.material.snackbar.Snackbar;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailsActivity extends AppCompatActivity {


    ImageView poster;
    TextView year;
    TextView runtime;
    ToggleButton add_fav;
    TextView title;
    TextView released;
    TextView plot;
    TextView director;
    TextView actors;
    TextView language;
    TextView production;
    RecyclerView recyclerGenres;
    GenresAdapter adapterGenres;
    RatingBar rating;
    MovieDetailed movieDetailed = new MovieDetailed();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);


        poster = findViewById(R.id.poster);
        year = findViewById(R.id.detail_year);
        runtime = findViewById(R.id.detail_runtime);
        title = findViewById(R.id.detail_title);
        released = findViewById(R.id.detail_released);
        plot = findViewById(R.id.detail_plot);
        director = findViewById(R.id.detail_director);
        actors = findViewById(R.id.detail_actors);
        language = findViewById(R.id.detail_language);
        production = findViewById(R.id.detail_production);
        recyclerGenres = findViewById(R.id.recyclerView_genre);
        add_fav = findViewById(R.id.add_to_fav_btn);
        rating = findViewById(R.id.ratingBar1);

        Intent intent = getIntent();
        String id = intent.getStringExtra("imdbID");
        API api = new API();
        Call<MovieDetailed> call = api.getMovieInterface().getMovie(id);

        call.enqueue(new Callback<MovieDetailed>() {
            @Override
            public void onResponse(Call<MovieDetailed> call, Response<MovieDetailed> response) {
                if (response.code() != 200) {
                    Log.d("Error_response", response.message());
                    return;
                }
                movieDetailed = response.body();
                Glide.with(getApplicationContext())
                        .load(movieDetailed.getPoster())
                        .into(poster);
                try {
                    year.setText(movieDetailed.getYear());
                    title.setText(movieDetailed.getTitle());
                    runtime.setText(movieDetailed.getRuntime());
                    released.setText(movieDetailed.getReleased());
                    plot.setText(movieDetailed.getPlot());
                    director.setText(movieDetailed.getDirector());
                    actors.setText(movieDetailed.getActors());
                    language.setText(movieDetailed.getLanguage());
                    production.setText(movieDetailed.getProduction());
                    adapterGenres = new GenresAdapter(movieDetailed.getGenre());
                    recyclerGenres.setAdapter(adapterGenres);
                    rating.setNumStars(10);
                    rating.setRating(movieDetailed.getRating());
                } catch (Exception e) {
                    Log.d("Error_text", e.getMessage());
                    throw e;
                }

            }

            @Override
            public void onFailure(Call<MovieDetailed> call, Throwable t) {

            }
        });
        boolean status = getIntent().getBooleanExtra("alreadySaved", false);
        add_fav.setChecked(status);
        add_fav.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
                MovieDataBase movieDataBase = MovieDataBase.getDatabase(MovieDetailsActivity.this);
                Movie movie = new Movie(movieDetailed.getTitle(), movieDetailed.getYear(), movieDetailed.getImdbId(), movieDetailed.getType(), movieDetailed.getPoster());
                FavMovie favMovie = new FavMovie(movie.getTitle(), movie.getYear(), movie.getPoster(), movie.getImdbId());
                if (isChecked) {
                    movieDataBase.insertFavMovie(favMovie);
                    Snackbar.make(buttonView, "Added to favourite movies", Snackbar.LENGTH_LONG)
                            .setAction("See List", new FavListListener()).show();

                } else {
                    movieDataBase.deleteFavMovie(favMovie.getImdbID());
                    Toast.makeText(MovieDetailsActivity.this, "Removed from fav list", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

}