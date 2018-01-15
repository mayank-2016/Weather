package com.example.m2k.weather

import android.content.AsyncTaskLoader
import android.content.Context
import android.util.Log

import org.json.JSONException
import org.json.JSONObject

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

/**
 * Created by m2k on 10/7/17.
 */

class Parser(context: Context, longi: Double?, lati: Double?) : AsyncTaskLoader<JSONObject>(context) {
    private var mlink: String? = null

    init {
        mlink = "https://api.darksky.net/forecast/4b4" + "dcd631a0b271e6d9623d8be27caeb/"+lati.toString()+","+longi.toString()
    }

    override fun loadInBackground(): JSONObject? {
        val url: URL
        var connection: HttpURLConnection
        var json: JSONObject? = null
        try {
            Log.i("TEST","dfjdshfjdsf")
            url = URL(mlink)
            Log.i("TEST", "connection established")
            connection = url.openConnection() as HttpURLConnection
            connection.connect()
            connection.requestMethod = "GET"
            if (connection.responseCode == 200) {
                Log.i("TEST", "started reading json")
                json = readFromLink(connection)
            }
        } catch (e: MalformedURLException) {
            Log.e("URL", "broken link")
        } catch (e: IOException) {
            Log.e("IO", "exception in Load in background")
        }

        return json
    }

    private fun readFromLink(connection: HttpURLConnection): JSONObject? {
        var json: JSONObject? = null
        val ans = StringBuilder()
        try {
            val io = InputStreamReader(connection.inputStream, "UTF-8")
            val bf = BufferedReader(io)
            var line: String? = bf.readLine()
            while (line != null) {
                ans.append(line)
                line = bf.readLine()
            }
            json = JSONObject(ans.toString())
        } catch (e: IOException) {
            Log.e("IO", "in readFromLink method")
        } catch (e: JSONException) {
            Log.e("JSON", "Broken JSON")
        }

        Log.i("TEST", "Done with JSON")
        return json
    }
}
