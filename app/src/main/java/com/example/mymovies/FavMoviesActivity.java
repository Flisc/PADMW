package com.example.mymovies;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.example.mymovies.adapters.FavMoviesAdapter;
import com.example.mymovies.data.FavMovie;
import com.example.mymovies.data.Movie;
import com.example.mymovies.roomEntities.MovieDataBase;
import com.example.mymovies.roomEntities.MovieViewModel;
import com.example.mymovies.roomEntities.favMovieViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class FavMoviesActivity extends AppCompatActivity {

    RecyclerView recyclerViewFav;
    FavMoviesAdapter favMoviesAdapter;
    FloatingActionButton delete_fav_movies;
    MovieViewModel movieViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_movies);

        recyclerViewFav = findViewById(R.id.recyclerViewFav);
        delete_fav_movies = findViewById(R.id.fab1);
        recyclerViewFav.setLayoutManager(new LinearLayoutManager(this));
        favMoviesAdapter = new FavMoviesAdapter(this);
        recyclerViewFav.setAdapter(favMoviesAdapter);
        movieViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(MovieViewModel.class);
        movieViewModel.getFavMovies().observe(this, new Observer<List<FavMovie>>() {
            @Override
            public void onChanged(List<FavMovie> favMovies) {
                favMoviesAdapter.setAll_fav_movies(favMovies);
            }
        });
        delete_fav_movies.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(FavMoviesActivity.this)
                        .setTitle("Delete movies from favourite")
                        .setMessage("Are you sure you want to empty the list ?")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                MovieDataBase movieDataBase = MovieDataBase.getDatabase(FavMoviesActivity.this);
                                movieDataBase.deleteAllMovies();
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });

//        FavMoviesDatabase db = FavMoviesDatabase.getDataBase(this);
//         FavMoviesDao dao = db.favMoviesDao();
//         new getAllFavMovies(dao).execute();

    }
//    class getAllFavMovies extends AsyncTask<Void,Void,Void>{
//        private FavMoviesDao dao;
//
//        public getAllFavMovies(FavMoviesDao dao) {
//            this.dao = dao;
//        }
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//            favMoviesAdapter = new FavMoviesAdapter(getApplicationContext(),dao.getAllFavMovies());
//            recyclerViewFav.setAdapter(favMoviesAdapter);
//            return null;
//        }
//        @Override
//        protected void onPostExecute(Void aVoid) {
//
//        }
//    }
}