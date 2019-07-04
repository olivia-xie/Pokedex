package com.example.pokedex.Activities;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

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
import com.example.pokedex.Utility.Prefs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // Initializing views and variables
    private RecyclerView recyclerView;
    private PokemonRecyclerViewAdapter pokemonRecyclerViewAdapter;
    private Pokemon pokemon;
    private RequestQueue queue;
    private AlertDialog.Builder alertDialogBuilder;
    private AlertDialog dialog;
    private ArrayList<Pokemon> pokemonList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        queue = Volley.newRequestQueue(this);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showInputDialog();

            }
        });

        recyclerView = findViewById(R.id.recyclerViewId);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        pokemonList = new ArrayList<>();
        pokemon = new Pokemon();

        pokemonList = getAllPokemon();

        pokemonRecyclerViewAdapter = new PokemonRecyclerViewAdapter(this, pokemonList);
        recyclerView.setAdapter(pokemonRecyclerViewAdapter);
        pokemonRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;

    }

    // Opening Search alert dialog from menu Search button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_search) {

            showInputDialog();
        }

        return super.onOptionsItemSelected(item);
    }

    // Creating the Search alert dialog
    public void showInputDialog() {

        alertDialogBuilder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_view, null);
        final EditText newSearchEdit = view.findViewById(R.id.searchEdit);
        Button submitButton = view.findViewById(R.id.submitButton);

        alertDialogBuilder.setView(view);
        dialog = alertDialogBuilder.create();
        dialog.show();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Prefs prefs = new Prefs(MainActivity.this);

                if (!newSearchEdit.getText().toString().isEmpty()) {

                    String search = newSearchEdit.getText().toString().toLowerCase().trim();
                    prefs.setSearch(search);
                    getPokemon(search);

                    pokemonRecyclerViewAdapter.notifyDataSetChanged();

                }

                dialog.dismiss();
            }
        });
    }

    // Retrieves the specified pokemon from user search
    public Pokemon getPokemon(final String searchTerm) {

        pokemonList.clear();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Constants.POKEMON_URL_LEFT + searchTerm + Constants.URL_RIGHT, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject pokemonObject) {

                        try {

                            JSONObject pokemonSprites = pokemonObject.getJSONObject("sprites");

                            pokemon.setName(pokemonObject.getString("name"));
                            pokemon.setIndexNum(pokemonObject.getString("id"));
                            pokemon.setImage(pokemonSprites.getString("front_default"));

                            // Getting pokemon abilities
                            JSONArray getAbilitiesArray = pokemonObject.getJSONArray("abilities");
                            ArrayList<String> newAbilitiesArray = new ArrayList<>();
                            JSONObject abilityObject;
                            JSONObject abilityName;

                            for (int i = 0; i < getAbilitiesArray.length(); i++) {

                                abilityObject = getAbilitiesArray.getJSONObject(i);
                                abilityName = abilityObject.getJSONObject("ability");
                                newAbilitiesArray.add(abilityName.getString("name"));

                            }
                            pokemon.setAbilities(newAbilitiesArray);

                            // Getting pokemon types
                            JSONArray getTypeArray = pokemonObject.getJSONArray("types");
                            ArrayList<String> newTypeArray = new ArrayList<>();
                            JSONObject typeObject;
                            JSONObject typeName;

                            for (int i = 0; i < getTypeArray.length(); i++) {

                                typeObject = getTypeArray.getJSONObject(i);
                                typeName = typeObject.getJSONObject("type");
                                newTypeArray.add(typeName.getString("name"));

                            }
                            pokemon.setType(newTypeArray);

                            pokemonList.add(pokemon);

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
        return pokemon;

    }

    // Retrieves all pokemon to display on application start up
    public ArrayList<Pokemon> getAllPokemon() {

        for (int i = 1; i <= Constants.NUM_POKEMON; i++) {

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Constants.POKEMON_URL_LEFT + i, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject pokemonObject) {

                    try {

                        Pokemon newPokemon = new Pokemon();

                        JSONObject pokemonSprites = pokemonObject.getJSONObject("sprites");

                        newPokemon.setName(pokemonObject.getString("name"));
                        newPokemon.setIndexNum(pokemonObject.getString("id"));
                        newPokemon.setImage(pokemonSprites.getString("front_default"));

                        // Getting pokemon abilities
                        JSONArray getAbilitiesArray = pokemonObject.getJSONArray("abilities");
                        ArrayList<String> newAbilitiesArray = new ArrayList<>();
                        JSONObject abilityObject;
                        JSONObject abilityName;

                        for (int i = 0; i < getAbilitiesArray.length(); i++) {

                            abilityObject = getAbilitiesArray.getJSONObject(i);
                            abilityName = abilityObject.getJSONObject("ability");
                            newAbilitiesArray.add(abilityName.getString("name"));

                        }
                        newPokemon.setAbilities(newAbilitiesArray);

                        // Getting pokemon types
                        JSONArray getTypeArray = pokemonObject.getJSONArray("types");
                        ArrayList<String> newTypeArray = new ArrayList<>();
                        JSONObject typeObject;
                        JSONObject typeName;

                        for (int i = 0; i < getTypeArray.length(); i++) {

                            typeObject = getTypeArray.getJSONObject(i);
                            typeName = typeObject.getJSONObject("type");
                            newTypeArray.add(typeName.getString("name"));

                        }
                        newPokemon.setType(newTypeArray);

                        pokemonList.add(newPokemon);

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

        return pokemonList;

    }
}
