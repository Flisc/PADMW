package com.example.mymovies.roomEntities;

import android.content.Context;
import android.os.AsyncTask;

import com.example.mymovies.data.FavMovie;
import com.example.mymovies.data.Movie;
import com.example.mymovies.data.MovieDetailed;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;


@Database(entities = {Movie.class, MovieDetailed.class, FavMovie.class}, version = 1)
public abstract class MovieDataBase extends RoomDatabase {

    private static MovieDataBase INSTANCE;
    private static Context contextActivity;

    public abstract MovieDao movieDao();

    public static synchronized MovieDataBase getDatabase(final Context context) {

   contextActivity  = context;
        if (INSTANCE == null) {
            synchronized (MovieDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MovieDataBase.class, "movie_database11")
                            .addCallback(callback)
                            .build();
                }
            }
        }

        return INSTANCE;
    }

    private static RoomDatabase.Callback callback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
         //   new prePopulateDB(INSTANCE).execute();

        }
    };
    public void insertMovie(Movie movie){
        new insertMovieAsyncTask(movieDao()).execute(movie);
    }
    public void insertFavMovie(FavMovie movie){
        new insertFavMovieAsyncTask().execute(movie);
    }
    public void deleteFavMovie(String id) {
        new deleteFavMovieAsyncTask().execute(id);
    }
    public void deleteAllMovies(){new deleteAllMoviesAsync().execute();}
    public AsyncTask<String, Void, FavMovie> getFavMovie(String id){return new getFavMovie().execute(id);}


    private static class insertMovieAsyncTask extends AsyncTask<Movie, Void, Void> {
        private MovieDao movieDao;

        private insertMovieAsyncTask(MovieDao movieDao) {
            this.movieDao = movieDao;
        }

        @Override
        protected Void doInBackground(Movie... movies) {
            movieDao.insert(movies[0]);
            return null;
        }
    }
    private class getFavMovie extends  AsyncTask<String,Void,FavMovie>{

        @Override
        protected FavMovie doInBackground(String... strings) {
            return movieDao().getFavMovie(strings[0]);
        }
    }
    private  class insertFavMovieAsyncTask extends AsyncTask<FavMovie, Void, Void> {

        @Override
        protected Void doInBackground(FavMovie... favMovies) {
           movieDao().insertFav(favMovies[0]);
            return null;
        }
    }
    private class deleteFavMovieAsyncTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            movieDao().deleteFavMvoie(strings[0]);
            return null;
        }
    }
    private class deleteAllMoviesAsync extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
           movieDao().deleteFavMovies();
           return null;
        }
    }




}
