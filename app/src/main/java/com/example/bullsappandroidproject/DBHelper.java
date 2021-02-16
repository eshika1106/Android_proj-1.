package com.example.bullsappandroidproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;
//    public static final String DBNAME = "water.db";
    public static final String DBNAME = "lalala.db";
    private static final String TABLE_USER = "bill";
    // User Table Columns names
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_USER_USERNAME = "user_username";
    private static final String COLUMN_USER_EMAIL = "user_email";
    private static final String COLUMN_USER_PASSWORD = "user_password";
    private static final String COLUMN_USER_PHONE = "user_phone";
    private static final String COLUMN_USER_AMOUNT = "user_amount";

    private String CREATE_USER_TABLE = "CREATE TABLE " +
            TABLE_USER + "("
            + COLUMN_USER_ID   + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_USER_NAME + " TEXT,"
            + COLUMN_USER_USERNAME + " TEXT,"
            + COLUMN_USER_EMAIL + " TEXT,"
            + COLUMN_USER_PASSWORD + " TEXT,"
            + COLUMN_USER_PHONE + " TEXT,"
            + COLUMN_USER_AMOUNT + " TEXT"
            + ")";

    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;


    public DBHelper(Context context) {
        super(context, DBNAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(DROP_USER_TABLE);
        // Create tables again
        onCreate(db);
    }

    public Boolean insertData(String un, String pwd, String fn, String eid, String phNo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USER_NAME, fn);
        contentValues.put(COLUMN_USER_USERNAME, un);
        contentValues.put(COLUMN_USER_PASSWORD, pwd);
        contentValues.put(COLUMN_USER_EMAIL, eid);
        contentValues.put(COLUMN_USER_PHONE, phNo);
        contentValues.put(COLUMN_USER_AMOUNT,"0");
        long result = db.insert("bill", null, contentValues);
        if (result == -1) return false;
        else
            return true;
    }

    public Boolean checkusername(String username) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Log.d("OOPS", "Entered checkusername");
        Cursor cursor = MyDB.rawQuery("Select * from bill where COLUMN_USER_USERNAME = ?", new String[]{username});
        Log.d("OOPS", "QUERY PASSED");
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public Boolean checkusernamepassword(String username, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from bill where username = ? and password = ?", new String[] {username,password});
        Log.d("OOPS", "Entered checkusernamepasss");
        Log.d("OOPS", "QUERY PASSED");
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }



    public boolean checkUser(String username) {
        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_USERNAME
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_USER_USERNAME+ " = ?";
        // selection argument
        String[] selectionArgs = {username};
        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }
        return false;
    }


    public boolean checkUser(String username, String password) {
        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_USER_USERNAME + " = ?" + " AND " + COLUMN_USER_PASSWORD + " = ?";
        // selection arguments
        String[] selectionArgs = {username, password};
        // query user table with conditions
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com' AND user_password = 'qwerty';
         */
        Log.d("OOPS", "Entered checkusernamepasss");
        Log.d("OOPS", "Entered checkusernamepasss"+username);
        Log.d("OOPS", "Entered checkusernamepasss"+password);

        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        Log.d("OOPS", "QUERY PASSED");
        Log.d("OOPS", "Entered checkusernamepasss"+cursorCount);
        if (cursorCount > 0) {
            return true;
        }
        return false;
    }


    public User getUser(String curr_username) {
        User user1 = new User();
        // array of columns to fetch
        Log.d("Cursor", "Entered");
        String[] columns = {
                COLUMN_USER_ID,
                COLUMN_USER_EMAIL,
                COLUMN_USER_NAME,
                COLUMN_USER_PASSWORD,
                COLUMN_USER_PHONE,
                COLUMN_USER_USERNAME,
                COLUMN_USER_AMOUNT
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_USER_USERNAME+ " = ?";
        // selection arguments
        String[] selectionArgs = {curr_username};
        // query user table with conditions
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com' AND user_password = 'qwerty';
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        Log.d("Cursor", "After Q");
        int cursorCount = cursor.getPosition();
        if(cursor!=null && cursor.getCount()>0) {
            cursor.moveToFirst();
            user1.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID))));
            user1.setName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)));
            user1.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)));
            user1.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)));
            user1.setPhone(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PHONE)));
            user1.setUsername(cursor.getString(cursor.getColumnIndex(COLUMN_USER_USERNAME)));
            user1.setAmount(cursor.getString(cursor.getColumnIndex(COLUMN_USER_AMOUNT)));
            Log.d("Cursor", "After Copying"+user1.getName());
        }
        cursor.close();
        db.close();
        Log.d("Cursor", "Good to go");
        return user1;

    }

    public void updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());
        values.put(COLUMN_USER_PHONE, user.getPhone());
        values.put(COLUMN_USER_AMOUNT, user.getAmount());
        values.put(COLUMN_USER_USERNAME, user.getUsername());
        // updating row
        db.update(TABLE_USER, values, COLUMN_USER_USERNAME + " = ?",
                new String[]{String.valueOf(user.getUsername())});
        db.close();
    }
}
