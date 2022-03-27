package com.cst2335.ghar0035;

import androidx.annotation.Nullable;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class MyOpenHelper extends SQLiteOpenHelper {

    public static final String fileName = "MessageDatabase";
    public static final int version = 1;
    public static final String TABLE_NAME = "MsgTable";
    public static final String COL_ID = "_id";
    public static final String COL_MESSAGE = "Message";
    public static final String COL_IS_SENT  = "IsSent";

/*
    context – the Activity where the database is being opened.
    databaseName – this is the filename that will contain the data.
    factory – An object to create Cursor objects, normally this is null.
    version – What is the version of your database
*/
    public MyOpenHelper(Context context) {
        super(context, fileName, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //Create table MsgTable ( _id INTEGER PRIMARY KEY AUTOINCREMENT, Message TEXT, COL_SEND BOOLEAN, COL_RECEIVE BOOLEAN );
        String result = String.format(" %s %s %s", "FirstMessege" , "True", "False" );

        //the creation statement. Create table MsgTable
        db.execSQL( String.format( "Create table %s ( %s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s INTEGER);"
                , TABLE_NAME, COL_ID, COL_MESSAGE, COL_IS_SENT) );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL( "Drop table if exists " + TABLE_NAME ); //deletes the current data

        //create a new table:
        this.onCreate(db); //calls function on line 26
    }
}