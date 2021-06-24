package com.juarez.trackingmapapp.viewmodel

import androidx.lifecycle.*
import com.juarez.trackingmapapp.model.MapLocation
import com.juarez.trackingmapapp.repository.LocationsRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class LocationsViewModel(private val repository: LocationsRepository) : ViewModel() {

    val locations: LiveData<List<MapLocation>> = repository.locations.asLiveData()

    fun saveLocation(location: MapLocation) = viewModelScope.launch {
        repository.insert(location)
    }

    fun deleteLocation(location: MapLocation) = viewModelScope.launch {
        repository.delete(location)
    }
}