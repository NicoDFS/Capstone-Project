package com.example.biro.footballsocer.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Biro on 8/29/2017.
 */

public class DbHelper extends SQLiteOpenHelper {

    private static final String NAME = "footballsoccer.db";
    private static final int VERSION = 2;
    private static  String PATH = "/data/data/";
    private static  String DIR = "/databases/";
    Context context;
    private static DbHelper instance = null;
    private static String DB_PATH;


    private DbHelper(Context context) {
        super(context, NAME, null, VERSION);
        this.context = context;
        DB_PATH = PATH+context.getPackageName()+DIR;
    }

    public static DbHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DbHelper(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String builder = "CREATE TABLE " + Contract.Teams.TABLE_NAME + " ("
                + Contract.Teams._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Contract.Teams.COLUMN_NAME + " TEXT NOT NULL, "
                + Contract.Teams.COLUMN_ROUND_ID + " INTEGER NOT NULL, "
                + Contract.Teams.COLUMN_STADIUM + " TEXT , "
                + Contract.Teams.COLUMN_PIC_URL + " TEXT , "
                + Contract.Teams.COLUMN_COUNTRY + " TEXT , "
                + Contract.Teams.COLUMN_POSITION + " INTEGER NOT NULL, "
                + Contract.Teams.COLUMN_GOALS + " INTEGER NOT NULL, "
                + Contract.Teams.COLUMN_PLAYED_GAMES + " INTEGER NOT NULL, "
                + Contract.Teams.COLUMN_GOALS_AGAINST + " INTEGER NOT NULL, "
                + Contract.Teams.COLUMN_WINS + " INTEGER NOT NULL, "
                + Contract.Teams.COLUMN_LOSES + " INTEGER NOT NULL, "
                + Contract.Teams.COLUMN_POINTS + " INTEGER NOT NULL, "
                + Contract.Teams.COLUMN_TEAM_ID + " INTEGER NOT NULL, "
                + Contract.Teams.COLUMN_DRAWS + " INTEGER NOT NULL); ";

        String builder2 = "CREATE TABLE " + Contract.Match.TABLE_NAME + " ("
                + Contract.Match._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Contract.Match.COLUMN_GAME_ID + " INTEGER NOT NULL, "
                + Contract.Match.COLUMN_HOME_ID + " INTEGER NOT NULL, "
                + Contract.Match.COLUMN_AWAY_ID + " INTEGER NOT NULL, "
                + Contract.Match.COLUMN_ROUND_ID + " INTEGER NOT NULL, "
                + Contract.Match.COLUMN_AWAY_TEAM + " TEXT NOT NULL, "
                + Contract.Match.COLUMN_AWAY_PIC + " TEXT NOT NULL, "
                + Contract.Match.COLUMN_HOME_PIC+ " TEXT NOT NULL, "

                + Contract.Match.COLUMN_HOME_TEAM + " INTEGER NOT NULL, "
                + Contract.Match.COLUMN_AWAY_TEAM_GOALS + " TEXT NOT NULL , "
                + Contract.Match.COLUMN_HOME_TEAM_GOALS + " TEXT NOT NULL,"
                + Contract.Match.COLUMN_DATE + " TEXT NOT NULL, "
                + Contract.Match.COLUMN_STATUS + " TEXT NOT NULL, "
                + Contract.Match.COLUMN_WEEK + " INTEGER NOT NULL); ";


        db.execSQL(builder);
        db.execSQL(builder2);

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    private boolean checkDataBase() {
        File databasePath = context.getDatabasePath(NAME);
        return databasePath.exists();
    }
    private void copyDataBase() throws IOException {

        //Open your local db as the input stream
        InputStream myInput = context.getAssets().open(NAME);

        // Path to the just created empty db
        String outFileName = DB_PATH + NAME;

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }


    public void createDataBase() throws IOException {

        boolean dbExist = checkDataBase();



        //By calling this method and empty database will be created into the default system path
        //of your application so we are gonna be able to overwrite that database with our database.
        this.getReadableDatabase();


        try {

            copyDataBase();

        } catch (IOException e) {

            throw new Error("Error copying database");

        }
    }







}
