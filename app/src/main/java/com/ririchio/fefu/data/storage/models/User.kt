package com.ririchio.fefu.data.storage.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ririchio.fefu.utils.Const.Companion.USER
import java.util.UUID

@Entity(tableName = USER)
data class User (
    @PrimaryKey
    @ColumnInfo(name = "id") val id: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "login") val login: String = "",
    @ColumnInfo(name = "name") val name: String = "",
    @ColumnInfo(name = "password") val pass: String = ""
)