package io.applaunch.applaunchmini.view.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import io.applaunch.applaunchmini.R

class SplashScreen : AppCompatActivity() {
    var mLogo: ImageView? = null
    var welcomeText: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        supportActionBar!!.hide()
        mLogo = findViewById(R.id.logo)
        welcomeText = findViewById(R.id.welcome_text)
        startAnimation()
    }

    private fun startAnimation() {
        mLogo!!.alpha = 1.0f
        var anim = AnimationUtils.loadAnimation(this, R.anim.translate_top_to_center)
        mLogo!!.startAnimation(anim)

        welcomeText!!.alpha = 1.0f
        anim = AnimationUtils.loadAnimation(this, R.anim.translate_bottom_to_center)
        welcomeText!!.startAnimation(anim)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(
                Intent(
                    this,
                    MainActivity::class.java
                )
            )

            finish()
        }, 5000)
    }
}