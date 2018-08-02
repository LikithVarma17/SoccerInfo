package com.example.acer.soccerinfo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyScheduleAdapter extends RecyclerView.Adapter<MyScheduleAdapter.MyScheduleHolder> {
    ArrayList<JsonSchedule> jsonSchedules;
    Context context;

    public MyScheduleAdapter(Context schedule, ArrayList<JsonSchedule> jsonSchedule) {
        this.jsonSchedules = jsonSchedule;
        this.context = schedule;
    }

    @NonNull
    @Override
    public MyScheduleAdapter.MyScheduleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int id = R.layout.schedule_list;
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.schedule_list, parent, false);
        MyScheduleAdapter.MyScheduleHolder sch = new MyScheduleAdapter.MyScheduleHolder(v);
        return sch;


    }

    @Override
    public void onBindViewHolder(@NonNull MyScheduleAdapter.MyScheduleHolder holder, int position) {
        holder.event.setText(jsonSchedules.get(position).getEvent());
        holder.date.setText(jsonSchedules.get(position).getDate());
        holder.time.setText(jsonSchedules.get(position).getTime());
        holder.icon.setImageResource(R.drawable.versus);
        Log.i("data", jsonSchedules.get(position).getEvent());
    }

    @Override
    public int getItemCount() {
        return jsonSchedules.size();
    }

    public class MyScheduleHolder extends RecyclerView.ViewHolder {
        TextView event;
        ImageView icon;
        TextView date;
        TextView time;

        public MyScheduleHolder(View itemView) {
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id.icon);
            event = (TextView) itemView.findViewById(R.id.event);
            date = (TextView) itemView.findViewById(R.id.date);
            time = (TextView) itemView.findViewById(R.id.time);


        }
    }
}
