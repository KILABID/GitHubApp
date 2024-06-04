package com.bangkit.submissionAwal.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import com.bangkit.submissionAwal.databinding.ActivitySplashScreenBinding
import com.bangkit.submissionAwal.ui.homePage.MainActivity
import com.bangkit.submissionAwal.ui.settings.SettingsPreferences
import com.bangkit.submissionAwal.ui.settings.dataStore

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    private lateinit var splashBinding: ActivitySplashScreenBinding
    private lateinit var settingsPreferences: SettingsPreferences

    companion object {
        const val DELAY_TIME = 1500L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        splashBinding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(splashBinding.root)


        supportActionBar?.hide()
        val handler = Handler(mainLooper)

        handler.postDelayed({
            val intent = Intent(this@SplashScreenActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, DELAY_TIME)
        settingsPreferences = SettingsPreferences.getInstance(this.dataStore)
        @Suppress("DEPRECATION")
        lifecycleScope.launchWhenStarted {
            settingsPreferences.getThemeSetting().collect { isDarkModeActive ->
                if (isDarkModeActive) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            }
        }
    }
}