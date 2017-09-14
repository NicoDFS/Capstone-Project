package com.example.biro.footballsocer.Data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.biro.footballsocer.Ui.GameWidget;
import com.example.biro.footballsocer.Utils.SharedPref;

/**
 * Created by Biro on 8/29/2017.
 */

public class DataProvider extends ContentProvider {

    private static final int TEAM = 1;
    private static final int MATCH = 2;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private DbHelper DbInstance;

    static {

        sUriMatcher.addURI(Contract.AUTHORITY, Contract.PATH_TEAM, 1);
        sUriMatcher.addURI(Contract.AUTHORITY, Contract.PATH_MATCH, 2);


    }

    @Override
    public boolean onCreate() {
        DbInstance = DbHelper.getInstance(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor returnCursor;
        String filterBy;
        SQLiteDatabase db = DbInstance.getReadableDatabase();
        int x = sUriMatcher.match(uri);
        switch (sUriMatcher.match(uri)) {
            case TEAM:
                if (selection == null) {
                    returnCursor = db.query(
                            Contract.Teams.TABLE_NAME,
                            projection,
                            null,
                            null,
                            null,
                            null,
                            sortOrder);
                } else {

                    returnCursor = db.query(
                            Contract.Teams.TABLE_NAME,
                            projection,
                            selection + " = ?",
                            selectionArgs,
                            null,
                            null,
                            sortOrder);
                }


                break;

            case MATCH:

                if (selection == null) {
                    returnCursor = db.query(
                            Contract.Match.TABLE_NAME,
                            projection,
                            null,
                            null,
                            null,
                            null,
                            sortOrder);
                } else {
                    if ('%' == selectionArgs[0].charAt(0)) {
                        filterBy = " LIKE ";
                    } else
                        filterBy = " = ";
                    returnCursor = db.query(
                            Contract.Match.TABLE_NAME,
                            projection,
                            selection + filterBy + "?",
                            selectionArgs,
                            null,
                            null,
                            sortOrder);
                }

                break;
            default:
                throw new UnsupportedOperationException("Unknown URI:" + uri);
        }
        return returnCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        SQLiteDatabase db = DbInstance.getWritableDatabase();
        Uri returnUri;

        switch (sUriMatcher.match(uri)) {
            case TEAM:
                db.insert(
                        Contract.Teams.TABLE_NAME,
                        null,
                        values
                );
                returnUri = Contract.Teams.URI;
                break;
            default:
                throw new UnsupportedOperationException("Unknown URI:" + uri);
        }
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = DbInstance.getWritableDatabase();
        switch (sUriMatcher.match(uri)) {
            case TEAM:
                return db.delete(Contract.Teams.TABLE_NAME, selection, selectionArgs);
            default:
                throw new UnsupportedOperationException("Unknown URI:" + uri);
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = DbInstance.getWritableDatabase();

        switch (sUriMatcher.match(uri)) {
            case TEAM:
                return db.update(Contract.Teams.TABLE_NAME, values, selection + " =? ", selectionArgs);

            case MATCH:

                return db.update(Contract.Match.TABLE_NAME, values, selection + " =? ", selectionArgs);


            default:
                throw new UnsupportedOperationException("Unknown URI:" + uri);

        }


    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {

        final SQLiteDatabase db = DbInstance.getWritableDatabase();
        db.beginTransaction();
        int returnCount = 0;
        Context context = getContext();


        switch (sUriMatcher.match(uri)) {
            case TEAM:
                try {
                    for (ContentValues value : values) {
                        long id = db.insert(
                                Contract.Teams.TABLE_NAME,
                                null,
                                value
                        );
                        if (id > 0) returnCount++;
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }

                if (context != null) {
                    context.getContentResolver().notifyChange(uri, null);
                }
                break;
            case MATCH:

                try {
                    for (ContentValues value : values) {
                        long id = db.insert(
                                Contract.Match.TABLE_NAME,
                                null,
                                value
                        );
                        if (id > 0) returnCount++;
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }

                if (context != null) {
                    context.getContentResolver().notifyChange(uri, null);
                }
                break;


            default:
                return super.bulkInsert(uri, values);
        }
        return returnCount;


    }
}
