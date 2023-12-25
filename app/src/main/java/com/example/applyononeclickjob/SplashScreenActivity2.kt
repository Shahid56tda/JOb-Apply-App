package com.example.applyononeclickjob

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class SplashScreenActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen2)
        supportActionBar?.hide()
        Handler().postDelayed({
            val preferences = getSharedPreferences("login", MODE_PRIVATE)
            val check = preferences.getBoolean("flag", false)
            val intent: Intent
            if (check) { //for true(if User is Logged in)
                intent = Intent(this@SplashScreenActivity2, DashBoardActivity::class.java)
            } else { //for false(if User is first In or Logged OUt)
                intent = Intent(this@SplashScreenActivity2, SignUpActivity::class.java)
            }
            startActivity(intent)
        }, 3000)
    }
}