package com.homindolentrahar.calcip.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.homindolentrahar.calcip.R
import com.homindolentrahar.calcip.util.Constants

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
//        Loading
        Handler().postDelayed({
            Constants.navigateToMainActivity(this)
        }, 1000L)
    }
}
