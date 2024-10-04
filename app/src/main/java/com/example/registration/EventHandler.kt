package com.example.registration

interface EventHandler<T> {
    fun obtainEvent(event : T)
}