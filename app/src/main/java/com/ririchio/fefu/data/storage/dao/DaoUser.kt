package com.ririchio.fefu.data.storage.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.ririchio.fefu.data.storage.models.User
import com.ririchio.fefu.utils.Const
import kotlinx.coroutines.flow.Flow

@Dao
interface DaoUser {
    @Query("SELECT * FROM ${Const.USER}")
    fun getAll(): Flow<List<User>>

    @Query("SELECT * FROM ${Const.USER} WHERE id = :id")
    fun getUser(id: String): Flow<List<User>>

    @Query("SELECT * FROM ${Const.USER} WHERE login = :login AND password = :pass")
    fun login(login: String, pass: String): Flow<User>

    @Upsert
    suspend fun insert(user: User)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun update(user: User)

    @Delete
    suspend fun delete(user: User)
}