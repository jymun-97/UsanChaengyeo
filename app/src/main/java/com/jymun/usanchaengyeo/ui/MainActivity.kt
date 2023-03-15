package com.jymun.usanchaengyeo.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.jymun.usanchaengyeo.databinding.ActivityMainBinding
import com.jymun.usanchaengyeo.ui.base.BaseActivity
import com.jymun.usanchaengyeo.util.resources.ResourcesProvider
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    @Inject
    lateinit var resourcesProvider: ResourcesProvider
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override val viewModel: MainViewModel by viewModels()

    override fun getViewDataBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun getWeatherViewInstance() = binding.weatherView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initFusedLocationProviderClient()
        initPermissionLauncher()

        permissionLauncher.launch(REQUIRED_PERMISSIONS)
    }

    private fun initFusedLocationProviderClient() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
    }

    private fun initPermissionLauncher() {
        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            checkPermissions()
        }
    }

    private fun checkPermissions() = when (PackageManager.PERMISSION_GRANTED) {
        ActivityCompat.checkSelfPermission(this, FINE_LOCATION_PERMISSION) -> {
            updateCurrentLocation()
        }
        ActivityCompat.checkSelfPermission(this, COURSE_LOCATION_PERMISSION) -> {
            updateCurrentLocation()
            // todo. show snack bar
        }
        else -> {
            // todo. show dialog
        }
    }

    @SuppressLint("MissingPermission")
    private fun updateCurrentLocation() {
        fusedLocationProviderClient.getCurrentLocation(
            Priority.PRIORITY_HIGH_ACCURACY,
            null
        ).addOnSuccessListener { location ->
            Log.d("# MainActivity", "$location")
        }.addOnFailureListener {
            Log.d("# MainActivity", "$it")
        }
    }

    companion object {
        private const val FINE_LOCATION_PERMISSION = Manifest.permission.ACCESS_FINE_LOCATION
        private const val COURSE_LOCATION_PERMISSION = Manifest.permission.ACCESS_COARSE_LOCATION

        private val REQUIRED_PERMISSIONS = arrayOf(
            FINE_LOCATION_PERMISSION,
            COURSE_LOCATION_PERMISSION
        )
    }
}