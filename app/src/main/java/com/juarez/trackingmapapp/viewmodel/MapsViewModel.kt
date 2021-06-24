package com.juarez.trackingmapapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juarez.trackingmapapp.model.MapLocation
import com.juarez.trackingmapapp.repository.MapsRepository
import kotlinx.coroutines.launch

class MapsViewModel(private val repository: MapsRepository) : ViewModel() {

    fun saveLocation(location: MapLocation) = viewModelScope.launch {
        repository.insert(location)
    }

    fun deleteLocation(location: MapLocation) = viewModelScope.launch {
        repository.delete(location)
    }
}