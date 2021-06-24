package com.juarez.trackingmapapp.repository

import com.juarez.trackingmapapp.database.MapDatabase
import com.juarez.trackingmapapp.model.MapLocation

class MapsRepository(private val database: MapDatabase) {
    suspend fun insert(location: MapLocation) = database.mapDao.insert(location)

    suspend fun delete(location: MapLocation) = database.mapDao.delete(location)
}