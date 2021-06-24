package com.juarez.trackingmapapp.di

import com.juarez.trackingmapapp.ui.LocationsActivity
import dagger.Component

@Component
interface AppComponent {
    fun inject(activity: LocationsActivity)
}