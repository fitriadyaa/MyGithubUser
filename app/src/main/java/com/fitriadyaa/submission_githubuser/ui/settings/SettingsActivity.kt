package com.fitriadyaa.submission_githubuser.ui.settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.fitriadyaa.submission_githubuser.R
import com.fitriadyaa.submission_githubuser.data.local.datastore.SettingPreferences
import com.fitriadyaa.submission_githubuser.data.local.datastore.dataStore
import com.fitriadyaa.submission_githubuser.viewmodel.SettingViewModel
import com.fitriadyaa.submission_githubuser.viewmodel.ViewModelFactory
import com.google.android.material.switchmaterial.SwitchMaterial

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val switchTheme = findViewById<SwitchMaterial>(R.id.switch_dark_mode)

        val pref = SettingPreferences.getInstance(dataStore)
        val themeViewModelFactory = ViewModelFactory(pref)
        val themeViewModel = ViewModelProvider(this, themeViewModelFactory).get(SettingViewModel::class.java)

        themeViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.isChecked = false
            }
        }

        switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            themeViewModel.saveThemeSetting(isChecked)
        }
    }
}