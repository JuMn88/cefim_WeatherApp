package com.julien.cefim_weatherapp.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.julien.cefim_weatherapp.R;
import com.julien.cefim_weatherapp.models.City;

import java.util.ArrayList;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<City> mCities;
    private int counter;

    //Constructor
    public FavoriteAdapter(Context mContext, ArrayList<City> mCities) {
        this.counter = 1;
        this.mContext = mContext;
        this.mCities = mCities;
    }

    //Classe holder qui contient la vue d'un item
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        public TextView mTextViewCityName;
        public TextView mTextViewDescription;
        public TextView mTextViewTemperature;
        public ImageView mImageView;
        public City mCity;
        public Integer mId; // Sert uniquement pour démontrer le nombre de views créées dans le log

        public ViewHolder(View view) {
            super(view);
            mTextViewCityName = view.findViewById(R.id.item_favorite_text_view_city_name);
            mTextViewDescription = view.findViewById(R.id.item_favorite_text_view_description);
            mTextViewTemperature = view.findViewById(R.id.item_favorite_text_view_temperature);
            mImageView = view.findViewById(R.id.item_favorite_image_view);
            view.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View view) {
            Log.d("lol", "Clic long détecté sur l'item n°" + this.mId + " : " + this.mCity.mName + " chef !");
            mCities.remove(this.mCity);
            notifyItemRemoved(getAdapterPosition());
            return false;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("lol", "Création de la vue n°" + counter + " !");

        View v = LayoutInflater.from(mContext).inflate(R.layout.item_favorite_city, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);

        viewHolder.mId = counter;
        counter++;

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        City city = mCities.get(position);

        Log.d("lol", "Vue n°" + holder.mId + " revêtue pour la ville " + city.mName);

        holder.mTextViewCityName.setText(city.mName);
        holder.mTextViewDescription.setText(city.mDescription);
        holder.mTextViewTemperature.setText(city.mTemperature);
        holder.mImageView.setImageResource(city.mWeatherIconGrey);
        holder.mCity = city;
    }

    @Override
    public int getItemCount() {

        if (mCities == null) return 0;
        return mCities.size();
    }
}
