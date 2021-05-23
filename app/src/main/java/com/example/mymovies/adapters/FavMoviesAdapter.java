package com.example.mymovies.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mymovies.activities.MovieDetailsActivity;
import com.example.mymovies.R;
import com.example.mymovies.data.FavMovie;

import java.util.ArrayList;
import java.util.List;

public class FavMoviesAdapter extends RecyclerView.Adapter<FavMoviesAdapter.FavMoviesViewHolder> {
  private Context context;
  private List<FavMovie> all_fav_movies = new ArrayList<>();

  public FavMoviesAdapter(Context context) {
    this.context = context;

  }

  public void setAll_fav_movies(List<FavMovie> all_fav_movies) {
    this.all_fav_movies = all_fav_movies;
    notifyDataSetChanged();
  }

  @NonNull
  @Override
  public FavMoviesAdapter.FavMoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fav_movie_item,parent,false);
    return new FavMoviesViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull FavMoviesAdapter.FavMoviesViewHolder holder, int position) {

      holder.fav_movies_title.setText(all_fav_movies.get(position).getTitle());
      holder.fav_movies_year.setText(all_fav_movies.get(position).getYear());
    RequestOptions requestOptions = new RequestOptions();
    requestOptions.placeholder(R.drawable.placeholder);
    requestOptions.error(R.drawable.placeholder);
    Glide.with(context)
            .setDefaultRequestOptions(requestOptions)
         .load(all_fav_movies.get(position).getPoster())
         .into(holder.poster);
  }

  @Override
  public int getItemCount() {
    if(all_fav_movies == null ) return 0;
    else return all_fav_movies.size();
  }
  class FavMoviesViewHolder extends RecyclerView.ViewHolder{
    ImageView poster;
    TextView fav_movies_title;
    TextView fav_movies_year;


    public FavMoviesViewHolder(@NonNull View itemView) {
      super(itemView);
     poster = itemView.findViewById(R.id.search_movie_poster);
      fav_movies_title = itemView.findViewById(R.id.fav_movie_title);
      fav_movies_year = itemView.findViewById(R.id.fav_movie_year);

      itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          Intent intent = new Intent(v.getContext(), MovieDetailsActivity.class);
          intent.putExtra("imdbID",all_fav_movies.get(getAbsoluteAdapterPosition()).getImdbID());
          intent.putExtra("alreadySaved",true);
          v.getContext().startActivity(intent);
        }
      });
    }

  }
}
