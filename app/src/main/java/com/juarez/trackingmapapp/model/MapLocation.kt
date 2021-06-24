package com.juarez.trackingmapapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "locations")
data class MapLocation  (
    @PrimaryKey(autoGenerate = true)
    val id: Long = -1,
    val initLatitude: String = "",
    val initLongitude: String = "",
    val endLatitude: String = "",
    val endLongitude: String = "",
    val routeName: String = ""
): Serializable