package com.example.mymovies.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mymovies.R;
import com.example.mymovies.adapters.MovieListAdapter;
import com.example.mymovies.data.Movie;
import com.example.mymovies.network.internetConnectionStatus;
import com.example.mymovies.roomEntities.MovieViewModel;
import com.example.mymovies.user.ProfileActivity;

import java.util.List;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerViewUpcoming;
    RecyclerView recyclerViewPopular;
    MovieViewModel movieViewModel;
    MovieViewModel movieViewModel2;
    Button profile;
    TextView favList;
    SearchView searchView;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu_item, menu);
            searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.search));
        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(new ComponentName(this, SearchActivity.class)));
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        ViewPump.init(ViewPump.builder()
//                .addInterceptor(new CalligraphyInterceptor(
//                        new CalligraphyConfig.Builder()
//                                .setDefaultFontPath("fonts/RobotoMono-Regular.ttf")
//                                .setFontAttrId(R.attr.fontPath)
//                                .build()))
//                .build());

        recyclerViewUpcoming = findViewById(R.id.upcomingMovies);
        recyclerViewPopular = findViewById(R.id.popularMovies);
        profile = findViewById(R.id.profileBtn);
        favList = findViewById(R.id.favList);
        recyclerViewUpcoming.setLayoutManager( new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        recyclerViewPopular.setLayoutManager( new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        MovieListAdapter adapter = new MovieListAdapter(this);
        MovieListAdapter adapter2 = new MovieListAdapter(this);
        recyclerViewUpcoming.setAdapter(adapter);
        recyclerViewPopular.setAdapter(adapter2);
        movieViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(MovieViewModel.class);
        movieViewModel2 = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(MovieViewModel.class);

        movieViewModel.getAllMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                adapter.setMovieList(movies);
            }
        });

        movieViewModel2.getPopularMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                adapter2.setMovieList(movies);
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (internetConnectionStatus.checkInternet(MainActivity.this)){
                    Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                    intent.putExtra("email",getIntent().getStringExtra("email"));
                    intent.putExtra("pass",getIntent().getStringExtra("pass"));
                    startActivity(intent);
                }

                else
                    Toast.makeText(MainActivity.this, "You are not connected !", Toast.LENGTH_SHORT).show();
            }
        });
        favList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FavMoviesActivity.class));
            }
        });

    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

}