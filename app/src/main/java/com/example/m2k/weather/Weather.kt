package com.example.m2k.weather

import android.app.AlertDialog
import android.app.LoaderManager
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.Loader
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast

import org.json.JSONException
import org.json.JSONObject

class Weather : AppCompatActivity(), LoaderManager.LoaderCallbacks<JSONObject> {
    private var context: Context? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.loading)
        context = this
        Log.i("TEST", "Button clicked")
        val con = this@Weather.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = con.activeNetworkInfo
        if (info != null) {
            Log.i("TEST", "Network OK")
            loaderManager.initLoader(1, Bundle(), this).forceLoad()
        } else {
            Log.i("TEST", "Network not working")
            //pb.visibility = ProgressBar.INVISIBLE
            setContentView(R.layout.no_internet)
            Toast.makeText(this@Weather, "Internet is not working", Toast.LENGTH_SHORT).show()
        }
    }

    fun forcast(view: View) {
        val i = Intent(this@Weather, Forcast::class.java)
        startActivity(i)
    }

    override fun onCreateLoader(i: Int, bundle: Bundle): Loader<JSONObject> {
        Log.i("TEST", "onCreateLoader called")
        return Parser(this@Weather)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.settings, menu)
        return true
    }

    fun getContext():Context{
        return this
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.setting) {
            startActivity(Intent(getContext(),Settings::class.java))
            return true
        } else if (item.itemId == R.id.about) {
            Log.i("TEST", "about working")
            val al = AlertDialog.Builder(context)
            al.setTitle("About")
            al.setMessage("this app is an open source project in github maintained by mayank-2016 if want " + "to contribute can visit the ripo.")
            al.setPositiveButton("OK") { dialogInterface, i ->
                // Do nothing
            }
            al.setNegativeButton("GIST",{dialogInterface, i ->
                val i = Intent(Intent.ACTION_VIEW)
                i.setData(Uri.parse("http://www.google.com"))
                startActivity(i)
            })
            al.show()
            return true
        } else
            return super.onOptionsItemSelected(item)
    }

    override fun onLoadFinished(loader: Loader<JSONObject>, json: JSONObject?) {
        if (json == null) {
            setContentView(R.layout.no_internet)
            Log.i("TEST", "Broken json")
            //val pb = findViewById(R.id.pb) as ProgressBar
            //pb.visibility = ProgressBar.INVISIBLE
            Toast.makeText(this@Weather, "Internet is not working", Toast.LENGTH_SHORT).show()
        } else {
            setContentView(R.layout.activity_weather)
            Log.i("TEST", "in On Load finished")
            //val pb = findViewById(R.id.pb) as ProgressBar
            //pb.visibility = ProgressBar.INVISIBLE
            //Toast.makeText(Weather.this,"Done",Toast.LENGTH_SHORT).show();
            try {
                val summary = findViewById(R.id.tv2) as TextView
                val temp = findViewById(R.id.tv3) as TextView
                val rain = findViewById(R.id.tv4) as TextView
                val i = findViewById(R.id.iv) as ImageView
                val s = json.getJSONObject("currently").getString("summary")
                val a1 = java.lang.Double.parseDouble(json.getJSONObject("currently").getString("precipProbability")) * 100
                var b1: Double? = 5 * (java.lang.Double.parseDouble(json.getJSONObject("currently").getString("temperature")) - 32)
                val icon = json.getJSONObject("currently").getString("icon")
                val ic = Image(icon)
                b1 =b1?.div(9)
                i.setImageResource(ic.image)
                val a = a1.toInt()
                val b = b1!!.toInt()
                summary.text = s
                rain.text = "Rain Chances : " + a.toString() + "%"
                temp.text = "Tempreture : " + b.toString() + "°C"
                Log.i("TEST", "Update UI")
            } catch (e: JSONException) {
                Log.e("JSON", "Dont worry this will never print")
            }

        }
    }

    override fun onLoaderReset(loader: Loader<JSONObject>) {
        Log.i("TEST", "Reseting UI")
        val summary = findViewById(R.id.tv2) as TextView
        val temp = findViewById(R.id.tv3) as TextView
        val rain = findViewById(R.id.tv4) as TextView
        summary.text = ""
        rain.text = ""
        temp.text = ""
    }

    companion object {

        private val LINK = "https://api.darksky.net/forecast/4b4" + "dcd631a0b271e6d9623d8be27caeb/28.7041,77.1025"
    }
}
