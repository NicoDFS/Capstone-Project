package com.example.biro.footballsocer.models;

/**
 * Created by Biro on 9/7/2017.
 */

public class Game {

    private int week;
    private int gameId;
    private String status;
    private String homeTeamName;
    private String awayTeamName;
    private String homeTeamPic;
    private String awayTeamPic;
    private String date;
    private String time;
    private String homeTeamGoals;
    private String awayTeamGoals;


    public Game(String status, String homeTeamName, String awayTeamName, String homeTeamPic, String awayTeamPic, String date, String time, String homeTeamGoals, String awayTeamGoals) {

        this.status = status;
        this.homeTeamName = homeTeamName;
        this.awayTeamName = awayTeamName;
        this.homeTeamPic = homeTeamPic;
        this.awayTeamPic = awayTeamPic;
        this.date = date;
        this.time = time;
        this.homeTeamGoals = homeTeamGoals;
        this.awayTeamGoals = awayTeamGoals;
    }

    public int getWeek() {
        return week;
    }

    public int getGameId() {
        return gameId;
    }

    public String getStatus() {
        return status;
    }

    public String getHomeTeamName() {
        return homeTeamName;
    }

    public String getAwayTeamName() {
        return awayTeamName;
    }

    public String getHomeTeamPic() {
        return homeTeamPic;
    }

    public String getAwayTeamPic() {
        return awayTeamPic;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getHomeTeamGoals() {
        return homeTeamGoals;
    }

    public String getAwayTeamGoals() {
        return awayTeamGoals;
    }
}
