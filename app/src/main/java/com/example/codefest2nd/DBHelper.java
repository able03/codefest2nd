package com.example.codefest2nd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper
{
    private Context context;
    public DBHelper(@Nullable Context context)
    {
        super(context, "customer", null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE customerTbl(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "firstName VARCHAR(50), " +
                "middleName VARCHAR(50), " +
                "lastName VARCHAR(50), " +
                "username VARCHAR(50), " +
                "password VARCHAR(50), " +
                "birthday VARCHAR(50), " +
                "position VARCHAR(50), " +
                "department VARCHAR(50), " +
                "maritalStatus VARCHAR(50), " +
                "payPeriod VARCHAR(50), " +
                "schedule VARCHAR(50))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS customerTbl");
        onCreate(db);
    }

    public boolean addData(String fName, String mName, String lName, String username,
                        String password, String bDay, String position, String dept,
                        String mStatus, String pPeriod, String schedule)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("firstName", fName);
        values.put("middleName", mName);
        values.put("lastName", lName);
        values.put("username", username);
        values.put("password", password);
        values.put("birthday", bDay);
        values.put("position", position);
        values.put("department", dept);
        values.put("maritalStatus", mStatus);
        values.put("payPeriod", pPeriod);
        values.put("schedule", schedule);

        long i = db.insert("customerTbl", null, values);
        if(i != -1)
        {
            return true;
        }
        else
        {
            return false;
        }
    }



    public boolean readData(String username, String password)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM customerTbl WHERE username = ? AND password = ?", new String[]{username, password});
        if(cursor.getCount() == 0)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public boolean updateData(String fname, String mname, String lname, String username, String password)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("password", password);

        String selection = "firstName = ? and middleName = ? and lastName = ?";
        String[] selectionArgs = {fname, mname, lname};

        long i = db.update("customerTbl", values, selection, selectionArgs);
        if(i > 0)
        {
            return true;
        }
        else
        {
            return false;
        }

    }


}
