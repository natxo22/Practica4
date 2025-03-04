package com.natxo.practica4.sharedpreferences

import android.content.Context

class Prefs(val context: Context) {

    val SHARED_NAME = "db"
    val SHARED_USERNAME = "username"
    val SHARED_PASSWORD = "password"
    val SHARED_LASTCLICKED_TITLE = "last_title"

    val storage = context.getSharedPreferences(SHARED_NAME,0)

    fun setName(name: String) {
        storage.edit().putString(SHARED_USERNAME, name).apply()
    }

    fun getName(): String {
        return storage.getString(SHARED_USERNAME, "")!!
    }

    fun setPassword(password: String) {
        storage.edit().putString(SHARED_PASSWORD, password).apply()
    }

    fun getPassword(): String {
        return storage.getString(SHARED_PASSWORD, "")!!
    }

    fun setLastClickedTitle(title: String) {
        storage.edit().putString(SHARED_LASTCLICKED_TITLE, title).apply()
    }

    fun getLastClickedTitle(): String {
        return storage.getString(SHARED_LASTCLICKED_TITLE, "")!!
    }

    fun clearAll() {
        storage.edit().clear().apply()
    }

}