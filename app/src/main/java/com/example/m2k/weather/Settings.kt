package com.example.m2k.weather

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.OnSuccessListener
import android.widget.TextView
import com.google.android.gms.tasks.Task
import java.lang.Double.parseDouble
import java.time.LocalTime


class Settings : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
    }

    fun done(view: View){
        var lati:Double = 0.0
        var longi:Double = 0.0
        val text1 = findViewById<EditText>(R.id.Longitude)
        val text2 = findViewById<EditText>(R.id.lattitude)
        if(text1.text.length==0||text2.text.length==0){
            Toast.makeText(this,"Lattitude or Longitude is not valid",Toast.LENGTH_SHORT).show()
        }
        else{
            lati = parseDouble(text2.text.toString())
            longi = parseDouble(text1.text.toString())
        }
        if(lati!=0.0&&longi!=0.0){
            val i = Intent()
            i.putExtra("lati",lati)
            i.putExtra("longi",longi)
            setResult(1,i)
            finish()
        }
    }
}
