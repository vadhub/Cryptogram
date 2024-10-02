package com.abg.cryptogram.data

import android.content.Context
import android.content.SharedPreferences
import com.abg.cryptogram.model.LocaleChange

class SaveConfig(private val context: Context) {
    private lateinit var pref: SharedPreferences

    private val namePref = "cryptogram_my"

    fun saveLevel(i: Int) {
        var level = i
        if (i >= 111) {
            level = 0
        }
        pref = context.getSharedPreferences(namePref, Context.MODE_PRIVATE)
        val ed: SharedPreferences.Editor = pref.edit()
        ed.putInt("level", level)
        ed.apply()
    }

    fun getLevel(): Int {
        pref = context.getSharedPreferences(namePref, Context.MODE_PRIVATE)
        return pref.getInt("level", 0)
    }

    fun saveIsTutorComplete(isFirst: Boolean) {
        pref = context.getSharedPreferences(namePref, Context.MODE_PRIVATE)
        val ed: SharedPreferences.Editor = pref.edit()
        ed.putBoolean("tutor_complete", isFirst)
        ed.apply()
    }

    fun getIsTutorComplete(): Boolean {
        pref = context.getSharedPreferences(namePref, Context.MODE_PRIVATE)
        return pref.getBoolean("tutor_complete", false)
    }

    fun changeLanguage(lang: String) {
        pref = context.getSharedPreferences(namePref, Context.MODE_PRIVATE)
        val ed: SharedPreferences.Editor = pref.edit()
        ed.putString("lang", lang)
        ed.apply()
    }

    fun getLanguage(): String? {
        pref = context.getSharedPreferences(namePref, Context.MODE_PRIVATE)
        return pref.getString("lang", LocaleChange.getLocale(context))
    }

}