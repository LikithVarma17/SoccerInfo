package com.example.acer.soccerinfo;

import android.os.Parcel;
import android.os.Parcelable;

public class JsonResult implements Parcelable {
    public static final Creator<JsonResult> CREATOR = new Creator<JsonResult>() {
        @Override
        public JsonResult createFromParcel(Parcel in) {
            return new JsonResult(in);
        }

        @Override
        public JsonResult[] newArray(int size) {
            return new JsonResult[size];
        }
    };
    String name;
    int played;
    int win;
    int loss;
    int draw;

    public JsonResult(String name, int played, int win, int loss, int draw) {
        this.name = name;
        this.played = played;
        this.win = win;
        this.loss = loss;
        this.draw = draw;
    }

    protected JsonResult(Parcel in) {
        name = in.readString();
        played = in.readInt();
        win = in.readInt();
        loss = in.readInt();
        draw = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(played);
        dest.writeInt(win);
        dest.writeInt(loss);
        dest.writeInt(draw);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPlayed() {
        return played;
    }

    public void setPlayed(int played) {
        this.played = played;
    }

    public int getWin() {
        return win;
    }

    public void setWin(int win) {
        this.win = win;
    }

    public int getLoss() {
        return loss;
    }

    public void setLoss(int loss) {
        this.loss = loss;
    }

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }
}
