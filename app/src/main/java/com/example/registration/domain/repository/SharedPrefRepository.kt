package com.example.registration.domain.repository

interface SharedPrefRepository {

    fun saveToken(isAuthorized: Boolean)
    fun isUserAuthorized(): Boolean
}