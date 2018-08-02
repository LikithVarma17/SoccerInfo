package com.example.acer.soccerinfo;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.MyPlayerViewHolder> {
    Context context;
    ArrayList<JsonPlayer> jsonPlayers;

    public PlayerAdapter(Context playerActivity, ArrayList<JsonPlayer> jsonPlayers) {
        this.context = playerActivity;
        this.jsonPlayers = jsonPlayers;
    }

    @NonNull
    @Override
    public PlayerAdapter.MyPlayerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int id = R.layout.player_list;
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.player_list, parent, false);
        PlayerAdapter.MyPlayerViewHolder film = new PlayerAdapter.MyPlayerViewHolder(v);
        return film;
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerAdapter.MyPlayerViewHolder holder, int position) {
        Picasso.with(context).load(jsonPlayers.get(position).getImage()).into(holder.iv);
        holder.tv.setText(jsonPlayers.get(position).getPlayerName());
    }

    @Override
    public int getItemCount() {
        return jsonPlayers.size();
    }

    public class MyPlayerViewHolder extends RecyclerView.ViewHolder {
        TextView tv;
        ImageView iv;

        public MyPlayerViewHolder(View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.playerlogo);
            tv = itemView.findViewById(R.id.playername);
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PlayerDetailsDisplayer.class);
                    intent.putParcelableArrayListExtra(context.getResources().getString(R.string.PlayerData), jsonPlayers);
                    intent.putExtra(context.getResources().getString(R.string.POS), getAdapterPosition());
                    context.startActivity(intent);
                }
            });
        }
    }
}
