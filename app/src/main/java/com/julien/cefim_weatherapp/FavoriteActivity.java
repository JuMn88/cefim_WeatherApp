package com.julien.cefim_weatherapp;

import android.app.AlertDialog;
import android.content.ContentProviderOperation;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.julien.cefim_weatherapp.adapters.FavoriteAdapter;
import com.julien.cefim_weatherapp.databinding.ActivityFavoriteBinding;
import com.julien.cefim_weatherapp.models.City;
import com.julien.cefim_weatherapp.utils.UtilAPI;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FavoriteActivity extends AppCompatActivity {

    private ActivityFavoriteBinding binding; // Permet de récupérer toute la vue (et donc ses membres) --> évite de créer une variable pour chaque membre
    private ArrayList<City> mCities;
    private Context mContext;
    private RecyclerView mRecyclerView;
    private FavoriteAdapter mAdapter;
    private OkHttpClient mOkHttpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mContext = this;

        init();

        super.onCreate(savedInstanceState);

        Log.d("lol", "Activité Favoris créée chef, c'est de toute beauté !");

        binding = ActivityFavoriteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true); // Affiche la flèche Retour en haut à gauche de la page

        CollapsingToolbarLayout toolBarLayout = binding.toolbarLayout;
        toolBarLayout.setTitle(getTitle());

        mRecyclerView = binding.include.myRecyclerView;
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        mAdapter = new FavoriteAdapter(mContext, mCities);
        mRecyclerView.setAdapter(mAdapter);

        FloatingActionButton fab = binding.fab;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Je garde le snackbar juste pour avoir un exemple d'utilisation de snackbar
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

                final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

                View v = LayoutInflater.from(mContext).inflate(R.layout.dialog_add_favorite, null);
                builder.setView(v);

                builder.setPositiveButton(android.R.string.ok, (dialogInterface, i) -> addCityDialog(v));
                builder.setNegativeButton(android.R.string.cancel, null);
                builder.create().show();
            }

        });
    }


    private void addCityDialog(View v) {
        final EditText editText = v.findViewById(R.id.edit_text_dialog_city);
        String cityName = editText.getText().toString();
        if (cityName.length() > 0) {
            String[] params = {cityName};
            String RequestUrl = String.format(UtilAPI.OPEN_WEATHER_MAP_API_CITY_NAME, params);
            Request request = new Request.Builder().url(RequestUrl).build();

            mOkHttpClient = new OkHttpClient();
            mOkHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    runOnUiThread(() -> Toast.makeText(binding.getRoot().getContext(), "Une erreur est survenue", Toast.LENGTH_SHORT).show());
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    final String stringJson = response.body().string();
                    if (response.isSuccessful() && UtilAPI.isSuccessful(stringJson)) {
                        Log.d("lol", "Voilà les infos du QG : " + stringJson);
                        runOnUiThread(() -> updateUi(stringJson));
                    }
                }

            });
            //City city = new City(cityName, "Ensoleillé", "22°C", R.drawable.weather_sunny_grey);
            //mCities.add(0, city); // L'index à 0 pour que la nouvelle ville apparaisse en haut de la liste
            //mAdapter.notifyDataSetChanged();
        }
    }

    private void updateUi(String stringJson) {
        try {
            City city = new City(stringJson);
            mCities.add(city);
            mAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            Toast.makeText(mContext, getString(R.string.place_not_found), Toast.LENGTH_SHORT).show();
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

    private void init() {
        mCities = new ArrayList<>();
    }
}