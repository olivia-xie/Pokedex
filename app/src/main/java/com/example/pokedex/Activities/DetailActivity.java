package com.example.pokedex.Activities;

import android.app.DownloadManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.pokedex.Models.Pokemon;
import com.example.pokedex.R;
import com.example.pokedex.Utility.Constants;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    private Pokemon pokemon;
    private ImageView pokemonImageView;
    private TextView nameTextView;

    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        requestQueue = Volley.newRequestQueue(this);

        pokemon = (Pokemon) getIntent().getSerializableExtra("pokemon");

        setUpUI();
        getPokemonDetails(pokemon.getName());
    }

    private void getPokemonDetails(String searchTerm) {
        Log.d("get pokemon details", "entered");
        Log.d("search term", searchTerm);

        // Setting index num
        String indexNum = String.format("%3s", pokemon.getIndexNum());
        indexNum = indexNum.replace(' ', '0');

        Picasso.with(getApplicationContext()).load(pokemon.getImage()).into(pokemonImageView);
        nameTextView.setText("#" + indexNum + " - " + pokemon.getName().toUpperCase());

    }

    private void setUpUI() {

        pokemonImageView = findViewById(R.id.detailImageId);
        nameTextView = findViewById(R.id.detailNameId);

    }
}
