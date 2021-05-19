package com.example.mymovies.data;

import com.example.mymovies.data.Movie;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JsonSearch {

    @SerializedName("Search")
    @Expose
    private Movie[] moviesList;
    @SerializedName("totalResults")
    private int totalResults;
    @SerializedName("Response")
    @Expose
    private boolean response;

    public Movie[] getMoviesList() {
        return moviesList;
    }

    public void setMoviesList(Movie[] moviesList) {
        this.moviesList = moviesList;
    }

    public JsonSearch() {
    }
}
