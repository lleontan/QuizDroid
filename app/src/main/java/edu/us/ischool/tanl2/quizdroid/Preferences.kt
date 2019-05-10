package edu.us.ischool.tanl2.quizdroid

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
class Preferences: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.preferences_layout)
        super.onCreate(savedInstanceState)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings_container, PreferenceFragment())
            .commit()
    }
}