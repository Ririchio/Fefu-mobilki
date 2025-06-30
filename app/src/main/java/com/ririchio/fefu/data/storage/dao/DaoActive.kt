package com.ririchio.fefu.data.storage.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.ririchio.fefu.data.storage.models.Active
import com.ririchio.fefu.data.storage.models.User
import com.ririchio.fefu.utils.Const
import kotlinx.coroutines.flow.Flow

@Dao
interface DaoActive {
    @Query("SELECT * FROM ${Const.ACTIVE}")
    fun getAll(): Flow<List<Active>>

    @Upsert
    suspend fun insert(active: Active)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun update(active: Active)

    @Delete
    suspend fun delete(active: Active)
}