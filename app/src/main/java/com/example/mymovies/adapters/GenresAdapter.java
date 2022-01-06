package com.example.mymovies.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymovies.R;

import java.util.Arrays;
import java.util.List;

public class GenresAdapter  extends RecyclerView.Adapter<GenresAdapter.GenreViewHolder> {
     private List<String> genres;
     public GenresAdapter(String genres){
         this.genres = Arrays.asList(genres.split(","));
     }
    @NonNull
    @Override
    public GenreViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.genre, viewGroup, false);
        return new GenresAdapter.GenreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GenreViewHolder genreViewHolder, int i) {
        genreViewHolder.genre_name.setText(genres.get(i));
    }
    @Override
    public int getItemCount() {
        if(genres==null) return 0;
        else return genres.size();
    }
    class GenreViewHolder extends RecyclerView.ViewHolder {
        TextView genre_name;
        public GenreViewHolder(@NonNull View itemView) {
            super(itemView);
            genre_name = itemView.findViewById(R.id.genre_name);
        }
    }


}
