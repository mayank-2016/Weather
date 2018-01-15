package com.example.m2k.weather

import android.app.LoaderManager
import android.content.Loader
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListView

import org.json.JSONException
import org.json.JSONObject

import java.util.ArrayList

class Forcast : AppCompatActivity(), LoaderManager.LoaderCallbacks<JSONObject> {

    public var dlati : Double?=28.7041
    public var dlongi : Double?  =77.1025

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.loading)
        dlati = intent?.extras?.getDouble("lati")
        dlongi = intent?.extras?.getDouble("longi")
        title = "Forecast"
        loaderManager.initLoader(2, Bundle(), this@Forcast).forceLoad()
    }

    override fun onCreateLoader(i: Int, bundle: Bundle): Loader<JSONObject> {
        return Parser(this@Forcast,dlongi,dlati)
    }

    override fun onLoadFinished(loader: Loader<JSONObject>, json: JSONObject?) {
        if (json == null) {
            setContentView(R.layout.no_internet)
        } else {
            setContentView(R.layout.activity_forcast)
            val lv = findViewById<ListView>(R.id.lv)
            val list = ArrayList<Day>()
            try {
                val data = json.getJSONObject("daily").getJSONArray("data")
                for (i in 0..data.length() - 1) {
                    val item = data.get(i) as JSONObject
                    list.add(Day(item.getInt("time"),
                            item.getDouble("precipProbability"),
                            item.getDouble("temperatureMin"),
                            item.getDouble("temperatureMax")))
                }
                val fast = CustomAdapter(this@Forcast, list)
                lv.adapter = fast
            } catch (e: JSONException) {
                Log.e("JSON", "error in Forcast")
            }

        }
    }

    override fun onLoaderReset(loader: Loader<JSONObject>) {

    }

    companion object {
        private val LINK = "https://api.darksky.net/forecast/4b4" + "dcd631a0b271e6d9623d8be27caeb/28.7041,77.1025"
    }
}
