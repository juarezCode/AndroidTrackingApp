package com.juarez.trackingmapapp.database

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule(private var application: Application) {
    private lateinit var libraryDatabase: MapDatabase

    @Singleton
    @Provides
    fun providesRoomDatabase(): MapDatabase {
        libraryDatabase =
            Room.databaseBuilder(application, MapDatabase::class.java, "maps_database")
                .fallbackToDestructiveMigration()
                .build()
        return libraryDatabase
    }

    @Singleton
    @Provides
    fun providesMapDao(database: MapDatabase) = libraryDatabase.mapDao
//
//    @Singleton
//    @Provides
//    fun providesBookDAO(database: MapDatabase) = libraryDatabase.getBookDAO()

}