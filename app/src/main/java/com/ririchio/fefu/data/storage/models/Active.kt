package com.ririchio.fefu.data.storage.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ririchio.fefu.utils.Const.Companion.ACTIVE
import com.ririchio.fefu.utils.Const.Companion.USER
import java.util.UUID

@Entity(tableName = ACTIVE)
data class Active (
    @PrimaryKey
    @ColumnInfo(name = "id") val id: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "type") val type: String = "",
    @ColumnInfo(name = "date_start") val date_start: Long = 0L,
    @ColumnInfo(name = "date_end") val date_end: Long = 0L,
    @ColumnInfo(name = "distance") val distance: String = "",
)