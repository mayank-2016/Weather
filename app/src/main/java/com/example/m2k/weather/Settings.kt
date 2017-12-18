package com.example.m2k.weather

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.View
import android.widget.Toast

class Settings : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
    }

    fun done(view: View){
        Toast.makeText(this,"not yet Implemented",Toast.LENGTH_LONG).show()
        TODO("Not yet implemented")
    }
}
