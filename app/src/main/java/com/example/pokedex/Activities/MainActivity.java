package com.example.pokedex.Activities;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import com.example.pokedex.Util.Constants;
import com.example.pokedex.Util.Prefs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PokemonRecyclerViewAdapter pokemonRecyclerViewAdapter;
    private Pokemon pokemon;
    private RequestQueue queue;
    private AlertDialog.Builder alertDialogBuilder;
    private AlertDialog dialog;

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

        pokemon = new Pokemon();

        Prefs prefs = new Prefs(MainActivity.this);
        String search = prefs.getSearch();
        pokemon = getPokemon(search);

        pokemonRecyclerViewAdapter = new PokemonRecyclerViewAdapter(this, pokemon);
        recyclerView.setAdapter(pokemonRecyclerViewAdapter);
        pokemonRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

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

                String search = newSearchEdit.getText().toString().toLowerCase();
                Log.d("search term: ", search);
                prefs.setSearch(search);
                getPokemon(search);

                pokemonRecyclerViewAdapter.notifyDataSetChanged();

                }

                dialog.dismiss();
            }
        });
    }

    // getting pokemon from search
    public Pokemon getPokemon(final String searchTerm) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Constants.URL_LEFT + searchTerm + Constants.URL_RIGHT, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject pokemonObject) {

                        try {

                            JSONObject pokemonSprites = pokemonObject.getJSONObject("sprites");

                            pokemon.setName(pokemonObject.getString("name"));
                            pokemon.setIndexNum(pokemonObject.getString("id"));
                            pokemon.setHeight(pokemonObject.getString("height"));
                            pokemon.setImage(pokemonSprites.getString("front_default"));

                            Log.d("name: ", pokemon.getName());

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
}
