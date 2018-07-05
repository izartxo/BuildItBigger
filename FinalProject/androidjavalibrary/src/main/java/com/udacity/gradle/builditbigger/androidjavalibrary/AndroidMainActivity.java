package com.udacity.gradle.builditbigger.androidjavalibrary;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class AndroidMainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getIntent().hasExtra("data")){
            Toast.makeText(this, "DATA: " + getIntent().getStringExtra("data"), Toast.LENGTH_LONG).show();
        }

        Toast.makeText(this, "-----------", Toast.LENGTH_LONG).show();
    }





}
