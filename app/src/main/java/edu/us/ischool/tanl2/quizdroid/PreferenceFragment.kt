package edu.us.ischool.tanl2.quizdroid

import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.preference.EditTextPreference
import android.support.v7.preference.Preference
import android.support.v7.preference.PreferenceFragmentCompat
import android.support.v7.preference.SeekBarPreference

class PreferenceFragment: PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences_doc, rootKey)
        val PrefScreen = preferenceScreen
        var dialog=PrefScreen.findPreference("url_dialog")
        var sharedPrefs=preferenceManager.sharedPreferences
        dialog.setTitle(sharedPrefs.getString("url","Set URL"))
        dialog.setOnPreferenceChangeListener { preference, newValue ->
            var sharedPrefEditor:SharedPreferences.Editor=sharedPrefs.edit()
            sharedPrefEditor.putString("url","$newValue")
            dialog.setTitle("$newValue")
            sharedPrefEditor.commit()
        }
        var minutesSlider=PrefScreen.findPreference("minutes_seek_bar") as SeekBarPreference
        minutesSlider.value=sharedPrefs.getInt("minutes",10)
        minutesSlider.setOnPreferenceChangeListener { preference, newValue ->
            var sharedPrefEditor:SharedPreferences.Editor=sharedPrefs.edit()
            sharedPrefEditor.putInt("minutes",newValue as Int)
            sharedPrefEditor.commit()
        }
    }
}