package com.appsgit.employeeprovider.activities;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.BoolRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.appsgit.employeeprovider.R;
import com.appsgit.employeeprovider.database.EmployeeContract;
import com.appsgit.employeeprovider.provider.EmployeeContentProvider;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    public static final int REQUEST_CODE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void saveData(View view) {
        try {
            TextView nameTextview = (TextView) findViewById(R.id.name);
            TextView addressTextview = (TextView) findViewById(R.id.address);
            TextView ageTextview = (TextView) findViewById(R.id.age);
            TextView emailTextview = (TextView) findViewById(R.id.email);
            TextView phoneTextview = (TextView) findViewById(R.id.phone);

            if (TextUtils.isEmpty(nameTextview.getText())) {
                showMessage("Name cannot be ampty.!", false);
                return;
            }

            ContentValues values = new ContentValues();
            values.put(EmployeeContract.COLUMN_NAME, nameTextview.getText().toString().trim());
            values.put(EmployeeContract.COLUMN_ADDRESS, addressTextview.getText().toString().trim());
            values.put(EmployeeContract.COLUMN_AGE, ageTextview.getText().toString().trim());
            values.put(EmployeeContract.COLUMN_EMAIL, emailTextview.getText().toString().trim());
            values.put(EmployeeContract.COLUMN_PHONE, phoneTextview.getText().toString().trim());

            //ContentResolver will access the Employee Content Provider
            Uri newUri = getContentResolver().insert(EmployeeContract.EMPLOYEE_URI, values);

            String newUserId = newUri.getLastPathSegment();

            showMessage("Successfully saved. New User ID is " + newUserId, true);


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        TextView nameTextview = (TextView) findViewById(R.id.name);
        TextView addressTextview = (TextView) findViewById(R.id.address);
        TextView ageTextview = (TextView) findViewById(R.id.age);
        TextView emailTextview = (TextView) findViewById(R.id.email);
        TextView phoneTextview = (TextView) findViewById(R.id.phone);

        nameTextview.setText("");
        addressTextview.setText("");
        ageTextview.setText("");
        emailTextview.setText("");
        phoneTextview.setText("");
    }

    private void showMessage(String message, final Boolean successfullySaved) {
        try {
            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
            alertDialog.setTitle("Message");
            alertDialog.setMessage(message);
            alertDialog.setCancelable(false);
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                            if (successfullySaved) {
                                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                                startActivityForResult(intent, REQUEST_CODE);
                            }
                        }
                    });
            alertDialog.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
