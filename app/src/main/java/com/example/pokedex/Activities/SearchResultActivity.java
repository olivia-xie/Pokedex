package com.example.pokedex.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.pokedex.Data.PokemonRecyclerViewAdapter;
import com.example.pokedex.Models.Pokemon;
import com.example.pokedex.R;
import com.example.pokedex.Utility.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchResultActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Pokemon searchedPokemon;
    private ArrayList<Pokemon> pokemonList;
    private PokemonRecyclerViewAdapter pokemonRecyclerViewAdapter;
    private String searchTerm;
    private RequestQueue queue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        searchTerm = getIntent().getStringExtra("searchTerm");

        queue = Volley.newRequestQueue(this);

        recyclerView = findViewById(R.id.searchRecyclerViewId);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        pokemonList = new ArrayList<>();
        searchedPokemon = new Pokemon();

        pokemonRecyclerViewAdapter = new PokemonRecyclerViewAdapter(this, pokemonList);
        recyclerView.setAdapter(pokemonRecyclerViewAdapter);

        getPokemon(searchTerm);
    }

    // Retrieves the specified searchedPokemon from user search
    public void getPokemon(final String searchTerm) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Constants.POKEMON_URL_LEFT + searchTerm + Constants.URL_RIGHT, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject pokemonObject) {

                        try {

                            JSONObject pokemonSprites = pokemonObject.getJSONObject("sprites");

                            searchedPokemon.setName(pokemonObject.getString("name"));
                            searchedPokemon.setIndexNum(pokemonObject.getString("id"));
                            searchedPokemon.setImage(pokemonSprites.getString("front_default"));

                            // Getting searchedPokemon abilities
                            JSONArray getAbilitiesArray = pokemonObject.getJSONArray("abilities");
                            ArrayList<String> newAbilitiesArray = new ArrayList<>();
                            JSONObject abilityObject;
                            JSONObject abilityName;

                            for (int i = 0; i < getAbilitiesArray.length(); i++) {

                                abilityObject = getAbilitiesArray.getJSONObject(i);
                                abilityName = abilityObject.getJSONObject("ability");
                                newAbilitiesArray.add(abilityName.getString("name"));

                            }
                            searchedPokemon.setAbilities(newAbilitiesArray);

                            // Getting searchedPokemon types
                            JSONArray getTypeArray = pokemonObject.getJSONArray("types");
                            ArrayList<String> newTypeArray = new ArrayList<>();
                            JSONObject typeObject;
                            JSONObject typeName;

                            for (int i = 0; i < getTypeArray.length(); i++) {

                                typeObject = getTypeArray.getJSONObject(i);
                                typeName = typeObject.getJSONObject("type");
                                newTypeArray.add(typeName.getString("name"));

                            }
                            searchedPokemon.setType(newTypeArray);

                            pokemonList.add(searchedPokemon);

                            pokemonRecyclerViewAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(jsonObjectRequest);

    }

}
