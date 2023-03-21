package com.jymun.usanchaengyeo.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.material.snackbar.Snackbar
import com.jymun.usanchaengyeo.R
import com.jymun.usanchaengyeo.databinding.ActivityMainBinding
import com.jymun.usanchaengyeo.domain.address.CoordinateToAddressUseCase
import com.jymun.usanchaengyeo.ui.base.BaseActivity
import com.jymun.usanchaengyeo.util.resources.ResourcesProvider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    @Inject
    lateinit var coordinateToAddressUseCase: CoordinateToAddressUseCase

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
        launchPermissionLauncher()

        viewModel.selectedAddress.observe(this) {
            it ?: return@observe
            Log.d("# MainActivity", "$it")
        }
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
            showRequestFineLocationPermissionSnackBar()
        }
        else -> showRequestPermissionDialog() {
            moveToApplicationDetailsSettings()
        }
    }

    @SuppressLint("MissingPermission")
    private fun updateCurrentLocation() {
        fusedLocationProviderClient.getCurrentLocation(
            Priority.PRIORITY_HIGH_ACCURACY,
            null
        ).addOnSuccessListener { location ->
            CoroutineScope(Dispatchers.IO).launch {
                viewModel.coordinateToAddress(location.longitude, location.latitude)
            }
        }.addOnFailureListener {
            Log.d("# MainActivity", "$it")
        }
    }

    private fun showRequestFineLocationPermissionSnackBar() = Snackbar.make(
        binding.root,
        R.string.request_find_location,
        Snackbar.LENGTH_INDEFINITE
    ).setAction(R.string.move) {
        moveToApplicationDetailsSettings()
    }.show()

    private fun moveToApplicationDetailsSettings() {
        val intent = Intent().apply {
            action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            data = Uri.fromParts("package", packageName, null)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(intent)
    }

    private fun showRequestPermissionDialog(
        onPositiveButtonClickListener: () -> Unit
    ) {
        RequestPermissionDialog(onPositiveButtonClickListener).apply {
            isCancelable = true
            show(supportFragmentManager, tag)
        }
    }

    private fun launchPermissionLauncher() = permissionLauncher.launch(REQUIRED_PERMISSIONS)

    companion object {
        private const val FINE_LOCATION_PERMISSION = Manifest.permission.ACCESS_FINE_LOCATION
        private const val COURSE_LOCATION_PERMISSION = Manifest.permission.ACCESS_COARSE_LOCATION

        private val REQUIRED_PERMISSIONS = arrayOf(
            FINE_LOCATION_PERMISSION,
            COURSE_LOCATION_PERMISSION
        )
    }
}