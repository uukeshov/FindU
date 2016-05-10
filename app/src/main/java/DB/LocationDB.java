package DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

class LocationDB extends SQLiteOpenHelper {

    private static final String LOG_TAG = "LocationDB";

    public LocationDB(Context context) {
        super(context, "LocationDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table location (id integer primary key autoincrement,adress text,createdate text, latitude text, longitude text);");
        db.execSQL("create table user (id integer primary key autoincrement,key text,createdate text);");
        db.execSQL("create table permissions (id integer primary key autoincrement,user integer,permissionType integer, status integer, createdate text, moddate text);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("DROP TABLE IF EXIST LocationDB");
    }

}