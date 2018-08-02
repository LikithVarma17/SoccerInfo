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

public class MyTeamAdapter extends RecyclerView.Adapter<MyTeamAdapter.MyTeamHolder> {
    ArrayList<JsonTeam> team;
    Context context;

    public MyTeamAdapter(Context context, ArrayList<JsonTeam> jsonTeams) {
        this.team = jsonTeams;
        this.context = context;
    }

    @NonNull
    @Override
    public MyTeamAdapter.MyTeamHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int id = R.layout.teams_list;
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.teams_list, parent, false);
        MyTeamHolder film = new MyTeamHolder(v);
        return film;

    }

    @Override
    public void onBindViewHolder(@NonNull MyTeamAdapter.MyTeamHolder holder, int position) {
        Picasso.with(context).load(team.get(position).getImage()).into(holder.image);
        holder.textView.setText(team.get(position).getTeam());
    }

    @Override
    public int getItemCount() {
        return team.size();
    }

    public class MyTeamHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView textView;

        public MyTeamHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.teamlogo);
            textView = (TextView) itemView.findViewById(R.id.name);
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, TeamDetailsDisplayer.class);
                    intent.putParcelableArrayListExtra(context.getResources().getString(R.string.TeamData), team);
                    intent.putExtra(context.getResources().getString(R.string.POS), getAdapterPosition());
                    context.startActivity(intent);
                }
            });
        }
    }
}
