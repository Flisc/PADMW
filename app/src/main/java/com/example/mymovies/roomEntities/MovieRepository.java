package com.example.mymovies.roomEntities;
import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import com.example.mymovies.R;
import com.example.mymovies.api.API;
import com.example.mymovies.data.FavMovie;
import com.example.mymovies.data.JsonSearch;
import com.example.mymovies.data.Movie;
import com.example.mymovies.network.internetConnectionStatus;

import androidx.lifecycle.LiveData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MovieRepository {
    MovieDataBase movieDataBase;
    private MovieDao movieDao;
    private LiveData<List<Movie>> allMovies;
    private LiveData<List<Movie>>  popularMovies;
    private LiveData<List<FavMovie>> favMovies;

    public MovieRepository(Application application) {
        movieDataBase = MovieDataBase.getDatabase(application);
        movieDao = movieDataBase.movieDao();
        if(checkInternet(application)) {
            ApiRepository();
        }
        else{
            prePopulateDB(application);
        }
        allMovies = movieDao.getAllMovies();
        popularMovies = movieDao.getPopularMovies();
        favMovies = movieDao.getAllFavMovies();
    }
 private  boolean checkInternet(Context context){

        ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
        if (netInfo != null)
            return true;
        else
            return false;

    }

    public LiveData<List<Movie>> getAllMovies() {
        return allMovies;
    }

    public LiveData<List<Movie>> getPopularMovies() {
        return popularMovies;
    }

    public LiveData<List<FavMovie>> getFavMovies() { return favMovies; }

    public void insert(Movie movie) {
        new insertMovieAsyncTask(movieDao).execute(movie);
    }

    public void deleteFav(String id)  {new deleteFavMovieAsyncTask().execute(id);}


public  void ApiRepository(){
        API api = new API();
        Call<JsonSearch> call = api.getMovieInterface().getUpcomingMovies();
        call.enqueue(new Callback<JsonSearch>() {
            @Override
            public void onResponse(Call<JsonSearch> call, Response<JsonSearch> response) {
                  if(response.code() != 200){
                      Log.d("Error_response", response.message());
                      return;
                  }
                  JsonSearch jsonSearch  = response.body();
                  List<Movie> listAux = Arrays.asList(jsonSearch.getMoviesList());
                  for(int i=0;i< listAux.size();i++) // new insertMovieAsyncTask(movieDao).execute(listAux.get(i));
                      insert(listAux.get(i));


            }
            @Override
            public void onFailure(Call<JsonSearch> call, Throwable t) {  }
        });
        Call<JsonSearch> call2 = api.getMovieInterface().getPopularMovies();
        call2.enqueue(new Callback<JsonSearch>() {
            @Override
            public void onResponse(Call<JsonSearch> call, Response<JsonSearch> response) {
                if(response.code() != 200){
                    Log.d("Error_response", response.message());
                    return;
                }
                try {
                    JsonSearch jsonSearch = response.body();
                    List<Movie> listAux = Arrays.asList(jsonSearch.getMoviesList());
                    for(int i=0;i< listAux.size();i++)
                        insert(listAux.get(i));

                }catch (Exception e){
                    throw e;
                }

            }

            @Override
            public void onFailure(Call<JsonSearch> call, Throwable t) {
                Log.d("Error_response", t.getMessage());
            }
        });
}


    public void prePopulateDB(Context context){

       // MovieDao dao = getDatabase(context).movieDao();
        JSONArray movies = dataFromJSON(context);
        try{
            for(int i=0;i<movies.length();i++){
                JSONObject movie = movies.getJSONObject(i);
               insert(new Movie(
                        movie.getString("Title"),
                        movie.getString("Year"),
                        movie.getString("imdbID"),
                        movie.getString("Type"),
                        movie.getString("Poster")
                ));
            }
        }catch (JSONException e){

        }
    }
    private static JSONArray dataFromJSON(Context context){
        StringBuilder builder = new StringBuilder();
        InputStream inputStream =  context.getResources().openRawResource(R.raw.movies);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        try{
            while((line = reader.readLine()) != null){
                builder.append(line);
            }
            JSONObject json = new JSONObject((builder.toString()));
            return json.getJSONArray("Search");
        }catch (IOException | JSONException exception){
            exception.printStackTrace();
        }
        return null;
    }


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
    private class deleteFavMovieAsyncTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
           movieDao.deleteFavMvoie(strings[0]);
           return null;
        }
    }


}
