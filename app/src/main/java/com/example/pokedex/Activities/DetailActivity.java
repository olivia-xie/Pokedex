package com.example.pokedex.Activities;

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

public class DetailActivity extends AppCompatActivity {

    private Pokemon pokemon;
    private ImageView pokemonImageView;
    private TextView nameTextView;
    private TextView flavorTextView;
    private TextView heightTextView;
    private TextView weightTextView;
    private TextView evolvesFromTextView;
    private TextView genusTextView;

    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        requestQueue = Volley.newRequestQueue(this);

        pokemon = (Pokemon) getIntent().getSerializableExtra("pokemon");

        setUpUI();
        getPokemonSpeciesDetails(pokemon.getName());
        getPokemonDetails(pokemon.getName());

    }

    // Gets information from Pokemon Species object
    private void getPokemonSpeciesDetails(String searchTerm) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Constants.SPECIES_URL_LEFT + searchTerm + Constants.URL_RIGHT, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject pokemonObject) {

                try {

                    // Getting flavour text
                    JSONArray allFlavorTextArray = pokemonObject.getJSONArray("flavor_text_entries");
                    for (int i = 0; i < allFlavorTextArray.length(); i++) {

                        JSONObject flavorTextObject = allFlavorTextArray.getJSONObject(i);
                        JSONObject langObject = flavorTextObject.getJSONObject("language");
                        String language = langObject.getString("name");
                        if (language.equals("en")) {
                            String flavorText = flavorTextObject.getString("flavor_text").replace('\n', ' ');
                            flavorTextView.setText(flavorText);
                        }
                    }

                    // Getting previous pokemon from evolution
                    // if (pokemonObject.getString("evolves_from_species") != null) {
                    //  evolvesFromTextView.setText(pokemonObject.getString("evolves_from_species"));
                    //}

                    // Getting genus type
                    JSONArray genusArray = pokemonObject.getJSONArray("genera");
                    String genus = genusArray.getJSONObject(2).getString("genus");
                    genusTextView.setText(genus);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("volley error", error.toString());
            }
        });

        requestQueue.add(jsonObjectRequest);

    }

    // Gets information from Pokemon object
    private void getPokemonDetails(String searchTerm) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Constants.POKEMON_URL_LEFT + searchTerm + Constants.URL_RIGHT, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject pokemonObject) {

                try {

                    // getting height
                    String heightStr = pokemonObject.getString("height");
                    int heightNum = Integer.parseInt(heightStr);
                    heightNum *= 10;
                    heightTextView.setText(heightNum + " CM");

                    // getting weight
                    String weightStr = pokemonObject.getString("weight");
                    int weightNum = Integer.parseInt(weightStr);
                    weightNum /= 10;
                    weightTextView.setText(weightNum + " KG");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    // Sets views and other UI stuff
    private void setUpUI() {

        pokemonImageView = findViewById(R.id.detailImageViewId);
        nameTextView = findViewById(R.id.detailNameId);
        genusTextView = findViewById(R.id.detailGenusId);
        heightTextView = findViewById(R.id.heightId);
        weightTextView = findViewById(R.id.weightId);
        flavorTextView = findViewById(R.id.flavorTextId);

        // Setting name and index number textview
        String indexNum = String.format("%3s", pokemon.getIndexNum());
        indexNum = indexNum.replace(' ', '0');
        String detailName = indexNum + " " + pokemon.getName();
        nameTextView.setText(detailName);

        // Setting photo
        Picasso.with(getApplicationContext()).load(pokemon.getImage()).into(pokemonImageView);

    }
}
