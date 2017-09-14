package com.example.biro.footballsocer.Data;

import android.net.Uri;
import android.provider.BaseColumns;

import java.util.ArrayList;

/**
 * Created by Biro on 8/29/2017.
 */

public final class Contract {
    public static final String TAG = "league";
    static final String AUTHORITY = "com.example.biro.footballsocer";
    static final String PATH_TEAM = "Team";
    static final String PATH_MATCH = "Match";
    private static final Uri BASE_URI = Uri.parse("content://" + AUTHORITY);
    public static final String baseUrl = "https://api.fantasydata.net/v3/soccer/scores/json";
    public static final String standing = "/Standings/";
    public static final String teams = "/Teams";
    public static final String schedule = "/Schedule/";

    public static final class Teams implements BaseColumns {

        public static final Uri URI = BASE_URI.buildUpon().appendPath(PATH_TEAM).build();
        //        public static final Uri TEAM_BY_ID =  BASE_URI.buildUpon().appendPath(PATH_TEAM_WITH_ID).build();
        public static final String TABLE_NAME = "Team";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_PIC_URL = "pic";
        public static final String COLUMN_COUNTRY = "country";
        public static final String COLUMN_TEAM_ID = "team_id";
        public static final String COLUMN_POSITION = "position";
        public static final String COLUMN_PLAYED_GAMES = "played_games";
        public static final String COLUMN_GOALS = "goals";
        public static final String COLUMN_GOALS_AGAINST = "goals_ag";
        public static final String COLUMN_WINS = "win";
        public static final String COLUMN_LOSES = "lose";
        public static final String COLUMN_STADIUM = "stadium";
        public static final String COLUMN_DRAWS = "draw";
        public static final String COLUMN_ROUND_ID = "round_id";
        public static final String COLUMN_POINTS = "points";
        public static final String[] TEAM_COLUMNS = {COLUMN_NAME, COLUMN_PIC_URL, COLUMN_COUNTRY,
                COLUMN_TEAM_ID, COLUMN_POSITION, COLUMN_PLAYED_GAMES, COLUMN_GOALS, COLUMN_GOALS_AGAINST,
                COLUMN_WINS, COLUMN_LOSES, COLUMN_STADIUM, COLUMN_DRAWS, COLUMN_POINTS,COLUMN_ROUND_ID};


        public static final String name = "ShortName";
        public static final String teamID = "TeamId";
        public static final String wins = "Wins";
        public static final String losses = "Losses";
        public static final String draws = "Draws";
        public static final String points = "Points";
        public static final String gA = "GoalsAgainst";
        public static final String goals = "GoalsScored";
        public static final String position = "Order";
        public static final String games = "Games";
        public static final String round_id = "RoundId";

    }


    public static final class Match implements BaseColumns {

        public static final Uri URI = BASE_URI.buildUpon().appendPath(PATH_MATCH).build();
        public static final String TABLE_NAME = "match";
        public static final String COLUMN_GAME_ID = "game_id";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_STATUS = "status";
        public static final String COLUMN_ROUND_ID = "round_id";
        public static final String COLUMN_HOME_ID = "home_id";
        public static final String COLUMN_AWAY_ID = "away_id";
        public static final String COLUMN_HOME_TEAM = "home_team";
        public static final String COLUMN_AWAY_TEAM = "away_team";
        public static final String COLUMN_AWAY_PIC = "away_pic";
        public static final String COLUMN_HOME_PIC = "home_pic";
        public static final String COLUMN_AWAY_TEAM_GOALS = "away_team_goals";
        public static final String COLUMN_HOME_TEAM_GOALS = "home_team_goals";
        public static final String COLUMN_WEEK = "week";

        public static final String []MATCH_COLUMNS = {COLUMN_WEEK,COLUMN_AWAY_TEAM,
                COLUMN_HOME_TEAM,COLUMN_AWAY_ID,COLUMN_HOME_ID,COLUMN_DATE,COLUMN_STATUS,COLUMN_HOME_TEAM_GOALS,COLUMN_AWAY_TEAM_GOALS,COLUMN_HOME_PIC,COLUMN_AWAY_PIC};

        public static final String awayTeamId = "AwayTeamId";
        public static final String homeTeamId = "HomeTeamId";
        public static final String homeTeamName = "HomeTeamName";
        public static final String awayTeamName = "AwayTeamName";
        public static final String status = "Status";
        public static final String gameId = "GameId";
        public static final String week = "Week";
        public static final String homeTeamScore = "HomeTeamScore";
        public static final String awayTeamScore = "AwayTeamScore";
        public static final String dateTime = "DateTime";



    }
}
