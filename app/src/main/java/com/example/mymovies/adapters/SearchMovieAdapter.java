package com.example.mymovies.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mymovies.activities.MovieDetailsActivity;
import com.example.mymovies.R;
import com.example.mymovies.data.Movie;
import com.example.mymovies.network.internetConnectionStatus;

import java.util.List;

public class SearchMovieAdapter extends RecyclerView.Adapter<SearchMovieAdapter.FavMoviesHolder> {
 private Context context;
 private List<Movie> movieList;

    public SearchMovieAdapter(Context context, List<Movie> movieList) {
        this.context = context;
        this.movieList = movieList;
    }

    @NonNull
    @Override
    public SearchMovieAdapter.FavMoviesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_movie_item,parent,false);
        return new FavMoviesHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchMovieAdapter.FavMoviesHolder holder, int position) {

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.placeholder);
        requestOptions.error(R.drawable.placeholder);
        Glide.with(context)
                .setDefaultRequestOptions(requestOptions)
                .load(movieList.get(position).getPoster())
                .into(holder.img);
    }

    @Override
    public int getItemCount() {
        if (movieList != null)
            return movieList.size();
        return 0;
    }
    public class FavMoviesHolder extends RecyclerView.ViewHolder{
        ImageView img;

        public FavMoviesHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.search_movie_poster);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(internetConnectionStatus.checkInternet(context)){
                        Toast.makeText(context, "You're online ", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(v.getContext(), MovieDetailsActivity.class);
                        intent.putExtra("imdbID", movieList.get(getAbsoluteAdapterPosition()).getImdbId());
                        v.getContext().startActivity(intent);
                    }else {
                        Toast.makeText(context, "You're offline :( ", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }
}
