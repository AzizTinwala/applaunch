package io.applaunch.applaunchmini.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.applaunch.applaunchmini.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar!!.hide()
    }
}