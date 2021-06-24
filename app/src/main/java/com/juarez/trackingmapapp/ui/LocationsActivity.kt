package com.juarez.trackingmapapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.juarez.trackingmapapp.adapters.LocationsAdapter
import com.juarez.trackingmapapp.database.getDatabase
import com.juarez.trackingmapapp.databinding.ActivityLocationsBinding
import com.juarez.trackingmapapp.model.MapLocation
import com.juarez.trackingmapapp.repository.LocationsRepository
import com.juarez.trackingmapapp.viewmodel.LocationsViewModel
import com.juarez.trackingmapapp.viewmodel.LocationsViewModelFactory
import javax.inject.Inject

class LocationsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLocationsBinding

    private lateinit var viewModel: LocationsViewModel
    private val locationsAdapter = LocationsAdapter(arrayListOf()) { location, action ->
        if (action == "Delete") {
            onDeleteLocation(location)
        } else {
            val intent = Intent(this, RouteActivity::class.java)
            intent.putExtra("location", location)
            startActivity(intent)
        }
    }
    private var index = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLocationsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val repository = LocationsRepository(getDatabase(application))
        val viewModelFactory = LocationsViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(LocationsViewModel::class.java)

        binding.recyclerLocations.apply {
            layoutManager =
                LinearLayoutManager(this@LocationsActivity, LinearLayoutManager.VERTICAL, false)
            adapter = locationsAdapter
        }

        binding.btnAdfDummyLocation.setOnClickListener {
            index += 1
            val dummy =
                MapLocation(index.toLong(), "923691273", "137217", "231", "1231", "Mi casa $index")
            viewModel.saveLocation(dummy)
        }

        viewModel.locations.observe(this, Observer {
            locationsAdapter.update(it)
        })
    }

    private fun onDeleteLocation(location: MapLocation) {
        viewModel.deleteLocation(location)
    }
}