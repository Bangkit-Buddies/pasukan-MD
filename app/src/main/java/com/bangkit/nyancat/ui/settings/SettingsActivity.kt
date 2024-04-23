package com.bangkit.nyancat.ui.settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.switchmaterial.SwitchMaterial
import com.bangkit.nyancat.ui.settings.SettingsViewModel
import com.bangkit.nyancat.R
import com.bangkit.nyancat.ViewModelFactory
import com.bangkit.nyancat.preferences.SettingPreferences
import com.bangkit.nyancat.preferences.dataStore

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val switchTheme = findViewById<SwitchMaterial>(R.id.switch_theme)

        val pref = SettingPreferences.getInstance(application.dataStore)
        val settingsViewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(
            SettingsViewModel::class.java
        )
        settingsViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.isChecked = false
            }
        }

        switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            settingsViewModel.saveThemeSetting(isChecked)
        }
    }
}