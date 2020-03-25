package com.example.lab1;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;

public class SettingsFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
        initNotificationPreference();
    }

    protected void initNotificationPreference() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SwitchPreference notificationSwitch = (SwitchPreference) findPreference("notificationsCheckbox");
        notificationSwitch.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference currentPreference, Object switchValue) {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                sharedPreferences.edit().putBoolean("notificationsCheckbox", (boolean) switchValue).commit();
                return true;
            }
        });
        notificationSwitch.setChecked(sharedPreferences.getBoolean("notificationsCheckbox", false));
    }

}