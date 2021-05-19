package com.example.mymovies.roomEntities;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.mymovies.data.FavMovie;

import java.util.List;

public class favMovieViewModel  extends AndroidViewModel {

    private MovieRepository repository;
    private LiveData<List<FavMovie>>  favMoviesList;

    public favMovieViewModel(@NonNull Application application) {
        super(application);
        repository = new MovieRepository(application);
        favMoviesList = repository.getFavMovies();
    }

    public LiveData<List<FavMovie>> getFavMoviesList() {
        return favMoviesList;
    }
}
