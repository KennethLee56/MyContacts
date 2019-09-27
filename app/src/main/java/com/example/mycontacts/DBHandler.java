package com.example.mycontacts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "contact.db";

    private static final String TABLE_CONTACT_LIST = "contactlist";
    private static final String COLUMN_LIST_ID = "_id";
    private static final String COLUMN_LIST_NAME = "name";
    private static final String COLUMN_LIST_EMAIL = "email";
    private static final String COLUMN_LIST_PHONE = "phone";

    public DBHandler (Context context, SQLiteDatabase.CursorFactory cursorFactory){
        super(context, DATABASE_NAME, cursorFactory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase){
        String query = "CREATE TABLE " + TABLE_CONTACT_LIST + "(" +
                COLUMN_LIST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_LIST_NAME + " TEXT, " +
                COLUMN_LIST_EMAIL + " TEXT, " +
                COLUMN_LIST_PHONE + " TEXT" +
                ");";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACT_LIST);
        onCreate(sqLiteDatabase);
    }

    public void addContactList(String name, String email, String phone){

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMN_LIST_NAME, name);
        values.put(COLUMN_LIST_EMAIL, email);
        values.put(COLUMN_LIST_PHONE, phone);

        db.insert(TABLE_CONTACT_LIST, null, values);
        db.close();
    }

    public Cursor getContactLists(){

        //get writeable reference to shopper database
        SQLiteDatabase db = getWritableDatabase();

        //select all data from shoppinglist table and return it as a cursor
        return db.rawQuery("SELECT * FROM " + TABLE_CONTACT_LIST, null);
    }

    public String getContactListsName(int id){

        //get writeable reference to shopper database
        SQLiteDatabase db = getWritableDatabase();

        String dbString = "";

        //create SQL String that will get the shopping list name
        String query = "SELECT * FROM " + TABLE_CONTACT_LIST +
                " WHERE " + COLUMN_LIST_ID + " = " + id;

        //execute the select statement and store result in a cursor
        Cursor cursor = db.rawQuery(query, null);

        //move the first row in the cursor
        cursor.moveToFirst();

        //check to make sure there's a shopping list name value
        if(cursor.getString(cursor.getColumnIndex("name")) != null){
            //store it in the String that will be returned by the method
            dbString = cursor.getString((cursor.getColumnIndex("name")));
        }
        return dbString;
    }

}
