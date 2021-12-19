package com.protsolo.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.protsolo.R

class MainActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    lateinit var authStateListener: FirebaseAuth.AuthStateListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = FirebaseAuth.getInstance()
        authStateListener = FirebaseAuth.AuthStateListener {
            val currentUser = it.currentUser
            if (currentUser != null) {
                //todo
            } else {
                //todo
            }
        }
    }

    override fun onStart() {
        super.onStart()
        auth.addAuthStateListener(authStateListener)
    }

    override fun onStop() {
        super.onStop()
        auth.removeAuthStateListener(authStateListener)
    }
}