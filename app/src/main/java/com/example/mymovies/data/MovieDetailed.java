package com.example.mymovies.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.mymovies.data.Movie;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "movieDetailed")
public class MovieDetailed  {
//
    @PrimaryKey(autoGenerate = true)
    private int id;
@SerializedName("Title")
    private String title;
@SerializedName("Year")
    private String year;
@SerializedName("imdbID")
    private String imdbId;
@SerializedName("Type")
    private String type;
@SerializedName("Poster")
    private String poster;

    @SerializedName("Released")
    private String released;
    @SerializedName("Genre")
    private  String genre;
    @SerializedName("Director")
    private String director;
    @SerializedName("Actors")
    private String actors;
    @SerializedName("Plot")
    private String plot ;
    @SerializedName("Language")
    private String language;
    @SerializedName("Runtime")
    private String runtime;
    @SerializedName("imdbRating")
    private float rating;
    @SerializedName("Production")
    private String production;

    public String getProduction() {
        return production;
    }

    public void setProduction(String production) {
        this.production = production;
    }

    public MovieDetailed(String title, String year, String type, String img, String imdID, String released, String genre, String director, String actors, String plot, String language, String runtime, float rating) {

        this.released = released;
        this.genre = genre;
        this.director = director;
        this.actors = actors;
        this.plot = plot;
        this.language = language;
        this.runtime = runtime;
        this.rating = rating;
    }

    public MovieDetailed() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getReleased() {
        return released;
    }

    public void setReleased(String released) {
        this.released = released;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
