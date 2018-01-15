package com.example.m2k.weather

import android.app.AlertDialog
import android.app.LoaderManager
import android.content.Context
import android.content.Intent
import android.content.Loader
import android.net.ConnectivityManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.location.FusedLocationProviderClient

import org.json.JSONException
import org.json.JSONObject

class Weather : AppCompatActivity(), LoaderManager.LoaderCallbacks<JSONObject> {

    private var context: Context? = null
    private var mFusedLocationClient:FusedLocationProviderClient?=null
    public var dlati : Double?=28.7041
    public var dlongi : Double?  =77.1025

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
        i.putExtra("longi",dlongi)
        i.putExtra("lati",dlati)
        startActivity(i)
    }

    override fun onCreateLoader(i: Int, bundle: Bundle): Loader<JSONObject> {
        Log.i("TEST", "onCreateLoader called")
        return Parser(this@Weather,dlati,dlongi)
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
            al.setMessage("This is a weather forcasting application, which gives us forcast on future days. We can set the place by giving its longitude and lattitude.")
            al.setPositiveButton("OK") { dialogInterface, i ->
                // Do nothing
            }
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
                val summary = findViewById<TextView>(R.id.tv2)
                val temp = findViewById<TextView>(R.id.tv3)
                val rain = findViewById<TextView>(R.id.tv4)
                val i = findViewById<ImageView>(R.id.iv)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode==1){
            val longi = data?.extras?.getDouble("longi")
            val lati = data?.extras?.getDouble("lati")
            dlongi = longi
            dlati = lati
        }
    }

    override fun onLoaderReset(loader: Loader<JSONObject>) {
        Log.i("TEST", "Reseting UI")
        val summary = findViewById<TextView>(R.id.tv2)
        val temp = findViewById<TextView>(R.id.tv3)
        val rain = findViewById<TextView>(R.id.tv4)
        summary.text = ""
        rain.text = ""
        temp.text = ""
    }

    companion object {

        private val LINK = "https://api.darksky.net/forecast/4b4" + "dcd631a0b271e6d9623d8be27caeb/28.7041,77.1025"
    }
}
