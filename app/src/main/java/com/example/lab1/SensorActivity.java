package com.example.lab1;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SensorActivity extends AppCompatActivity implements SensorEventListener, LocationListener {

    private SensorManager sensorManager;
    private List<Sensor> sensorList;
    private ListView sensorListView;
    private HashMap<String, float[]> sensorsValues;
    private SensorListAdapter sensorListAdapter;
    private LocationManager locationManager;
    private TextView geoLocationView;


    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

        this.sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorList = this.sensorManager.getSensorList(Sensor.TYPE_ALL);

        this.sensorsValues = (HashMap<String, float[]>) this.generateSensorsMapValues();

        this.sensorListView = (ListView) findViewById(R.id.list_view_sensors);
        this.sensorListAdapter = new SensorListAdapter(getApplicationContext(), this.sensorList, this.sensorsValues);
        this.sensorListView.setAdapter(this.sensorListAdapter);

        Log.i("Sensor", "SENSOR LOG");
        Log.i("Sensor", this.sensorList.toString());

        this.initLocationManager();
    }

    private Map generateSensorsMapValues() {
        HashMap result = new HashMap<>();
        for (Sensor sensor : this.sensorList) {
            result.put(sensor.getName(), null);
        }

        return result;
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.registerSensors();
    }

    @Override
    protected void onStop() {
        super.onStop();
        this.unregisterSensors();
    }

    @Override
    public final void onSensorChanged(SensorEvent event) {
        Sensor currentSensor = event.sensor;
        String sensorName = currentSensor.getName();
        this.sensorsValues.put(sensorName, event.values);
        this.sensorListAdapter.notifyDataSetChanged();;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (this.isGeolocationPermissionGranted()) {
            this.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        }
    }

    public boolean isGeolocationPermissionGranted() {
        return (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED);
    }

    @Override
    public void onLocationChanged(Location location) {
        geoLocationView = (TextView) findViewById(R.id.geolocation);
        geoLocationView.setText("Latitude: " + location.getLatitude() + ", Longitude: " + location.getLongitude() + ", Altitude: " + location.getAltitude());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude", "Status");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Latitude", "Enable");
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("Latitude", "Disable");
    }

    private void registerSensors() {
        if (this.sensorList != null) {
            for (Sensor sensor : sensorList) {
                this.sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
            }
        }
    }

    private void unregisterSensors() {
        this.sensorManager.unregisterListener(this);
    }

    @SuppressLint("MissingPermission")
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initLocationManager() {
        this.geoLocationView = (TextView) findViewById(R.id.geolocation);

        this.locationManager = (LocationManager) getSystemService(getApplicationContext().LOCATION_SERVICE);
        if (!this.isGeolocationPermissionGranted()) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET}, 10);
            return;
        }
        this.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
    }
}