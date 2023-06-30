package com.julien.cefim_weatherapp;

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

public class MainActivity extends AppCompatActivity {

    private LinearLayout mLinearLayoutMain;
    private TextView mTextViewNoConnexion;
    private TextView mTextViewCityName;
    private EditText mEditTextMessage;
    private Button mButtonFavorites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("lol", "Activité Principale créée chef !");

        mTextViewCityName = findViewById(R.id.text_view_city_name);
        mTextViewCityName.setText(R.string.city_name);
        Toast.makeText(this, mTextViewCityName.getText(), Toast.LENGTH_SHORT).show();

        mEditTextMessage = findViewById(R.id.edit_text_message);
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