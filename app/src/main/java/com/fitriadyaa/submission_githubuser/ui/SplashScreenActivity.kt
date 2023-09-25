package com.fitriadyaa.submission_githubuser.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.fitriadyaa.submission_githubuser.R
import com.fitriadyaa.submission_githubuser.ui.main.MainActivity

class SplashScreenActivity : AppCompatActivity() {
    private val splashDelay = 5000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
        }, splashDelay)
    }

    override fun onPause() {
        super.onPause()
        finish()
    }
}