package com.example.biro.footballsocer.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Biro on 9/11/2017.
 */

public class Team implements Parcelable {

    private String image;
    private String country;
    private String name;
    private String stadium;
    private int position;
    private int points;
    private int wins;
    private int loses;
    private int draws;
    private int goals;
    private int goalsAg;
    private int league;

    public int getGames() {
        return games;
    }

    private int games;

    public Team(String image, String country, String name, String stadium, int position, int points, int wins, int loses, int draws, int goals, int goalsAg, int league, int games) {
        this.image = image;
        this.country = country;
        this.name = name;
        this.stadium = stadium;
        this.position = position;
        this.points = points;
        this.wins = wins;
        this.loses = loses;
        this.draws = draws;
        this.goals = goals;
        this.goalsAg = goalsAg;
        this.league = league;
        this.games = games;
    }

    public String getImage() {
        return image;
    }

    public String getCountry() {
        return country;
    }

    public String getName() {
        return name;
    }

    public String getStadium() {
        return stadium;
    }

    public int getPosition() {
        return position;
    }

    public int getPoints() {
        return points;
    }

    public int getWins() {
        return wins;
    }

    public int getLoses() {
        return loses;
    }

    public int getDraws() {
        return draws;
    }

    public int getGoals() {
        return goals;
    }

    public int getGoalsAg() {
        return goalsAg;
    }

    public int getLeague() {
        return league;
    }

    protected Team(Parcel in) {
        image = in.readString();
        country = in.readString();
        name = in.readString();
        stadium = in.readString();
        position = in.readInt();
        points = in.readInt();
        wins = in.readInt();
        loses = in.readInt();
        draws = in.readInt();
        goals = in.readInt();
        goalsAg = in.readInt();
        league = in.readInt();
        games = in.readInt();
    }

    public static final Creator<Team> CREATOR = new Creator<Team>() {
        @Override
        public Team createFromParcel(Parcel in) {
            return new Team(in);
        }

        @Override
        public Team[] newArray(int size) {
            return new Team[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(image);
        dest.writeString(country);
        dest.writeString(name);
        dest.writeString(stadium);
        dest.writeInt(position);
        dest.writeInt(points);
        dest.writeInt(wins);
        dest.writeInt(loses);
        dest.writeInt(draws);
        dest.writeInt(goals);
        dest.writeInt(goalsAg);
        dest.writeInt(league);
        dest.writeInt(games);
    }
}
