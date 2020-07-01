package com.example.applybroadcast_service.broadcast;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class database {

    database.DB db;

    public database(Context context) {
        db = new database.DB(context);
    }

    public long dataInsert(String id, int status) {

        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(DB.ID, id);
        contentValues.put(DB.STATUS, status);
        long putData = sqLiteDatabase.insert(DB.tableName, null, contentValues);
        return putData;
    }

    public long statusUpdate(String id, int status) {
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(DB.STATUS, status);
        String[] y = {id};
        long count = sqLiteDatabase.update(DB.tableName, contentValues, DB.ID + " =? ", y);
        return count;
    }

    public String getLastStatus(String id) {

        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        String[] y = {DB.STATUS};
        Cursor cursor = sqLiteDatabase.query(DB.tableName, y,
                DB.ID + " = '" + id + "'",
                null, null, null, null, null);

        StringBuffer log = new StringBuffer();
        while (cursor.moveToNext()) {
            int index = cursor.getColumnIndex(DB.STATUS);
            int log_ = cursor.getInt(index);
            log.append(log_);
        }

        return log.toString();
    }

    public int checkEmpty() {

        SQLiteDatabase sqLiteDatabase = db.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + DB.tableName, null);
        int count = cursor.getCount();
        return count;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* Inner Class For Create Table */
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    static class DB extends SQLiteOpenHelper {
        private Context context;

        private static final String database_Name = "Settings";

        private static final String tableName = "Notify";

        private static final int dataBase_version = 1;

        private static final String ID = "id";
        private static final String STATUS = "status";

        // Create tables
        private static final String Create_Table = "CREATE TABLE " + tableName + " (" + ID
                + " VARCHAR(10), " + STATUS + " INTEGER);";

        public DB(Context context) {
            super(context, database_Name, null, dataBase_version);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            try {
                db.execSQL(Create_Table);

            } catch (Exception e) {
                Toast.makeText(context, "due to " + e, Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
