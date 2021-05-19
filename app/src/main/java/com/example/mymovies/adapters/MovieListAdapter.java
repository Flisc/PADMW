package com.example.mymovies.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mymovies.MovieDetailsActivity;
import com.example.mymovies.R;
import com.example.mymovies.data.Movie;
import com.example.mymovies.network.internetConnectionStatus;

import java.util.ArrayList;
import java.util.List;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MyViewHolder> {
    Context context;
    private List<Movie> movieList = new ArrayList<>();

    public MovieListAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.movie_item_list, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.title.setText(movieList.get(position).getTitle());
        holder.year.setText(movieList.get(position).getYear());
        Glide.with(context)
                .load(movieList.get(position).getPoster())
                .into(holder.img);
    }

    @Override
    public int getItemCount() {
        if (movieList != null)
            return movieList.size();
        return 0;
    }

    public void setMovieList(List<Movie> movies) {
        this.movieList = movies;
        notifyDataSetChanged();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView year;
        ImageView img;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_movie);
            year = itemView.findViewById(R.id.year_movie);
            img = itemView.findViewById(R.id.movie_avatar);
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

