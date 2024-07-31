package com.abg.cryptogram.data

import android.content.Context
import android.content.SharedPreferences

class SaveConfig(private val context: Context) {
    private lateinit var pref: SharedPreferences

    private val namePref = "cryptogram_my"

    fun saveLevel(i: Int) {
        pref = context.getSharedPreferences(namePref, Context.MODE_PRIVATE)
        val ed: SharedPreferences.Editor = pref.edit()
        ed.putInt("level", i)
        ed.apply()
    }

    fun getLevel(): Int {
        pref = context.getSharedPreferences(namePref, Context.MODE_PRIVATE)
        return pref.getInt("level", 0)
    }

}