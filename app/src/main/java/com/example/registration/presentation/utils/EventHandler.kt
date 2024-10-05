package com.example.registration.presentation.utils

interface EventHandler<T> {
    fun obtainEvent(event : T)
}