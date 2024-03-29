package com.company.khomasi.data.data_source.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AppEntity(
    val title: String,
    @PrimaryKey val id: Int? = null
)