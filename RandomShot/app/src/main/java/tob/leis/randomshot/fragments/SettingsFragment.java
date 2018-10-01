package tob.leis.randomshot.fragments;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;


import tob.leis.randomshot.R;
import tob.leis.randomshot.helper.PreferenceHelper;


public class SettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings_layout);

        EditTextPreference usernameEditText = (EditTextPreference) findPreference("username_settings");
        usernameEditText.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                PreferenceHelper.setUsernamePreference(getActivity(), newValue.toString());
                preference.setTitle(newValue.toString());
                ((EditTextPreference)preference).setText(newValue.toString());
                return false;
            }
        });

        EditTextPreference ageEditText = (EditTextPreference) findPreference("age_settings");
        ageEditText.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                PreferenceHelper.setAgePreference(getActivity(), newValue.toString());
                preference.setTitle(newValue.toString());
                ((EditTextPreference)preference).setText(newValue.toString());
                return false;
            }
        });

        EditTextPreference heightEditText = (EditTextPreference) findPreference("height_settings");
        heightEditText.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                PreferenceHelper.setHeightPreference(getActivity(), newValue.toString());
                preference.setTitle(newValue.toString());
                ((EditTextPreference)preference).setText(newValue.toString());
                return false;
            }
        });



        //INIT all preferences
        System.out.println(PreferenceHelper.getSamplingRatePreference(getActivity()));
        usernameEditText.setTitle(PreferenceHelper.getUsernamePreference(getActivity()));
        usernameEditText.setText(PreferenceHelper.getUsernamePreference(getActivity()));
        heightEditText.setTitle(PreferenceHelper.getHeightPreference(getActivity()));
        heightEditText.setText(PreferenceHelper.getHeightPreference(getActivity()));
        ageEditText.setTitle(PreferenceHelper.getAgePreference(getActivity()));
        ageEditText.setText(PreferenceHelper.getAgePreference(getActivity()));
    }
}