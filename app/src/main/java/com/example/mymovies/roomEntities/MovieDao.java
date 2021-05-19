package com.example.mymovies.roomEntities;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.mymovies.data.FavMovie;
import com.example.mymovies.data.Movie;

import java.util.List;

@Dao
public interface MovieDao {

    @Insert
    void insert(Movie movie);
    @Insert
    void insertFav(FavMovie favMovie);

    @Query("SELECT * FROM movies where YEAR = 2021 ")
    LiveData<List<Movie>> getAllMovies();

    @Query("SELECT * FROM movies WHERE year = 2019")
    LiveData<List<Movie>> getPopularMovies();

    @Query("select * from movies where imdbID = :id")
    Movie getMovieById(String id);

    @Query("SELECT * FROM fav_movies")
    LiveData<List<FavMovie>> getAllFavMovies();

    @Query("DELETE from fav_movies where imdbID = :id")
    void deleteFavMvoie(String id);

    @Query("DELETE FROM fav_movies")
    void deleteFavMovies();
    @Query("SELECT * from fav_movies where imdbID = :id")
    FavMovie getFavMovie(String id);




}
