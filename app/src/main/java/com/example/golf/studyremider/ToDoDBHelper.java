package com.example.golf.studyremider;

/**
 * Created by Golf on 24/2/2558.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ToDoDBHelper extends SQLiteOpenHelper {

    // Database file name
    public static final String name = "todo.sqlite5";
    // Database version
    public static final int version = 1;

    public ToDoDBHelper(Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // This is called when the database is still NOT available
        // The primary key of the table must be "_id".
        String sql = "CREATE TABLE todo2 (" +
                "_id integer primary key autoincrement, " +
                "title text not null," +
                "cname text not null);";
        db.execSQL(sql);

        String sql2 = "CREATE TABLE todo3 (" +
                "_id integer primary key autoincrement, " +
                "cid integer not null," +
                "title text not null," +
                "date text not null," +
                "time text not null," +
                "text text not null);";
        db.execSQL(sql2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This is called when the version defined in this class is
        // different from the version in the database file
        String sql = "DROP TABLE IF EXISTS todo2;";
        db.execSQL(sql);
        this.onCreate(db);
    }
}
