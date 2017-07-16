package com.example.m2k.weather

/**
 * Created by m2k on 10/7/17.
 */

class Day(private val mTime: Int?, rain: Double?, mintemp: Double?, maxtemp: Double?) {
    private val mRain: Double?
    private val mMinTemp: Double?
    private val mMaxTemp: Double?

    init {
        mRain = rain!! * 100
        mMinTemp = 5.0 / 9.0 * (mintemp!! - 32)
        mMaxTemp = 5.0 / 9.0 * (maxtemp!! - 32)
    }

    val time: Int
        get() = mTime!!

    val minTemp: Int
        get() = mMinTemp!!.toInt()

    val rain: Int
        get() = mRain!!.toInt()

    val maxTemp: Int
        get() = mMaxTemp!!.toInt()
}
