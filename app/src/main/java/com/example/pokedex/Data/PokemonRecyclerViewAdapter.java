package com.example.pokedex.Data;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pokedex.Activities.DetailActivity;
import com.example.pokedex.Models.Pokemon;
import com.example.pokedex.R;
import com.squareup.picasso.Picasso;

import java.io.Serializable;

public class PokemonRecyclerViewAdapter extends RecyclerView.Adapter<PokemonRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private Pokemon pokemon;

    public PokemonRecyclerViewAdapter(Context context, Pokemon aPokemon) {
        this.context = context;
        pokemon = aPokemon;
    }

    @NonNull
    @Override
    public PokemonRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.pokemon_row, viewGroup, false);

        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonRecyclerViewAdapter.ViewHolder viewHolder, int position) {

        if ((pokemon.getAbilities() != null) && (pokemon.getName() != null) && (pokemon.getIndexNum() != null) && (pokemon.getType() != null)) {

            String pokemonName = pokemon.getName().substring(0, 1).toUpperCase() + pokemon.getName().substring(1).toLowerCase();
            viewHolder.name.setText(pokemonName);
            String imageLink = pokemon.getImage();
            Picasso.with(context).load(imageLink).into(viewHolder.image);

            // formatting index number
            String indexNum = String.format("%3s", pokemon.getIndexNum());
            indexNum = indexNum.replace(' ', '0');
            viewHolder.index.setText("#" + indexNum.replace(' ', '0'));

            // Setting ability text
            String abilitiesText = "Abilities: ";
            for (String ability : pokemon.getAbilities()) {

                if (pokemon.getAbilities().indexOf(ability) == (pokemon.getAbilities().size() - 1)) {
                    abilitiesText += ability.substring(0, 1).toUpperCase() + ability.substring(1).toLowerCase();
                } else {
                    abilitiesText += ability.substring(0, 1).toUpperCase() + ability.substring(1).toLowerCase() + ", ";
                }
            }
            viewHolder.abilities.setText(abilitiesText);

            // Setting Type text
            String typeText = "Type: ";
            for (String type : pokemon.getType()) {

                if (pokemon.getType().indexOf(type) == (pokemon.getType().size() - 1)) {
                    typeText += type.substring(0, 1).toUpperCase() + type.substring(1).toLowerCase();
                } else {
                    typeText += type.substring(0, 1).toUpperCase() + type.substring(1).toLowerCase() + ", ";
                }
            }
            viewHolder.type.setText(typeText);

        }

    }

    @Override
    public int getItemCount() {
        return 1;
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
