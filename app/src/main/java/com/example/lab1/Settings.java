package com.example.lab1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getFragmentManager()
            .beginTransaction()
            .add(R.id.settings_container, new SettingsFragment())
            .commit();
    }
}
