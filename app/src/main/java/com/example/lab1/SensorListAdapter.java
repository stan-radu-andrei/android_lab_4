package com.example.lab1;

import android.content.Context;
import android.hardware.Sensor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class SensorListAdapter extends ArrayAdapter<Sensor> {

    private Context context;
    private List<Sensor> sensorList;
    private HashMap<String, float[]> sensorValues;

    public SensorListAdapter(Context context,  List<Sensor> sensorList, HashMap<String, float[]> sensorsValues) {
        super(context, 0 , sensorList);
        this.context = context;
        this.sensorList = sensorList;
        this.sensorValues = sensorsValues;
    }

    public View getView(int position, View convertView, ViewGroup container) {
        View listItem = convertView;
        Sensor currentSensor = this.sensorList.get(position);
        String sensorName = currentSensor.getName();
        if(listItem == null) {
            listItem = LayoutInflater.from(this.context).inflate(R.layout.list_item_sensor,container,false);
        }

        // set sensor name
        TextView sensorNameView = (TextView) listItem.findViewById(R.id.sensor_name);
        sensorNameView.setText(sensorName);

        // set sensor value
        TextView sensorValueView = (TextView) listItem.findViewById(R.id.sensor_value);
        sensorValueView.setText(this.getSensorValue(sensorName));

        return listItem;
    }

    private String getSensorValue(String sensorName) {
        StringBuilder result = new StringBuilder("Values: \n");
        if (this.sensorValues != null && this.sensorValues.containsKey(sensorName)) {
            float[] sensorValues = this.sensorValues.get(sensorName);
            if (sensorValues != null) {
                for (float sensorValue : sensorValues) {
                    result.append(sensorValue + "\n");
                }
            }
            Log.i("Sensor Map", "Sensor name: " + sensorName + "\nSensor values are: " + result);
        }
        return result.toString();
    }

}