package com.jymun.usanchaengyeo.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Rect
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.material.snackbar.Snackbar
import com.jymun.usanchaengyeo.R
import com.jymun.usanchaengyeo.data.model.address.Address
import com.jymun.usanchaengyeo.data.model.history.History
import com.jymun.usanchaengyeo.databinding.ActivityMainBinding
import com.jymun.usanchaengyeo.ui.base.BaseActivity
import com.jymun.usanchaengyeo.ui.forecast.ForecastFragment
import com.jymun.usanchaengyeo.ui.history.OnHistorySelectedListener
import com.jymun.usanchaengyeo.ui.search_address.SearchAddressViewModel
import com.jymun.usanchaengyeo.util.resources.ResourcesProvider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity<SearchAddressViewModel, ActivityMainBinding>(),
    OnHistorySelectedListener {

    @Inject
    lateinit var resourcesProvider: ResourcesProvider

    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var navController: NavController
    private lateinit var forecastFragment: ForecastFragment

    override val viewModel: SearchAddressViewModel by viewModels()

    override fun getViewDataBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun getWeatherViewInstance() = binding.weatherView

    override fun setUpBinding() = binding.apply {
        viewModel = this@MainActivity.viewModel
        lifecycleOwner = this@MainActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initNavigation()
        initAddressView()
        initFusedLocationProviderClient()
        initPermissionLauncher()
        initToolbar()
        launchPermissionLauncher()

        viewModel.selectedAddress.observe(this) {
            submitAddress(it)
        }
        viewModel.searchKeyword.observe(this) { searchKeyword ->
            replaceFragment(searchKeyword)
        }
    }

    private fun initNavigation() {
        val navHost =
            supportFragmentManager.findFragmentById(binding.fragmentContainerView.id) as NavHostFragment

        navController = navHost.navController
        forecastFragment = navHost.childFragmentManager.fragments[0] as ForecastFragment
    }

    private fun initAddressView() = binding.addressView.setOnClickListener {
        val searchKeyword = viewModel.searchKeyword.value
        replaceFragment(searchKeyword)
    }

    private fun submitAddress(address: Address?) {
        binding.addressView.submitAddress(
            newAddress = address,
            stateText = address?.let { null }
                ?: resourcesProvider.getString(R.string.loading_address)
        )
        forecastFragment.submitSelectedAddress(address)
    }

    private fun replaceFragment(searchKeyword: String?) {
        val destination = if (searchKeyword.isNullOrEmpty()) {
            R.id.fragment_history
        } else {
            R.id.fragment_search_address
        }
        navController.navigate(
            destination,
            null,
            NavOptions.Builder()
                .setPopUpTo(navController.graph.startDestinationId, false)
                .build()
        )
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

    private fun checkPermissions(): Any = when (PackageManager.PERMISSION_GRANTED) {
        ActivityCompat.checkSelfPermission(this, FINE_LOCATION_PERMISSION) -> {
            updateCurrentLocation()
        }
        ActivityCompat.checkSelfPermission(this, COURSE_LOCATION_PERMISSION) -> {
            updateCurrentLocation()
            showRequestFineLocationPermissionSnackBar()
        }
        else -> {
            showRequestPermissionDialog() { moveToApplicationDetailsSettings() }
            binding.addressView.submitAddress(
                stateText = resourcesProvider.getString(R.string.location_permission_not_granted)
            )
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

    private fun initToolbar() = binding.toolbar.apply {
        setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.refresh -> submitAddress(viewModel.selectedAddress.value)
            }
            true
        }
    }

    private fun launchPermissionLauncher() = permissionLauncher.launch(REQUIRED_PERMISSIONS)

    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN) {
            val view = currentFocus
            if (view is EditText) {
                val outRect = Rect()
                view.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    view.clearFocus()
                    val imm: InputMethodManager =
                        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0)
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }

    override fun onHistorySelected(history: History) {
        viewModel.updateSelectedAddress(history.address)
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