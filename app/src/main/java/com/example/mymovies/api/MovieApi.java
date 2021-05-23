package com.example.mymovies.api;
import com.example.mymovies.data.JsonSearch;
import com.example.mymovies.data.MovieDetailed;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieApi {

    @GET("?apikey=b650d11e&s=\"the\"&y=2021&type=movie&page=3")
    Call<JsonSearch> getUpcomingMovies();

    @GET("?apikey=b650d11e&s=\"the\"&y=2019&type=movie&page=2")
    Call<JsonSearch> getPopularMovies();

    @GET("?apikey=b650d11e")
    Call<MovieDetailed> getMovie(@Query("i") String id);

    @GET("?apikey=b650d11e")
    Call<JsonSearch> searchMovie(@Query("s") String s);
    @GET("?apikey=b650d11e")
    Call<JsonSearch> searchMoviePage(@Query("s") String s,@Query("page") int page);
}
