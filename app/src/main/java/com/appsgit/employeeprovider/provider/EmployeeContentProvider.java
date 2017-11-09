package com.appsgit.employeeprovider.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.appsgit.employeeprovider.database.EmployeeContract;
import com.appsgit.employeeprovider.database.EmployeeDataBaseHelper;

import java.util.Arrays;

/**
 *  Contains ContentProvider class methods.
 */

public class EmployeeContentProvider extends ContentProvider {

    public static final String TAG = EmployeeContentProvider.class.getSimpleName();

    public static final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

    String[] tableColumns = {EmployeeContract.COLUMN_ID, EmployeeContract.COLUMN_NAME, EmployeeContract.COLUMN_AGE, EmployeeContract.COLUMN_EMAIL, EmployeeContract.COLUMN_ADDRESS, EmployeeContract.COLUMN_PHONE};

    /*
     * Defines a handle to the database helper object. The MainDatabaseHelper class is defined
     * in a following snippet.
     */
    private EmployeeDataBaseHelper employeeDataBaseHelper;;

    // Holds the database object
    private SQLiteDatabase db;

    //add the URIs which is going to used by this provider.
    static {
        matcher.addURI(EmployeeContract.AUTHORITY, EmployeeContract.EMPLOYEE_TABLE_NAME, EmployeeContract.EMPLOYEE);
        matcher.addURI(EmployeeContract.AUTHORITY, EmployeeContract.EMPLOYEE_TABLE_NAME + "/#", EmployeeContract.EMPLOYEE_ID);
    }


    @Override
    public boolean onCreate() {
        employeeDataBaseHelper = new EmployeeDataBaseHelper(getContext());
        return true;
    }



    @Nullable
    @Override // query() Must return a Cursor object
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        int matchedUriType = matcher.match(uri);

        if (projection != null &&!Arrays.asList(tableColumns).containsAll(Arrays.asList(projection))) {
            throw new IllegalArgumentException("No Column found in Projection.");
        }

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(EmployeeContract.EMPLOYEE_TABLE_NAME);
        switch (matchedUriType) {
            case EmployeeContract.EMPLOYEE:

                break;

            case EmployeeContract.EMPLOYEE_ID:
                queryBuilder.appendWhere(EmployeeContract.COLUMN_ID + "=" + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        db = employeeDataBaseHelper.getWritableDatabase();

        Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);

        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {

        db = employeeDataBaseHelper.getWritableDatabase();

        int matchedUriType = matcher.match(uri);

        long newId = 0;

        switch (matchedUriType) {
            case EmployeeContract.EMPLOYEE:
                newId = db.insert(EmployeeContract.EMPLOYEE_TABLE_NAME, null, contentValues);
                break;

            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }


// We cn use this as wellUri.parse(EmployeeContract.EMPLOYEE_URI.toString() + "/" + newId);

        return ContentUris.withAppendedId(uri, newId);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        int rowDeleted = 0;

        db = employeeDataBaseHelper.getWritableDatabase();

        int matchedUriType = matcher.match(uri);

        switch (matchedUriType) {

            case EmployeeContract.EMPLOYEE:

                rowDeleted = db.delete(EmployeeContract.EMPLOYEE_TABLE_NAME, selection, selectionArgs);
                break;

            case EmployeeContract.EMPLOYEE_ID:

                String idTobeDeleted = uri.getLastPathSegment();

                if (selection != null && !selection.isEmpty()) {
                    rowDeleted = db.delete(
                            EmployeeContract.EMPLOYEE_TABLE_NAME,
                            EmployeeContract.COLUMN_ID + "=" + idTobeDeleted + " AND " + selection, selectionArgs);
                } else {
                    rowDeleted = db.delete(
                            EmployeeContract.EMPLOYEE_TABLE_NAME, EmployeeContract.COLUMN_ID + "=" + idTobeDeleted, null);
                }

                break;

            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);

        return rowDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String selection, @Nullable String[] selectionArgs) {

        int rowUpdated = 0;

        db = employeeDataBaseHelper.getWritableDatabase();

        int matchedUriType = matcher.match(uri);

        switch (matchedUriType) {
            case EmployeeContract.EMPLOYEE:
                rowUpdated = db.update(EmployeeContract.EMPLOYEE_TABLE_NAME, contentValues, selection, selectionArgs);
                break;

            case EmployeeContract.EMPLOYEE_ID:

                String idTobeUpdated = uri.getLastPathSegment();

                if (selection != null && !selection.isEmpty()) {
                    rowUpdated = db.update(
                            EmployeeContract.EMPLOYEE_TABLE_NAME,
                            contentValues,
                            EmployeeContract.COLUMN_ID + "=" + idTobeUpdated + " and " + selection,
                            selectionArgs);
                } else {
                    rowUpdated = db.update(
                            EmployeeContract.EMPLOYEE_TABLE_NAME,
                            contentValues,
                            EmployeeContract.COLUMN_ID + "=" + idTobeUpdated,
                            null);
                }
                break;

            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return rowUpdated;
    }
}
