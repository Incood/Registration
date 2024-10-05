package com.example.registration.domain.repository

import android.content.Context
import android.content.SharedPreferences
import com.example.registration.domain.repository.SharedPrefRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SharedPrefRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : SharedPrefRepository {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("Registration", Context.MODE_PRIVATE)
    override fun saveToken(isAuthorized: Boolean) {
        val editor = prefs.edit()
        editor.putBoolean("isAuthorized", isAuthorized)
        editor.apply()
    }

    override fun isUserAuthorized(): Boolean {
        return prefs.getBoolean("isAuthorized", false)
    }

    override fun saveCurrentUserId(userId: Long) {
        val editor = prefs.edit()
        editor.putLong("currentUserId", userId)
        editor.apply()
    }

    override fun getCurrentUserId(): Long {
        return prefs.getLong("currentUserId", -1)
    }
}