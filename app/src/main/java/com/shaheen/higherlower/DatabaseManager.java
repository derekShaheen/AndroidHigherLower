package com.shaheen.higherlower;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.UUID;

/**
 * @author Derek Shaheen
 * @description This is designed to handle the inserts and selects from the database.
 * @date: Apr 17, 2020
 * @version: 1.1
 */

public class DatabaseManager extends SQLiteOpenHelper {

    public static final String TABLE_SCORE = "score"; // Name of the table
    public static final String TABLE_SCORE_C_ID = "_id";           // Primary key column
    public static final String TABLE_SCORE_C_SCORE = "score";  // Name of comment field
    public static final String TABLE_SCORE_C_SESSION = "session"; // Session ID
    public static final String TABLE_SCORE_C_ROUND = "round"; // Round number the score was achieved
    private static final String DATABASE_NAME = "db.db"; // Name of db file
    private static final int DATABASE_VERSION = 7;
    /**
     * CREATE TABLE "score" (
     * "_id"	INTEGER PRIMARY KEY AUTOINCREMENT,
     * "session"	NVARCHAR(30),
     * "score"	TEXT NOT NULL,
     * "round" TEXT NOT NULL
     * );
     */
    private static final String DATABASE_CREATE = "CREATE TABLE "
            + TABLE_SCORE + "( "
            + TABLE_SCORE_C_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TABLE_SCORE_C_SESSION + " NVARCHAR(30), "
            + TABLE_SCORE_C_SCORE + " INTEGER NOT NULL, "
            + TABLE_SCORE_C_ROUND + " TEXT NOT NULL"
            + ");";
    public static String sessionID = generateSessionID();

    /**
     * Constructor
     *
     * @param context Specify the contect
     */
    public DatabaseManager(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static String generateSessionID() {
        return UUID.randomUUID().toString();
    }

    public static String getSessionID() {
        return sessionID;
    }

    public static void setSessionID(String sessionID) {
        DatabaseManager.sessionID = sessionID;
    }

    /**
     * Used to create the databased on first create
     *
     * @param database Specify the database
     */
    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    /**
     * Used to upgrade the data from an old version to a new version
     *
     * @param db         Specify the database
     * @param oldVersion Specify the old version
     * @param newVersion Specify the new version
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DatabaseManager.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCORE);
        onCreate(db);
    }

    public void dbInsertNewScore(String score, String round) {
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            dbDeleteSessionScores(); // Let's only keep the high score for each session.

            ContentValues cV = new ContentValues();
            cV.put(TABLE_SCORE_C_SCORE, score);
            cV.put(TABLE_SCORE_C_ROUND, round);
            cV.put(TABLE_SCORE_C_SESSION, sessionID);
            Log.d("INSERT", "Success: " + db.insertOrThrow(TABLE_SCORE, null, cV)); // Perform the insert
        } catch (SQLException ex) {
            Log.d("ERROR", ex.getMessage());
            Log.d("dbMgr", "FAILED TO DELETE OR INSERT SCORES");
        }
    }

    public void dbDeleteSessionScores() {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(TABLE_SCORE, TABLE_SCORE_C_SESSION + " = '" + getSessionID() + "'", null);
        } catch (Exception ex) {
            Log.d("ERROR", ex.getMessage());
        }

    }

    public int dbGetHighScore() {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor hs = db.rawQuery(
                    "SELECT score FROM "
                            + TABLE_SCORE
                            + " ORDER BY "
                            + TABLE_SCORE_C_SCORE + " desc "
                            + "LIMIT 1"
                    , null);
            if (hs.moveToFirst()) {
                String temp = hs.getString(hs.getColumnIndex(TABLE_SCORE_C_SCORE));
                if (temp.isEmpty() || temp.equals("")) {
                    temp = "0";
                } // Coalesce nulls and emptys to 0
                Log.d("GET", temp);
                return Integer.parseInt(temp);
            } else {
                return 0;
            }
        } catch (Exception ex) {
            Log.d("ERROR", ex.getMessage());
            return 0;
        }
    }
}
