package com.ShortNote.alihamza.shortnotes.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Ali Hamza on 10/14/2017.
 */

public class NotesDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "shorenotes.db";

    private static final int DATABASE_VERSION = 1;

    public NotesDbHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        // Create a table to hold ShortNotes data
        final String SQL_CREATE_SHORTNOTES_TABLE = "CREATE TABLE " + NotesContract.NotesEntry.TABLE_NAME + " (" +
                NotesContract.NotesEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                NotesContract.NotesEntry.count + " INTEGER," +
                NotesContract.NotesEntry.COLUMN_TTILE_NAME + " TEXT NOT NULL, " +
                NotesContract.NotesEntry.COLUMN__DEFINATION + " TEXT NOT NULL, " +
                NotesContract.NotesEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +

                "); ";

        sqLiteDatabase.execSQL(SQL_CREATE_SHORTNOTES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + NotesContract.NotesEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
