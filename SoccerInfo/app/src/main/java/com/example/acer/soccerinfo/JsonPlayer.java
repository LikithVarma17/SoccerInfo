package com.example.acer.soccerinfo;

import android.os.Parcel;
import android.os.Parcelable;

public class JsonPlayer implements Parcelable {
    public static final Creator<JsonPlayer> CREATOR = new Creator<JsonPlayer>() {
        @Override
        public JsonPlayer createFromParcel(Parcel in) {
            return new JsonPlayer(in);
        }

        @Override
        public JsonPlayer[] newArray(int size) {
            return new JsonPlayer[size];
        }
    };
    String id;
    String playerName;
    String teamName;
    String image;
    String nationality;
    String description;

    protected JsonPlayer(Parcel in) {
        id = in.readString();
        playerName = in.readString();
        teamName = in.readString();
        image = in.readString();
        nationality = in.readString();
        description = in.readString();
    }

    public JsonPlayer(String id, String playerName, String teamName, String image, String nationality, String description) {
        this.id = id;
        this.playerName = playerName;
        this.teamName = teamName;
        this.image = image;
        this.nationality = nationality;
        this.description = description;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(playerName);
        dest.writeString(teamName);
        dest.writeString(image);
        dest.writeString(nationality);
        dest.writeString(description);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getId() {
        return id;

    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
