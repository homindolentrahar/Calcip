package com.homindolentrahar.calcip.util

import android.content.Context
import android.content.Intent
import com.homindolentrahar.calcip.main.MainActivity

object Constants {
    val listBlock = listOf(0, 128, 192, 224, 240, 248, 252, 254, 255)

    fun navigateToMainActivity(context: Context) {
        context.startActivity(Intent(context, MainActivity::class.java))
    }
}