package com.julien.cefim_weatherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.julien.cefim_weatherapp.databinding.ActivityMainBinding;
import com.julien.cefim_weatherapp.models.City;
import com.julien.cefim_weatherapp.utils.Util;

import org.json.JSONException;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private OkHttpClient mOkHttpClient; // Client en charge des requêtes http
    private City mCurrentCity;
    private static final double LAT = 40.716709;
    private static final double LNG = -74.005698;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("lol", "Activité Principale créée chef !");

        // Création de la classe associée à la vue
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mOkHttpClient = new OkHttpClient();

        // Vérification de la connexion
        checkConnexion();

        Toast.makeText(this, binding.textViewCityName.getText(), Toast.LENGTH_SHORT).show();

        binding.editTextMessage.setText(R.string.edit_text_message);
    }

    private void checkConnexion() {
        if (Util.isActiveNetwork(binding.getRoot().getContext())) {
            Log.d("lol", "Connexion à internet établie ! We are live !");
            // Activité rendue visible
            setActivityVisibility(true);
            // Appel API
            apiWeatherCallWithCoord();
        } else {
            Log.d("lol", "Y a pas de connexion. C'est Bestel, chef : il a voulu brancher le truc sur sa CB, ça a fait Pffffii !");
            // Affichage non visible
            setActivityVisibility(false);
        }
    }

    private void setActivityVisibility(boolean b) {
        if (!b) {
            binding.activityLinearMain.setVisibility(View.GONE);
            binding.activityButtonFavorites.setVisibility(View.GONE);
            binding.activityTextViewNoConnexion.setVisibility(View.VISIBLE);
        }
        binding.activityLinearMain.setVisibility(View.VISIBLE);
        binding.activityButtonFavorites.setVisibility(View.VISIBLE);
        binding.activityTextViewNoConnexion.setVisibility(View.GONE);
    }

    private void apiWeatherCallWithCoord() {
        String[] coordinates = {String.valueOf(LAT), String.valueOf(LNG)};
        Request request = new Request.Builder().url("https://api.openweathermap.org/data/2.5/weather?lat=47.390026&lon=0.688891&appid=01897e497239c8aff78d9b8538fb24ea&units=metric&lang=fr").build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(binding.getRoot().getContext(), "Une erreur est survenue", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String stringJson = response.body().string();
                    Log.d("lol", "Nous recevons une communication à distance : " + stringJson);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            updateUi(stringJson);
                        }
                    });
                }
            }

            private void updateUi(String stringJson) {
                try {
                    mCurrentCity = new City(stringJson);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                binding.textViewCityName.setText(mCurrentCity.mName);
                binding.textViewCityDesc.setText(mCurrentCity.mDescription);
                binding.imageViewCityWeather.setImageResource(mCurrentCity.mWeatherResIconWhite);
                binding.textViewCityTemp.setText(mCurrentCity.mTemperature);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d("lol", "Activité Principale relancée ! Gardez bien les mains à l'intérieur du véhicule !");
        checkConnexion();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("lol", "Activité Principale détruite chef !");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("lol", "Activité Principale démarrée ! Le bébé ronronne chef.");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("lol", "Activité Principale en pause les gars !");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("lol", "Activité Principale arrêtée ! Merci de ne pas sortir avant l'arrivée en gare");
    }

    public void onClickButtonFavorites (View v) {
        Intent intent = new Intent(this, FavoriteActivity.class);
        intent.putExtra("message", binding.editTextMessage.getText().toString());
        startActivity(intent);
    }
}