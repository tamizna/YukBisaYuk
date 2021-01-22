package com.tamizna.yukbisayuk.utils

import android.content.Context
import com.tamizna.yukbisayuk.CustomApplication

class SharedPreferencesHelper {

    companion object {
        const val SP_NAME = "yukbisayuk-prefs"
        const val USERNAME = "PREF_USERNAME"
    }

    private val prefs = CustomApplication.application.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)

    fun getUsername() : String? = prefs.getString(USERNAME, null)

    fun setUsername(username : String) {
        prefs.edit().putString(USERNAME, username).apply()
    }

    fun removeUsername() {
        prefs.edit().clear().apply()
    }
}