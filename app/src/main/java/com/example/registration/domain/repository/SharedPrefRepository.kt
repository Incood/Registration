package com.example.registration.domain.repository

interface SharedPrefRepository {

    fun saveToken(isAuthorized: Boolean)
    fun isUserAuthorized(): Boolean
    fun saveCurrentUserId(userId: Long)
    fun getCurrentUserId(): Long
}