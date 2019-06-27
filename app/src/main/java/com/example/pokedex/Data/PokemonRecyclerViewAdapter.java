package com.example.pokedex.Data;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.pokedex.Models.Pokemon;
import com.example.pokedex.R;
import com.squareup.picasso.Picasso;

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

        String imageLink = pokemon.getImage();
        viewHolder.name.setText(pokemon.getName());
        viewHolder.height.setText("Height: " + pokemon.getHeight() + " dm");
        Picasso.with(context).load(imageLink).into(viewHolder.image);

        // formatting index number
        String indexNum = String.format("%3s", pokemon.getIndexNum());
        indexNum = indexNum.replace(' ', '0');
        viewHolder.index.setText("#" + indexNum.replace(' ', '0'));

    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name;
        ImageView image;
        TextView index;
        TextView height;

        public ViewHolder(@NonNull View itemView, Context ctx) {
            super(itemView);
            context = ctx;

            // connecting views
            name = itemView.findViewById(R.id.pokemonNameId);
            image = itemView.findViewById(R.id.pokemonImageId);
            index = itemView.findViewById(R.id.indexId);
            height = itemView.findViewById(R.id.heightId);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Row Tapped", Toast.LENGTH_LONG).show();
                }
            });
        }

        @Override
        public void onClick(View v) {

        }
    }
}
