package com.ShortNote.alihamza.shortnotes.Data;

import android.provider.BaseColumns;

/**
 * Created by Ali Hamza on 10/10/2017.
 */

public class NotesContract {

    public static final class NotesEntry implements BaseColumns{

        public static final String TABLE_NAME = "shortnotes";
        public static final String count="counter";
        public static final String COLUMN_TTILE_NAME = "title";
        public static final String COLUMN__DEFINATION = "defination";
        public static final String COLUMN_TIMESTAMP = "timestamp";


    }
}
