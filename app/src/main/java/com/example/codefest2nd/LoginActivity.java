package com.example.codefest2nd;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.codefest2nd.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity
{
    private ActivityLoginBinding binding;
    private DBHelper dbHelper;
    private final static String admin = "admin";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        dbHelper = new DBHelper(this);

        String adminUname = "admin";
        String adminPass = "@dmin";
        if(!dbHelper.readData(adminUname, adminPass))
        {
            dbHelper.addData(null, null, null, "admin", "@dmin", null, null, null, null, null, null);
        }

        binding.loginBtn.setOnClickListener(login -> {
            boolean unameEmpty = binding.loginUsernameEdtx.getText().toString().trim().isEmpty();
            boolean passEmpty = binding.loginPasswordEdtx.getText().toString().trim().isEmpty();

            if(!unameEmpty && !passEmpty)
            {
                String uname = binding.loginUsernameEdtx.getText().toString().trim();
                String pass = binding.loginPasswordEdtx.getText().toString().trim();

                if(uname.equals(admin))
                {
                    if(dbHelper.readData(uname, pass))
                    {
                        Intent intent = new Intent(this, AdminActivity.class);
                        intent.putExtra("username", uname);
                        intent.putExtra("password", pass);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(this, "Account not found", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    if(dbHelper.readData(uname, pass))
                    {
                        Intent intent = new Intent(this, MainActivity.class);
                        intent.putExtra("username", uname);
                        intent.putExtra("password", pass);
                        startActivity(intent);
                    }else{
                        Toast.makeText(this, "Account not found", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            else {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            }
        });

    }
}