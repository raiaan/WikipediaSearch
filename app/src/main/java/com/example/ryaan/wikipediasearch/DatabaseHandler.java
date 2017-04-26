package com.example.ryaan.wikipediasearch;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ryaan on 25/04/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "contactsManager";

    // Contacts table name
    private static final String TableName = "Result";

    // Contacts Table Columns names
    private static final String title = "title";
    private static final String result = "result";


    public DatabaseHandler(Context context) {
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TableName + "("
                + title + " TEXT PRIMARY KEY," + result + " TEXT )"
                 ;
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    /**
     * Called when the database needs to be upgraded. The implementation
     * should use this method to drop tables, add tables, or do anything else it
     * needs to upgrade to the new schema version.
     * <p>
     * <p>
     * The SQLite ALTER TABLE documentation can be found
     * <a href="http://sqlite.org/lang_altertable.html">here</a>. If you add new columns
     * you can use ALTER TABLE to insert them into a live table. If you rename or remove columns
     * you can use ALTER TABLE to rename the old table, then create the new table and then
     * populate the new table with the contents of the old table.
     * </p><p>
     * This method executes within a transaction.  If an exception is thrown, all changes
     * will automatically be rolled back.
     * </p>
     *
     * @param db         The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TableName);

        // Create tables again
        onCreate(db);
    }
    public void addData(String title,String result) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(this.title,title);
        values.put(this.result,result);
        // Inserting Row
        db.insert(TableName, null, values);
        db.close(); // Closing database connection
    }
    // Getting single contact
    public String getData(String title) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor= db.query(TableName,new String[]{this.title,this.result},this.title +"=?",
                new String[] { String.valueOf(title) },null,null,null);
        if (cursor != null)
            cursor.moveToFirst();
        return cursor.getString(1);
    }
    public List<String> getAllData() {
        List<String> data = new ArrayList<String>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " +TableName;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                data.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        // return contact list
        return data;
    }

}
