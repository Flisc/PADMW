package com.example.mymovies.listener;

import android.content.Intent;
import android.view.View;

import com.example.mymovies.FavMoviesActivity;

public class FavListListener implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        v.getContext().startActivity(new Intent(v.getContext(), FavMoviesActivity.class));
    }
}
