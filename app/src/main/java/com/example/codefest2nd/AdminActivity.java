package com.example.codefest2nd;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.codefest2nd.databinding.ActivityAdminBinding;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class AdminActivity extends AppCompatActivity
{
    private ActivityAdminBinding binding;
    private Spinner position, department, mStatus, pPeriod, day;
    private int month1, year1, in, out, hours1;
    private String fname1, mname1, lname1, uname1, pass1, bday1, position1,
            department1, mstatus1, pperiod1, day1, timein1, timeout1;
    private DBHelper dbHelper;
    private final static String format = "STI-";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_admin);
        initValues();
        setAllSpinnerData();
        setListeners();
        dbHelper = new DBHelper(this);

        binding.generateBtn.setOnClickListener(generateBtn -> {
            boolean fNameEmpty = binding.registerFirstNameEdtx.getText().toString().trim().isEmpty();
            boolean mNameEmpty = binding.registerMiddleNameEdtx.getText().toString().trim().isEmpty();
            boolean lNameEmpty = binding.registerLastNameEdtx.getText().toString().trim().isEmpty();
            if(!fNameEmpty && !mNameEmpty && !lNameEmpty)
            {
               fname1 = binding.registerFirstNameEdtx.getText().toString().trim();
               mname1 = binding.registerMiddleNameEdtx.getText().toString().trim();
               lname1 = binding.registerLastNameEdtx.getText().toString().trim();
               bday1 = binding.birthday.getText().toString().trim();
               position1 = position.getSelectedItem().toString();
               department1 = department.getSelectedItem().toString();
               mstatus1 = mStatus.getSelectedItem().toString();
               pperiod1 = pPeriod.getSelectedItem().toString();

               day1 = day.getSelectedItem().toString();
               timein1 = binding.registerTimeIn.getText().toString().trim();
               timeout1 = binding.registerTimeOut.getText().toString().trim();
               hours1 = Integer.parseInt(binding.registerHours.getText().toString().trim());

               String result = String.format("%s, %s, %s, %s", day1,timein1,timeout1,hours1);

               if(dbHelper.addData(fname1,mname1,lname1,null, null,bday1,position1,department1,mstatus1,pperiod1, result))
               {
                   Toast.makeText(this, "Add success", Toast.LENGTH_SHORT).show();

                   //generate username and pass in this line
                   generateUsername();
                   generatePassword();

                   uname1 = binding.registerUsernameEdtx.getText().toString().trim();
                   pass1 = binding.registerPasswordEdtx.getText().toString().trim();

                   dbHelper.updateData(fname1,mname1,lname1,uname1,pass1);

               }
               else
               {
                   Toast.makeText(this, "Add failed", Toast.LENGTH_SHORT).show();
               }

            }
            else
            {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void initValues()
    {
        position = findViewById(R.id.position_spinner);
        department = findViewById(R.id.department_spinner);
        mStatus = findViewById(R.id.marital_status_spinner);
        pPeriod = findViewById(R.id.pay_period_spinner);
        day = findViewById(R.id.day_spinner);
    }
    private void setAllSpinnerData()
    {
        ArrayAdapter<CharSequence> positionAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.position_array,
                android.R.layout.simple_spinner_dropdown_item);
        positionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        position.setAdapter(positionAdapter);

        ArrayAdapter<CharSequence> departmentAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.department_array,
                android.R.layout.simple_spinner_dropdown_item
                );
        departmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        department.setAdapter(departmentAdapter);

        ArrayAdapter<CharSequence> maritalStatusAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.marital_status_array,
                android.R.layout.simple_spinner_dropdown_item
        );
        maritalStatusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mStatus.setAdapter(maritalStatusAdapter);

        ArrayAdapter<CharSequence> payPeriodAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.pay_period_array,
                android.R.layout.simple_spinner_dropdown_item
        );
        payPeriodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pPeriod.setAdapter(payPeriodAdapter);

        ArrayAdapter<CharSequence> dayAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.day_adapter,
                android.R.layout.simple_spinner_dropdown_item
        );
        day.setAdapter(dayAdapter);
    }

    private void setListeners()
    {
        binding.birthday.setOnClickListener(showCalendar -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener()
            {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
                {
                    binding.birthday.setText(
                            String.valueOf(String.format("%02d", month+1)+ "/" +
                                    String.format("%02d", dayOfMonth) + "/" +
                                    String.format("%04d", year)));
                    binding.ageTxt.setText(String.valueOf(2024-year));
                    month1 = month;
                    year1 = year;
                }
            }, 2024, 3, 22);
            datePickerDialog.show();
        });


        binding.registerTimeIn.setOnClickListener(timeIn -> {
            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener()
            {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute)
                {
                    binding.registerTimeIn.setText(String.valueOf(String.format("%02d", hourOfDay) + ":" + String.format("%02d", minute)));
                    in = hourOfDay;
                }
            }, 12, 00, false);
            timePickerDialog.show();
        });

        binding.registerTimeOut.setOnClickListener(timeOut -> {
            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener()
            {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute)
                {
                    binding.registerTimeOut.setText(String.valueOf(String.format("%02d", hourOfDay) + ":" + String.format("%02d", minute)));
                    out = hourOfDay;
                }
            }, 12, 00, false);
            timePickerDialog.show();
        });
    }

    private void generateUsername()
    {
        String fName = binding.registerFirstNameEdtx.getText().toString().trim();
        String lName = binding.registerLastNameEdtx.getText().toString().trim();

        StringBuilder sb = new StringBuilder();
        sb.append(fName.substring(0,3));

        sb.append(lName.charAt(lName.length()-3));
        sb.append(lName.charAt(lName.length()-2));
        sb.append(lName.charAt(lName.length()-1));

        Set<Character> vowels = new HashSet<>();
        Collections.addAll(vowels, 'A','E','I','O','U', 'a','e','i','o','u');
        for(int i=0; i<lName.length(); i++)
        {
            if(!vowels.contains(lName.charAt(i)))
            {
                sb.append(lName.charAt(i));
            }
        }

        binding.registerUsernameEdtx.setText(sb.toString());
    }

    private void generatePassword()
    {
        String fname = binding.registerFirstNameEdtx.getText().toString().trim();
        String mname = binding.registerMiddleNameEdtx.getText().toString().trim();
        String lname = binding.registerLastNameEdtx.getText().toString().trim();

        StringBuilder sb = new StringBuilder();
        dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM customerTbl WHERE firstName = ? and middleName = ? and lastName = ?", new String[]{fname, mname, lname});
        try
        {
            if(cursor.moveToFirst())
            {
                int id = cursor.getInt(0);
                int age = Integer.parseInt(binding.ageTxt.getText().toString().trim());

                sb.append(format+id+age+month1+year1);
                binding.registerPasswordEdtx.setText(sb.toString());
            }
            else {
                Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
            }
        }
        finally
        {
            dbHelper.close();
            cursor.close();
            db.close();
        }

    }


}