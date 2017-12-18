package com.example.m2k.weather

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

import java.util.ArrayList
import java.util.Calendar

/**
 * Created by m2k on 12/7/17.
 */

internal class CustomAdapter(context: Context, list: ArrayList<Day>) : ArrayAdapter<Day>(context, 0, list) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var lv = convertView
        if (lv == null)
            lv = LayoutInflater.from(context).inflate(R.layout.each, parent, false)
        val d = getItem(position)
        val day = lv!!.findViewById<TextView>(R.id.day)
        val temp = lv.findViewById<TextView>(R.id.temp)
        val rain = lv.findViewById<TextView>(R.id.rain)
        temp.text = "Min " + d!!.minTemp + "°C / Max " + d.maxTemp + "°C"
        rain.text = d.rain.toString() + "%"
        day.text = getday(d.time)
        return lv
    }

    private val day = arrayOf("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")
    private fun getday(time: Int): String {
        val cal = Calendar.getInstance()
        cal.timeInMillis = (time * 1000).toLong()
        return day[(cal.get(Calendar.DAY_OF_WEEK) + 1) % 7]
    }
}
