package com.example.acer.soccerinfo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MyResultAdapter extends RecyclerView.Adapter<MyResultAdapter.MyResultViewHolder> {
    ArrayList<JsonResult> jsonResults;
    Context context;

    public MyResultAdapter(Context result, ArrayList<JsonResult> jsonResults) {
        this.jsonResults = jsonResults;
        this.context = result;
    }

    @NonNull
    @Override
    public MyResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int id = R.layout.result_list;
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(id, parent, false);
        MyResultAdapter.MyResultViewHolder res = new MyResultAdapter.MyResultViewHolder(v);
        return res;

    }

    @Override
    public void onBindViewHolder(@NonNull MyResultViewHolder holder, int position) {
        holder.tname.setText(jsonResults.get(position).getName());
        holder.twin.setText(context.getString(R.string.played_win) + jsonResults.get(position).getWin());
        holder.tplay.setText(context.getString(R.string.played_matches) + jsonResults.get(position).getPlayed());
        holder.tloss.setText(context.getString(R.string.played_loss) + jsonResults.get(position).getLoss());
        holder.tdraw.setText(context.getString(R.string.played_draw) + jsonResults.get(position).getDraw());
    }

    @Override
    public int getItemCount() {
        return jsonResults.size();
    }


    public class MyResultViewHolder extends RecyclerView.ViewHolder {
        TextView tname, tplay, twin, tloss, tdraw;

        public MyResultViewHolder(View itemView) {
            super(itemView);
            tname = itemView.findViewById(R.id.teamName);
            tplay = itemView.findViewById(R.id.played);
            twin = itemView.findViewById(R.id.win);
            tloss = itemView.findViewById(R.id.loss);
            tdraw = itemView.findViewById(R.id.draw);
        }
    }
}
