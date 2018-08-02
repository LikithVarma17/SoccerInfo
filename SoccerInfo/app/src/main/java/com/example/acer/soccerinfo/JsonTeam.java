package com.example.acer.soccerinfo;

import android.os.Parcel;
import android.os.Parcelable;

public class JsonTeam implements Parcelable {

    public static final Creator<JsonTeam> CREATOR = new Creator<JsonTeam>() {
        @Override
        public JsonTeam createFromParcel(Parcel in) {
            return new JsonTeam(in);
        }

        @Override
        public JsonTeam[] newArray(int size) {
            return new JsonTeam[size];
        }
    };
    String team;
    String image;
    String otherName;
    String id;
    String teamShortCut;
    String website;
    String description;

    protected JsonTeam(Parcel in) {
        team = in.readString();
        image = in.readString();
        otherName = in.readString();
        id = in.readString();
        teamShortCut = in.readString();
        website = in.readString();
        description = in.readString();
    }

    public JsonTeam(String team, String image, String otherName, String id, String teamShortCut, String website, String description) {
        this.team = team;
        this.image = image;
        this.otherName = otherName;
        this.id = id;
        this.teamShortCut = teamShortCut;
        this.website = website;
        this.description = description;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(team);
        dest.writeString(image);
        dest.writeString(otherName);
        dest.writeString(id);
        dest.writeString(teamShortCut);
        dest.writeString(website);
        dest.writeString(description);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getOtherName() {
        return otherName;
    }

    public void setOtherName(String otherName) {
        this.otherName = otherName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTeamShortCut() {
        return teamShortCut;
    }

    public void setTeamShortCut(String teamShortCut) {
        this.teamShortCut = teamShortCut;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
