package edu.ivytech.officecrimesfall2020.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static edu.ivytech.officecrimesfall2020.Database.CrimeDbSchema.*;

public class CrimeBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 2;
    private static final String DATABASE_NAME = "crimeBase.db";

    public CrimeBaseHelper(Context context)
    {
        super(context, DATABASE_NAME,null, VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + CrimeTable.NAME + " (" +
                "_id integer primary key autoincrement, " +
                CrimeTable.Cols.UUID + ", " +
                CrimeTable.Cols.TITLE + ", " +
                CrimeTable.Cols.DATE + ", " +
                CrimeTable.Cols.SOLVED + ", " +
                CrimeTable.Cols.SUSPECT + ");");
        /*create table CrimeTable.name (
        _id integer primary Key autoincrement,
        CrimeTable.Cols.UUID,
        CrimeTable.Cols.TITLE,
        CrimeTable.Cols.Date,
        CrimeTable.Cols.SOLVED);
         */
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        switch(i){
            case 1: sqLiteDatabase.execSQL("alter table " + CrimeTable.NAME + " add column " + CrimeTable.Cols.SUSPECT + ";");
            //case 2:
        }

    }
}
