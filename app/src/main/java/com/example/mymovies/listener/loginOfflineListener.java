package com.example.mymovies.listener;

import android.content.Intent;
import android.view.View;

import com.example.mymovies.activities.MainActivity;

public class loginOfflineListener  implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        v.getContext().startActivity(new Intent(v.getContext(), MainActivity.class));
    }
}
