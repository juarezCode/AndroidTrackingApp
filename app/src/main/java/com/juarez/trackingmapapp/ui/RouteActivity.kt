package com.juarez.trackingmapapp.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.juarez.trackingmapapp.R
import com.juarez.trackingmapapp.databinding.ActivityRouteBinding
import com.juarez.trackingmapapp.model.MapLocation

class RouteActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var map: GoogleMap
    private lateinit var binding: ActivityRouteBinding
    private lateinit var locationRoute: MapLocation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRouteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        locationRoute = intent.getSerializableExtra("location") as MapLocation
        Log.d("RouteActivity", locationRoute.toString())

        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.routeMap) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        val zoomLevel = 15f
        // Add a marker in Sydney and move the camera
        val home =
            LatLng(locationRoute.initLatitude.toDouble(), locationRoute.initLongitude.toDouble())
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(home, zoomLevel))
        map.addMarker(MarkerOptions().position(home).title("Marker in Home"))
        map.isMyLocationEnabled = true
        addPolyline()
    }

    private fun addPolyline() {
        val polylineOptions = PolylineOptions()
            .add(
                LatLng(
                    locationRoute.initLatitude.toDouble(),
                    locationRoute.initLongitude.toDouble()
                )
            )
            .add(LatLng(18.666619618130422, -96.99850648721203))
            .width(15f)
            .color(ContextCompat.getColor(this, R.color.purple_500))

        val polyline = map.addPolyline(polylineOptions)

        val end =
            LatLng(18.666619618130422, 18.666619618130422)
        map.addMarker(
            MarkerOptions().position(end).title("fin de la ruta").icon(
                BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
            )
        )

    }
}