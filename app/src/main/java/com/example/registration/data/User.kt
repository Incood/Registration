package com.example.registration.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val name: String,
    val password: String,
    val dateOfBirth: String,
    val avatarUri: String?,
    val registrationDate: Date = Date()
)