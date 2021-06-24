package com.juarez.trackingmapapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.juarez.trackingmapapp.repository.MapsRepository

class MapsViewModelFactory(
    private val mapsRepository: MapsRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MapsViewModel(mapsRepository) as T
    }
}