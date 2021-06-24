package com.juarez.trackingmapapp.ui

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.juarez.trackingmapapp.R
import com.juarez.trackingmapapp.database.getDatabase
import com.juarez.trackingmapapp.databinding.ActivityMapsBinding
import com.juarez.trackingmapapp.databinding.DialogLocationNameBinding
import com.juarez.trackingmapapp.model.MapLocation
import com.juarez.trackingmapapp.repository.MapsRepository
import com.juarez.trackingmapapp.viewmodel.MapsViewModel
import com.juarez.trackingmapapp.viewmodel.MapsViewModelFactory
import java.util.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private lateinit var viewModel: MapsViewModel
    private lateinit var binding: ActivityMapsBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val TAG = "MapsActivity"
    private var recording = false
    private lateinit var initLocation: Location
    private lateinit var endLocation: Location
    private var id = 100

    companion object {
        const val REQUEST_CODE_LOCATION = 1
    }

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val repository = MapsRepository(getDatabase(application))
        val viewModelFactory = MapsViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MapsViewModel::class.java)

        binding.btnOPenLocations.setOnClickListener {
            startActivity(Intent(this, LocationsActivity::class.java))
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        binding.btnStartTracking.setOnClickListener {
            recording = !recording
            if (recording) {
                binding.btnStartTracking.text = "grabando"
                fusedLocationClient.lastLocation.addOnSuccessListener {
                    initLocation = it
                    Log.d(TAG, "latitude ${it.latitude}")
                    Log.d(TAG, "longitude ${it.longitude}")
                }
            } else {
                binding.btnStartTracking.text = "iniciar"
                fusedLocationClient.lastLocation.addOnSuccessListener {
                    endLocation = it
                    Log.d(TAG, "latitude ${it.latitude}")
                    Log.d(TAG, "longitude ${it.longitude}")
                    showDialog()
                }
            }
        }

        // fix
//        viewModel.saveLocation(MapLocation(3, "12312", "312312"))
//
//        viewModel.deleteLocation(MapLocation(1, "12312", "312312"))


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun showDialog() {
        val builder = AlertDialog.Builder(this)
        val dialogBinding = DialogLocationNameBinding.inflate(LayoutInflater.from(this))
        val btnSave = dialogBinding.btnDialogSave
        val edtName = dialogBinding.edtDialogName
        builder.setView(dialogBinding.root)
        builder.setTitle("Agrega un nombre a la ruta")
        builder.setCancelable(false)
        builder.create()
        val dialog = builder.show()
        btnSave.setOnClickListener {
            dialog.dismiss()
            id += 1
            val location = MapLocation(
                id.toLong(),
                initLocation.latitude.toString(),
                initLocation.longitude.toString(),
                endLocation.longitude.toString(),
                endLocation.longitude.toString(),
                edtName.text.toString()
            )

            Log.d(TAG, "$location")
            viewModel.saveLocation(location)
        }
    }


    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        val zoomLevel = 15f
        // Add a marker in Sydney and move the camera
        val home = LatLng(18.664422389313543, -96.99530750117256)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(home, zoomLevel))
        map.addMarker(MarkerOptions().position(home).title("Marker in Sydney"))


        setMapLongClick(map)
        setPoiClick(map)
        enableMyLocation()
        enableMyLocation()
    }

    private fun setMapLongClick(map: GoogleMap) {
        map.setOnMapLongClickListener { latLng ->
            val snippet = String.format(
                Locale.getDefault(),
                "Lat: %1$.3f, Long: %2$.3f",
                latLng.latitude,
                latLng.longitude
            )
            map.addMarker(
                MarkerOptions()
                    .position(latLng)
                    .title("dropped pin")
                    .snippet(snippet)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
            )
        }
    }

    private fun setPoiClick(map: GoogleMap) {
        map.setOnPoiClickListener { poi ->
            val poiMarker = map.addMarker(
                MarkerOptions()
                    .position(poi.latLng)
                    .title(poi.name)
            )
            poiMarker.showInfoWindow()
        }
    }

    private fun isPermissionGranted() = ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

    @SuppressLint("MissingPermission")
    private fun enableMyLocation() {
        if (!::map.isInitialized) return
        if (isPermissionGranted()) {
            map.isMyLocationEnabled = true
        } else {
            requestLocationPermission()
        }
    }

    private fun requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        ) {
            Toast.makeText(this, "Ve a ajustes y acepta los permisos", Toast.LENGTH_SHORT).show()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_CODE_LOCATION
            )
        }
    }

    @SuppressLint("MissingSuperCall", "MissingPermission")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_CODE_LOCATION -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                map.isMyLocationEnabled = true
            } else {
                Toast.makeText(
                    this,
                    "Para activar la localización ve a ajustes y acepta los permisos",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else -> {
            }
        }
    }

    @SuppressLint("MissingPermission")
    override fun onResumeFragments() {
        super.onResumeFragments()
        if (!::map.isInitialized) return
        if (!isPermissionGranted()) {
            map.isMyLocationEnabled = false
            Toast.makeText(
                this,
                "Para activar la localización ve a ajustes y acepta los permisos",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}