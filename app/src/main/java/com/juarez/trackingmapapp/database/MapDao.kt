package com.juarez.trackingmapapp.database

import androidx.room.*
import com.juarez.trackingmapapp.model.MapLocation
import kotlinx.coroutines.flow.Flow

@Dao
interface MapDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(location: MapLocation)

    @Delete
    suspend fun delete(location: MapLocation)

    @Query("SELECT * FROM locations")
    fun getAllLocations(): Flow<List<MapLocation>>
}