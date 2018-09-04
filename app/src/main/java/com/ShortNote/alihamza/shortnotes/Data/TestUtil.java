package com.ShortNote.alihamza.shortnotes.Data;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class TestUtil {

    public static void insertFakeData(SQLiteDatabase db){
        if(db == null){
            return;
        }
        //create a list of fake guests
        List<ContentValues> list = new ArrayList<ContentValues>();
         ContentValues cv = new ContentValues();

        cv = new ContentValues();
        cv.put(NotesContract.NotesEntry.COLUMN_TTILE_NAME, "Tip");
        cv.put(NotesContract.NotesEntry.COLUMN__DEFINATION, "Swipe Right or Left to delete any Note");
        list.add(cv);

        cv = new ContentValues();
        cv.put(NotesContract.NotesEntry.COLUMN_TTILE_NAME, "Tip");
        cv.put(NotesContract.NotesEntry.COLUMN__DEFINATION, "Swipe Right or Left to delete A Note");
        list.add(cv);



        cv = new ContentValues();
        cv.put(NotesContract.NotesEntry.COLUMN_TTILE_NAME, "Developer Contact:");
        cv.put(NotesContract.NotesEntry.COLUMN__DEFINATION, "+923427212801");
        list.add(cv);


        cv = new ContentValues();
        cv.put(NotesContract.NotesEntry.COLUMN_TTILE_NAME, "Banana");
        cv.put(NotesContract.NotesEntry.COLUMN__DEFINATION, "Eat three bananas in a day for healthy life but  I love mangoes" );
        list.add(cv);

        cv = new ContentValues();
        cv.put(NotesContract.NotesEntry.COLUMN_TTILE_NAME, "Tip");
        cv.put(NotesContract.NotesEntry.COLUMN__DEFINATION, "Swipe Right or Left to delete A Note");
        list.add(cv);

        //insert all guests in one transaction
        try
        {
            db.beginTransaction();
            //clear the table first
            db.delete (NotesContract.NotesEntry.TABLE_NAME,null,null);
            //go through the list and add one by one
            for(ContentValues c:list){
                db.insert(NotesContract.NotesEntry.TABLE_NAME, null, c);
            }
            db.setTransactionSuccessful();
        }
        catch (SQLException e) {
            //too bad :(
        }
        finally
        {
            db.endTransaction();
        }

    }
}