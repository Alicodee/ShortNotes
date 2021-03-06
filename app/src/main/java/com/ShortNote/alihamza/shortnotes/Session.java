package com.ShortNote.alihamza.shortnotes;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Ali Hamza on 11/28/2017.
 */

public class Session {
    public  SharedPreferences pref;
    public  SharedPreferences.Editor editor;
    Context _context;

    // shared pref mode
    public static final int PRIVATE_MODE = 0;

    // Shared preferences file name
    public static final String PREF_NAME = "ShortNotes";
    public static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";

    public Session(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public   void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public  boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

}