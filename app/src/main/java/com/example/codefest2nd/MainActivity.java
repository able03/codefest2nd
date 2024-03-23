package com.example.codefest2nd;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import com.example.codefest2nd.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity
{
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        String username = getIntent().getStringExtra("username");
        String password = getIntent().getStringExtra("password");

        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM customerTbl WHERE username = ? and password = ?", new String[]{username, password});
        if(cursor.moveToFirst())
        {
            String fname = cursor.getString(1);
            String mname = cursor.getString(2);
            String lname = cursor.getString(3);
            String uname = cursor.getString(4);
            String pass = cursor.getString(5);
            String bday = cursor.getString(6);
            String position = cursor.getString(7);
            String dep = cursor.getString(8);
            String mstatus = cursor.getString(9);
            String pperiod = cursor.getString(10);
            String schedule = cursor.getString(11);

            binding.firstNameTxt.setText(fname);
            binding.middleNameTxt.setText(mname);
            binding.lastNameTxt.setText(lname);
            binding.usernameTxt.setText(uname);
            binding.passwordTxt.setText(pass);
            binding.birthdayTxt.setText(bday);
            binding.positionTxt.setText(position);
            binding.departmentTxt.setText(dep);
            binding.maritalStatusTxt.setText(mstatus);
            binding.payPeriodTxt.setText(pperiod);
            binding.scheduleTxt.setText(schedule);
        }
    }
}