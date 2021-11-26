package com.protsolo.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.protsolo.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onDestroy() {
        super.onDestroy()
        isFirstLogin = false
    }

    companion object {
        var isFirstLogin = true
    }
}