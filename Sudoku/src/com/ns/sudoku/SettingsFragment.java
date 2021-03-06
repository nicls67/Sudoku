package com.ns.sudoku;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;

public class SettingsFragment extends PreferenceFragment implements OnSharedPreferenceChangeListener {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
		updatePreference("color_on");
	}
	
	@Override
	public void onPause() {
        super.onPause();
        // Unregister the listener whenever a key changes
        getPreferenceScreen().getSharedPreferences()
            .unregisterOnSharedPreferenceChangeListener(this);
    }
	
	@Override
	public void onResume() {
	      super.onResume();

	      // Set up a listener whenever a key changes
	      getPreferenceScreen().getSharedPreferences()
	         .registerOnSharedPreferenceChangeListener(this);        
	  }
	
	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        updatePreference(key);
    }
	
	private void updatePreference(String key){
		if(key.equals("color_on")){
        	Preference preference = findPreference("color_on");
        	if (preference instanceof CheckBoxPreference){
        		CheckBoxPreference cbpref = (CheckBoxPreference)preference;
        		boolean isChk = cbpref.isChecked();
        		Preference prefSwitch = findPreference("color_mode");
        		Preference prefColorBase = findPreference("color_base");
        		if(isChk){
        			prefSwitch.setEnabled(true);
        			prefColorBase.setEnabled(false);
        		}
        		else{
        			prefSwitch.setEnabled(false);
        			prefColorBase.setEnabled(true);
        		}
        	}
        }  
     
    }

}

