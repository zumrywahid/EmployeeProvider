package com.appsgit.employeeprovider.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created on 11/4/17.
 */

public class EmployeeDataBaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "employee.db";
    public static final int DATABASE_VERSION = 1;


    public EmployeeDataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {
            EmployeeContract.onCreate(sqLiteDatabase);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }



    //implement logics if you want to upgrade the current daatabse.
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

}
