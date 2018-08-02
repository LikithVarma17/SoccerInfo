package com.example.acer.soccerinfo;

import android.os.Parcel;
import android.os.Parcelable;

public class JsonSchedule implements Parcelable {
    public static final Creator<JsonSchedule> CREATOR = new Creator<JsonSchedule>() {
        @Override
        public JsonSchedule createFromParcel(Parcel in) {
            return new JsonSchedule(in);
        }

        @Override
        public JsonSchedule[] newArray(int size) {
            return new JsonSchedule[size];
        }
    };
    String event;
    String date;
    String time;

    protected JsonSchedule(Parcel in) {
        event = in.readString();
        date = in.readString();
        time = in.readString();
    }

    public JsonSchedule(String event, String date, String time) {
        this.event = event;
        this.date = date;
        this.time = time;

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(event);
        dest.writeString(date);
        dest.writeString(time);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
