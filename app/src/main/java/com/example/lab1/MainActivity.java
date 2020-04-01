package com.example.lab1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.hardware.Sensor;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.content.Intent;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private static final String TEXT_VIEW_KEY = "hello";
    String lala = "-----------LIFECICLE----------\t";
    TextView textView;
    int orientation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        int currentOrientation = getResources().getConfiguration().orientation;
        if (savedInstanceState != null) {
            orientation = savedInstanceState.getInt("orientation");
            if (orientation != currentOrientation) {
                orientation = currentOrientation;
            }
            Log.i("Test", "Saved Instance loaded, orientation: " + orientation);
        } else {
            orientation = currentOrientation;
            Log.i("Test", "No saved Instance loaded, orientation: " + orientation);
        }
        Log.i(lala, "onCreate " + orientation);
        TextView theTextView = (TextView) findViewById(R.id.textView1);
        TextView theTextViewLandscape = (TextView) findViewById(R.id.textView2);
        theTextView.setText(orientation == 1 ? "portret" : "landscape");
        theTextViewLandscape.setText(orientation == 1 ? "portret" : "landscape");    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
//        textView.setText(savedInstanceState.getString(TEXT_VIEW_KEY));
    }

    // invoked when the activity may be temporarily destroyed, save the instance state here
    @Override
    public void onSaveInstanceState(Bundle outState) {
//        outState.putString(TEXT_VIEW_KEY, "Hello new Text");

        // call superclass to save any view hierarchy
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(lala, "onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(lala, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(lala, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(lala, "onStop");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.burger_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.login_menu:
                showLogin();
                return true;
            case R.id.about_menu:
                showContact();
                return true;
            case R.id.main_menu_settings:
                showSettings();
                return true;
            case R.id.main_menu_save:
                saveInternalStorage();
                return true;
            case R.id.main_menu_sensors:
                showSensors();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected void showLogin() {
        AlertDialog.Builder login = new AlertDialog.Builder(this);
        final View loginAlertLayout = getLayoutInflater().inflate(R.layout.login_alert, null);
        login.setTitle("Log in")
            .setView(loginAlertLayout)
            .setPositiveButton("Log in", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Log.i(lala, "-------LOGIN");
                }
            })
            .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Log.i(lala, "-------CANCEL LOGIN");
                }
            });

        // create and show the alert dialog
        AlertDialog dialog = login.create();
        dialog.show();
    }

    protected void showContact() {
        Intent intent = new Intent(this, ContactMenu.class);
        startActivity(intent);
    }

    protected void showSettings() {
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }

    protected void saveInternalStorage() {
        String filename = "app_save";
        String fileContents = "Lab 5";
        try {
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), filename);
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(fileContents.getBytes());
            fos.flush();
            fos.close();
        } catch (IOException e) {

        }
    }
    protected void showSensors() {
        Intent intent = new Intent(this, SensorActivity.class);
        startActivity(intent);
    }
}
