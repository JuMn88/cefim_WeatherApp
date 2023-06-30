package com.julien.cefim_weatherapp;

import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;

import com.julien.cefim_weatherapp.databinding.ActivityFavoriteBinding;

import java.util.Objects;

public class FavoriteActivity extends AppCompatActivity {

    private ActivityFavoriteBinding binding; // Permet de récupérer toute la vue (et donc ses membres) --> évite de créer une variable pour chaque membre

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("lol", "Activité Favoris créée chef, c'est de toute beauté !");

        binding = ActivityFavoriteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true); // Affiche la flèche Retour en haut à gauche de la page

        CollapsingToolbarLayout toolBarLayout = binding.toolbarLayout;
        toolBarLayout.setTitle(getTitle());

        FloatingActionButton fab = binding.fab;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras !=null) {
            assert binding.include.textViewMessage != null;
            binding.include.textViewMessage.setText("Message : " + extras.getString("message", getString(R.string.favorites_text_view_default)));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("lol", "Activité Favoris relancée, c'est parti mon kiki !");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("lol", "Activité Favoris détruite, c'était impressionnant.");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("lol", "Activité Favoris démarrée ! Accrochez vos ceintures.");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("lol", "Activité Favoris en pause ! Vous pouvez souffler un peu.");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("lol", "Activité Favoris arrêtée ! On n'en a pas besoin pour l'instant.");
    }
}