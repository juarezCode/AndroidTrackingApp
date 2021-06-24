package com.juarez.trackingmapapp.repository

import com.juarez.trackingmapapp.database.MapDatabase
import com.juarez.trackingmapapp.model.MapLocation
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocationsRepository(private val database: MapDatabase) {

    val locations: Flow<List<MapLocation>> get() = database.mapDao.getAllLocations()

    suspend fun insert(location: MapLocation) = database.mapDao.insert(location)

    suspend fun delete(location: MapLocation) = database.mapDao.delete(location)
}