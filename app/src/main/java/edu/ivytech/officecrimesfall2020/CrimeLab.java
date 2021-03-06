package edu.ivytech.officecrimesfall2020;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import edu.ivytech.officecrimesfall2020.Database.CrimeBaseHelper;
import edu.ivytech.officecrimesfall2020.Database.CrimeCursorWrapper;

import static edu.ivytech.officecrimesfall2020.Database.CrimeDbSchema.*;

public class CrimeLab {
    private static CrimeLab sCrimeLab;
    private Context mContext;
    private SQLiteDatabase mDatabase;


    public static CrimeLab get(Context context) {
        if(sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }
    private CrimeLab(Context context) {
        mContext = context;
        mDatabase = new CrimeBaseHelper(mContext).getWritableDatabase();


    }

    public Crime getCrime(UUID id) {
        CrimeCursorWrapper cursor = queryCrimes(CrimeTable.Cols.UUID + " = ?", new String[]{id.toString()});

        try {
            if(cursor.getCount() == 0) {
                Crime crime = new Crime(id);
                add(crime);
                return crime;
            }
            cursor.moveToFirst();
            return cursor.getCrime();
        } finally {
            cursor.close();
        }


    }

    public List<Crime> getCrimes() {
        List<Crime> crimes = new ArrayList<Crime>();
        CrimeCursorWrapper cursor = queryCrimes(null, null);

        try {
           cursor.moveToFirst();
           while (!cursor.isAfterLast()) {
               crimes.add(cursor.getCrime());
               cursor.moveToNext();
           }
        } finally {
            cursor.close();
        }
        return crimes;
    }

    public void add(Crime crime) {
        ContentValues values = getContentValues(crime);
        mDatabase.insert(CrimeTable.NAME, null, values);

    }
    public void updateCrime(Crime crime) {
        String uuidString = crime.getId().toString();
        ContentValues values = getContentValues(crime);
        mDatabase.update(CrimeTable.NAME, values, CrimeTable.Cols.UUID + " = ?", new String[]{uuidString});
    }


    private static ContentValues getContentValues(Crime crime) {
        ContentValues values = new ContentValues();
        values.put(CrimeTable.Cols.UUID, crime.getId().toString());
        values.put(CrimeTable.Cols.TITLE, crime.getTitle());
        values.put(CrimeTable.Cols.DATE, crime.getDate().getTime());
        values.put(CrimeTable.Cols.SOLVED, crime.isSolved() ? 1 : 0);
        values.put(CrimeTable.Cols.SUSPECT,crime.getSuspect());
        return values;
    }

    private CrimeCursorWrapper queryCrimes(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(CrimeTable.NAME, null, whereClause, whereArgs, null, null, null);
        return new CrimeCursorWrapper(cursor);
    }
    public File getPhotoFile(Crime crime) {
        File filesDir = mContext.getFilesDir();
        return new File(filesDir, crime.getPhotoFilename());
    }

}
