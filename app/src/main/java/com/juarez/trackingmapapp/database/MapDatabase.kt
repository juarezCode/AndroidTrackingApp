package com.juarez.trackingmapapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.juarez.trackingmapapp.model.MapLocation

@Database(entities = [MapLocation::class], version = 1)
abstract class MapDatabase : RoomDatabase() {
    abstract val mapDao: MapDao
}

private lateinit var INSTANCE: MapDatabase

fun getDatabase(context: Context): MapDatabase {
    synchronized(MapDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                MapDatabase::class.java,
                "maps_database").build()
        }
    }
    return INSTANCE
}