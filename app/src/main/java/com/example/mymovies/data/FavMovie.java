package com.example.mymovies.data;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "fav_movies")
public class FavMovie{

    @PrimaryKey(autoGenerate = true)
    private int id;
 private String title;
 private String year;
 private String poster;
 private String imdbID;

    public FavMovie(String title, String year, String poster,String imdbID) {
        this.title = title;
        this.year = year;
        this.poster = poster;
        this.imdbID = imdbID;
    }

    public String getImdbID() {
        return imdbID;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public int getId() {
        return id;
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
}
