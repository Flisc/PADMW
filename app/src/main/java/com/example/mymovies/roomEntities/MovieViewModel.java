package com.example.mymovies.roomEntities;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.mymovies.data.FavMovie;
import com.example.mymovies.data.Movie;

import java.util.List;

public class MovieViewModel  extends AndroidViewModel {

    private MovieRepository repository;
    private LiveData<List<Movie>> allMovies;
    private LiveData<List<Movie>> popularMovies;
    private LiveData<List<FavMovie>> favMovies;



    public MovieViewModel(@NonNull Application application) {
        super(application);
        repository = new MovieRepository(application);
        allMovies = repository.getAllMovies();
        popularMovies = repository.getPopularMovies();
        favMovies = repository.getFavMovies();
    }

    public void insert(Movie movie) {
        repository.insert(movie);
    }


    public LiveData<List<Movie>> getAllMovies() {
        return allMovies;
    }
    public LiveData<List<Movie>> getPopularMovies() {
        return popularMovies;
    }
    public LiveData<List<FavMovie>> getFavMovies() {
        return favMovies;
    }
}
