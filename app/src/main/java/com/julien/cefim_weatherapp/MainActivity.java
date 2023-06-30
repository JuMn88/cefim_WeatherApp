package com.julien.cefim_weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Context mContext;
    private LinearLayout mLinearLayoutMain;
    private TextView mTextViewNoConnexion;
    private TextView mTextViewCityName;
    private Button mButtonFavorites;
    private Button mButton2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

        mTextViewCityName = findViewById(R.id.text_view_city_name);
        mTextViewCityName.setText(R.string.city_name);
        Toast.makeText(this, mTextViewCityName.getText(), Toast.LENGTH_SHORT).show();

        mButton2 = findViewById(R.id.activity_button_2);
        View.OnClickListener onClickListenerButton2 = (v) -> Toast.makeText(mContext, "Clic sur Bouton 2", Toast.LENGTH_SHORT).show();;
        mButton2.setOnClickListener(onClickListenerButton2);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mLinearLayoutMain = findViewById(R.id.activity_linear_main);
        mButtonFavorites = findViewById(R.id.activity_button_favorites);
        mTextViewNoConnexion = findViewById(R.id.activity_no_connexion);

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(
                Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            // Oui je suis connecté à internet
            Log.d("TAG", "Oui je suis connecté");
            mLinearLayoutMain.setVisibility(View.VISIBLE);
            mButtonFavorites.setVisibility(View.VISIBLE);
            mTextViewNoConnexion.setVisibility(View.GONE);
        } else {
            // Non
            Log.d("TAG", "Ah merde je suis en Edge attends non j'ai plus de barre");
            mLinearLayoutMain.setVisibility(View.GONE);
            mButtonFavorites.setVisibility(View.GONE);
            mTextViewNoConnexion.setVisibility(View.VISIBLE);
        }
    }

    public void onClickButton (View v) {
        if (v.getId() == R.id.activity_button_1) {
            Toast.makeText(this, "Clic sur Bouton 1", Toast.LENGTH_SHORT).show();
        } else if (v.getId() == R.id.activity_button_favorites) {
            Toast.makeText(this, "Clic sur Favoris", Toast.LENGTH_SHORT).show();
        }
    }
}