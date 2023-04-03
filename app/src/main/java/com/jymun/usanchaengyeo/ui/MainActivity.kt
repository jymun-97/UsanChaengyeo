package com.jymun.usanchaengyeo.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Rect
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
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
import com.jymun.usanchaengyeo.ui.base.LoadState
import com.jymun.usanchaengyeo.ui.forecast.ForecastFragment
import com.jymun.usanchaengyeo.ui.history.OnHistorySelectedListener
import com.jymun.usanchaengyeo.ui.search_address.SearchAddressViewModel
import com.jymun.usanchaengyeo.ui.setting.SettingActivity
import com.jymun.usanchaengyeo.util.exception.CustomExceptions
import com.jymun.usanchaengyeo.util.resources.ResourcesProvider
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity<SearchAddressViewModel, ActivityMainBinding>(),
    OnHistorySelectedListener, OnCurrentLocationRequiredListener {

    @Inject
    lateinit var resourcesProvider: ResourcesProvider

    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var navHost: NavHostFragment
    private lateinit var navController: NavController
    private lateinit var forecastFragment: ForecastFragment
    private var doubleBackToExitPressedOnce = false

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {

        override fun handleOnBackPressed() {
            when {
                navHost.childFragmentManager.backStackEntryCount > 0 -> {
                    val selectedAddress = viewModel.selectedAddress.value
                    submitAddress(
                        selectedAddress,
                        if (selectedAddress == null) resourcesProvider.getString(R.string.loading_address) else null
                    )
                    navController.popBackStack()
                }
                !doubleBackToExitPressedOnce -> {
                    doubleBackToExitPressedOnce = true
                    Toast.makeText(
                        this@MainActivity,
                        resourcesProvider.getString(R.string.back_pressed),
                        Toast.LENGTH_SHORT
                    ).show()

                    Handler(Looper.getMainLooper()).postDelayed({
                        doubleBackToExitPressedOnce = false
                    }, 2000)
                }
                else -> finish()
            }
        }
    }

    override val viewModel: SearchAddressViewModel by viewModels()

    override fun getViewDataBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun getWeatherViewInstance() = binding.weatherView

    override fun setUpBinding() = binding.apply {
        viewModel = this@MainActivity.viewModel
        lifecycleOwner = this@MainActivity
    }

    override fun observeState() = viewModel.loadState.observe(this) {
        if (it is LoadState.Error) {
            Log.d("# MainActivity", "${it.exception}")
            submitAddress(
                address = null,
                stateText = it.exception.getMessage(resourcesProvider)
            )
        }
    }

    override fun observeWeatherData() = viewModel.weatherData.observe(this) {
        val weatherData = it ?: return@observe
        bindWeatherView(weatherData)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)

        initNavigation()
        initAddressView()
        initToolbar()
        initFusedLocationProviderClient()
        initPermissionLauncher()
        launchPermissionLauncher()

        viewModel.selectedAddress.observe(this) {
            submitAddress(
                address = it,
                stateText = it?.let { null }
                    ?: resourcesProvider.getString(R.string.loading_address)
            )
        }
        viewModel.searchKeyword.observe(this) { searchKeyword ->
            if (binding.addressView.hasFocused) {
                replaceFragment(searchKeyword)
            }
        }
    }

    private fun initNavigation() {
        navHost =
            supportFragmentManager.findFragmentById(binding.fragmentContainerView.id) as NavHostFragment

        navController = navHost.navController
        forecastFragment = navHost.childFragmentManager.fragments[0] as ForecastFragment
    }

    private fun initAddressView() = binding.addressView.setOnClickListener {
        val searchKeyword = viewModel.searchKeyword.value
        replaceFragment(searchKeyword)
    }

    private fun submitAddress(
        address: Address?,
        stateText: String?
    ) {
        address?.let { forecastFragment.submitSelectedAddress(it) }
        binding.addressView.submitAddress(
            newAddress = address,
            stateText = stateText
        )
    }

    private fun initToolbar() {
        setSupportActionBar(binding.toolbar)
        binding.toolbar.setNavigationOnClickListener {
            startActivity(Intent(this, SettingActivity::class.java))
            overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out)
        }
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
            viewModel.exceptionCaused(CustomExceptions.NotGrantedLocationPermissions)
        }
    }

    @SuppressLint("MissingPermission")
    private fun updateCurrentLocation() {
        fusedLocationProviderClient.getCurrentLocation(
            Priority.PRIORITY_HIGH_ACCURACY,
            null
        ).addOnSuccessListener { location ->
            submitAddress(null, resourcesProvider.getString(R.string.loading_address))
            viewModel.coordinateToAddress(location.longitude, location.latitude)
        }.addOnFailureListener {
            viewModel.exceptionCaused(CustomExceptions.FailToLoadCurrentLocationException)
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

    override fun onCurrentLocationRequired() {
        updateCurrentLocation()
    }

    override fun loadWeatherData() {
        viewModel.loadWeatherData()
    }
}