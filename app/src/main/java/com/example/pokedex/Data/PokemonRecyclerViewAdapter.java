package com.example.pokedex.Data;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pokedex.Activities.DetailActivity;
import com.example.pokedex.Models.Pokemon;
import com.example.pokedex.R;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;

import static com.example.pokedex.Utility.StringFunctions.formatIndexNum;
import static com.example.pokedex.Utility.StringFunctions.formatList;

public class PokemonRecyclerViewAdapter extends RecyclerView.Adapter<PokemonRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Pokemon> pokemonList;

    public PokemonRecyclerViewAdapter(Context context, ArrayList<Pokemon> listPokemon) {
        this.context = context;
        pokemonList = listPokemon;
    }

    @NonNull
    @Override
    public PokemonRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.pokemon_row, viewGroup, false);

        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonRecyclerViewAdapter.ViewHolder viewHolder, int position) {

        Pokemon pokemon = pokemonList.get(position);

        if ((pokemon.getAbilities() != null) && (pokemon.getName() != null) && (pokemon.getIndexNum() != null) && (pokemon.getType() != null)) {

            String pokemonName = pokemon.getName().toUpperCase();
            viewHolder.name.setText(pokemonName);
            String imageLink = pokemon.getImage();
            Picasso.with(context).load(imageLink).into(viewHolder.image);

            // formatting index number
            viewHolder.index.setText("#" + formatIndexNum(pokemon.getIndexNum()));

            // Setting ability text
            String abilitiesText = "ABILITIES: ";
            abilitiesText = formatList(pokemon.getAbilities(), abilitiesText);
            viewHolder.abilities.setText(abilitiesText);

            // Setting Type text
            String typeText = "TYPE: ";
            typeText = formatList(pokemon.getType(), typeText);
            viewHolder.type.setText(typeText);

        }

    }

    @Override
    public int getItemCount() {
        if (pokemonList!= null) {
            return pokemonList.size();
        } else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name;
        ImageView image;
        TextView index;
        TextView abilities;
        TextView type;

        public ViewHolder(@NonNull View itemView, final Context ctx) {
            super(itemView);
            context = ctx;

            // connecting views
            name = itemView.findViewById(R.id.pokemonNameId);
            image = itemView.findViewById(R.id.pokemonImageId);
            index = itemView.findViewById(R.id.indexId);
            abilities = itemView.findViewById(R.id.abilitiesId);
            type = itemView.findViewById(R.id.typeId);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Pokemon pokemon = pokemonList.get(getAdapterPosition());

                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("pokemon", (Serializable) pokemon);
                    ctx.startActivity(intent);

                }
            });
        }

        @Override
        public void onClick(View v) {


        }
    }
}
