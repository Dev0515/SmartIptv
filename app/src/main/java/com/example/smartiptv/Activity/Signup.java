package com.example.smartiptv.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.smartiptv.R;

public class Signup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ActionBar myActionBar = getSupportActionBar();
        myActionBar.hide();
        getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.backk));
    }
}
