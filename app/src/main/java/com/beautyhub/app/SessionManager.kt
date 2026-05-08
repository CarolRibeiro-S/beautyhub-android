package com.beautyhub.app

import android.content.Context
import android.content.SharedPreferences

object SessionManager {
    private const val PREF_NAME = "beautyhub_prefs"
    private const val KEY_TOKEN = "jwt_token"
    private const val KEY_EMAIL = "user_email"
    private const val KEY_NAME = "user_name"

    private lateinit var prefs: SharedPreferences

    fun init(context: Context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun saveToken(token: String) {
        prefs.edit().putString(KEY_TOKEN, token).apply()
    }

    fun getToken(): String? {
        return prefs.getString(KEY_TOKEN, null)
    }

    fun saveEmail(email: String) {
        prefs.edit().putString(KEY_EMAIL, email).apply()
    }

    fun getEmail(): String? {
        return prefs.getString(KEY_EMAIL, null)
    }

    fun saveName(name: String) {
        prefs.edit().putString(KEY_NAME, name).apply()
    }

    fun getName(): String? {
        return prefs.getString(KEY_NAME, null)
    }

    fun isLoggedIn(): Boolean {
        return getToken() != null
    }

    fun logout() {
        prefs.edit().clear().apply()
    }
}