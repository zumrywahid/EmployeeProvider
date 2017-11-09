package com.appsgit.employeeprovider.activities;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.appsgit.employeeprovider.R;
import com.appsgit.employeeprovider.database.EmployeeContract;

public class DetailActivity extends AppCompatActivity {

    public static final String TAG = DetailActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);

        setResult(RESULT_OK);

        TextView textView = (TextView) findViewById(R.id.textView);


        Cursor cursor = getContentResolver().query(EmployeeContract.EMPLOYEE_URI, null, null, null, null);

        textView.setText("Employee count in Content Provider : " +  cursor.getCount());

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
