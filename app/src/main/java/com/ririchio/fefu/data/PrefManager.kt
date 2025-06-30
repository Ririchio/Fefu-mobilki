package com.ririchio.fefu.data

import android.content.Context
import android.content.SharedPreferences
import com.ririchio.fefu.utils.Const

class PrefManager(context: Context) {

    private var sharedPref: SharedPreferences = context.getSharedPreferences(Const.LOG_TAG, Context.MODE_PRIVATE)

    fun isKeyExist(id: String): Boolean {
        return sharedPref.all?.containsKey(id) ?: false
    }

    fun removeKey(id: String) {
        with (sharedPref.edit()) {
            remove(id)
            apply()
        }
    }

    fun setString(id: String, data: String) {
        with (sharedPref.edit()) {
            putString(id, data)
            apply()
        }
    }

    fun getString(id: String): String? {
        return sharedPref.getString(id, null)
    }

    fun setInt(id: String, data: Int) {
        with (sharedPref.edit()) {
            putInt(id, data)
            apply()
        }
    }

    fun getInt(id: String): Int {
        return sharedPref.getInt(id, -1)
    }

    fun setBoolean(id: String, data: Boolean) {
        with (sharedPref.edit()) {
            putBoolean(id, data)
            apply()
        }
    }

    fun getBoolean(id: String): Boolean {
        return sharedPref.getBoolean(id, false)
    }

    fun clear() {
        with (sharedPref.edit()) {
            clear()
            apply()
        }
    }
}