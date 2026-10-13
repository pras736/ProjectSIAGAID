package com.example.project_prasetio

import android.content.Context
import android.content.SharedPreferences

/**
 * Class Pengelola SharedPreferences untuk menyimpan sesi Login pengguna
 */
class SharedPrefManager(context: Context) {

    private val pref: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREF_NAME = "SIAGA_ID_PREF"
        private const val KEY_IS_LOGIN = "isLogin"
        private const val KEY_USERNAME = "username"
    }

    // Status apakah pengguna sudah login
    var isLogin: Boolean
        get() = pref.getBoolean(KEY_IS_LOGIN, false)
        set(value) = pref.edit().putBoolean(KEY_IS_LOGIN, value).apply()

    // Username pengguna yang tersimpan
    var username: String
        get() = pref.getString(KEY_USERNAME, "Ahmad") ?: "Ahmad"
        set(value) = pref.edit().putString(KEY_USERNAME, value).apply()

    // Menyimpan sesi login
    fun saveLoginSession(user: String) {
        pref.edit().apply {
            putBoolean(KEY_IS_LOGIN, true)
            putString(KEY_USERNAME, user)
            apply()
        }
    }

    // Hapus sesi login (Logout)
    fun clearSession() {
        pref.edit().clear().apply()
    }
}