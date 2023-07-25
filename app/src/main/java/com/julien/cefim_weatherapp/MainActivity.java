package com.julien.cefim_weatherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttp;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private LinearLayout mLinearLayoutMain;
    private TextView mTextViewNoConnexion;
    private TextView mTextViewCityName;
    private EditText mEditTextMessage;
    private Button mButtonFavorites;
    private OkHttpClient mOkHttpClient; // Client en charge des requêtes http

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("lol", "Activité Principale créée chef !");

        mTextViewCityName = findViewById(R.id.text_view_city_name);
        mTextViewCityName.setText(R.string.city_name);
        Toast.makeText(this, mTextViewCityName.getText(), Toast.LENGTH_SHORT).show();

        mEditTextMessage = findViewById(R.id.edit_text_message);

        mOkHttpClient = new OkHttpClient();

        // Vérification de la connexion
        connexionCheck();
    }

    private void connexionCheck() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            Log.d("lol", "Connexion à internet établie ! We are live !");
            // Appel API
            apiCall();
        } else {
            Log.d("lol", "Y a pas de connexion. C'est Bestel, chef : il a voulu brancher le truc sur sa CB, ça a fait Pffffii !");
            // Echec connexion
        }
    }

    private void apiCall() {
        Request request = new Request.Builder().url("https://api.openweathermap.org/data/2.5/weather?lat=47.390026&lon=0.688891&appid=01897e497239c8aff78d9b8538fb24ea&units=metric&lang=fr").build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String stringJson = response.body().string();
                    Log.d("lol", "Nous recevons une communication à distance : " + stringJson);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d("lol", "Activité Principale relancée ! Gardez bien les mains à l'intérieur du véhicule !");

        mLinearLayoutMain = findViewById(R.id.activity_linear_main);
        mButtonFavorites = findViewById(R.id.activity_button_favorites);
        mTextViewNoConnexion = findViewById(R.id.activity_no_connexion);

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(
                Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            // Oui je suis connecté à internet
            Log.d("lol", "Oui je suis connecté");
            mLinearLayoutMain.setVisibility(View.VISIBLE);
            mButtonFavorites.setVisibility(View.VISIBLE);
            mTextViewNoConnexion.setVisibility(View.GONE);
        } else {
            // Non
            Log.d("lol", "Ah merde je suis en Edge attends non j'ai plus de barre");
            mLinearLayoutMain.setVisibility(View.GONE);
            mButtonFavorites.setVisibility(View.GONE);
            mTextViewNoConnexion.setVisibility(View.VISIBLE);
        }
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
        intent.putExtra("message", mEditTextMessage.getText().toString());
        startActivity(intent);
    }
}