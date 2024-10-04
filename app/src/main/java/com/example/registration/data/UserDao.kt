package com.example.registration.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM users WHERE name = :name AND password = :password LIMIT 1")
    suspend fun getUserByCredentials(name: String, password: String): User?

    @Query("SELECT * FROM users ORDER BY dateOfBirth ASC")
    suspend fun getAllUsers(): List<User>

    @Query("DELETE FROM users WHERE id = :userId")
    suspend fun deleteUser(userId: Long)
}