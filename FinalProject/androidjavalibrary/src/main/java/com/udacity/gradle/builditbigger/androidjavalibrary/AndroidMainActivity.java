package com.udacity.gradle.builditbigger.androidjavalibrary;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class AndroidMainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_joke);


     /*   if (getIntent().hasExtra("data")){
            Toast.makeText(this, "DATA: " + getIntent().getStringExtra("data"), Toast.LENGTH_LONG).show();
        }*/


    }





}
