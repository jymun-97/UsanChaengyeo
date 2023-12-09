package com.jymun.usanchaengyeo.ui.forecast

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.view.MenuProvider
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.jymun.usanchaengyeo.R
import com.jymun.usanchaengyeo.data.model.address.Address
import com.jymun.usanchaengyeo.data.model.forecast.Forecast
import com.jymun.usanchaengyeo.data.model.forecast.ForecastInfo
import com.jymun.usanchaengyeo.databinding.FragmentForecastBinding
import com.jymun.usanchaengyeo.ui.base.BaseFragment
import com.jymun.usanchaengyeo.ui.base.LoadState
import com.jymun.usanchaengyeo.ui.base.adapter.ModelRecyclerAdapter
import com.jymun.usanchaengyeo.util.exception.CustomExceptions
import com.jymun.usanchaengyeo.util.forecast.ForecastInfoHelper
import com.jymun.usanchaengyeo.util.resources.ResourcesProvider
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ForecastFragment : BaseFragment<ForecastViewModel, FragmentForecastBinding>() {

    @Inject
    lateinit var resourcesProvider: ResourcesProvider

    @Inject
    lateinit var forecastInfoHelper: ForecastInfoHelper

    override val viewModel: ForecastViewModel by activityViewModels()

    private var flag = false

    override fun getViewDataBinding() = FragmentForecastBinding.inflate(layoutInflater)

    override fun setUpBinding() = binding.apply {
        viewModel = this@ForecastFragment.viewModel
        lifecycleOwner = viewLifecycleOwner
    }

    override fun observeState() = viewModel.loadState.observe(viewLifecycleOwner) {
        if (it is LoadState.Error) {
            if (!flag) {
                Log.d("# ForecastFragment", "try again")
                flag = true
                viewModel.runForecast()

                return@observe
            }
            val errorText = it.exception.getMessage(resourcesProvider)
            binding.commentTextView.text = errorText
            Toast.makeText(
                requireActivity(),
                errorText,
                Toast.LENGTH_LONG
            ).show()
        } else if (it is LoadState.Success) {
            flag = false
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initForecastRecyclerView()
        initForecastInfoRecyclerView()

        viewModel.forecastResult.observe(viewLifecycleOwner) { forecastList ->
            binding.commentTextView.text = forecastList?.let {
                forecastInfoHelper.map(it).comment
            } ?: ""
        }
    }

    fun submitSelectedAddress(address: Address) {
        viewModel.runForecast(address)
    }

    private fun initForecastRecyclerView() = binding.forecastRecyclerView.apply {
        adapter = ModelRecyclerAdapter<Forecast>(resourcesProvider)
    }

    private fun initForecastInfoRecyclerView() = binding.forecastInfoRecyclerView.apply {
        layoutManager = LinearLayoutManager(requireActivity())
        adapter = ModelRecyclerAdapter<ForecastInfo>(resourcesProvider).apply {
            submitList(forecastInfoHelper.get())
        }
    }

    override val menuProvider = object : MenuProvider {
        override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
            menuInflater.inflate(R.menu.toolbar_menu, menu)
        }

        override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
            return when (menuItem.itemId) {
                R.id.current_location -> {
                    onCurrentLocationRequiredListener?.onCurrentLocationRequired()
                    true
                }

                R.id.refresh -> {
                    viewModel.runForecast() {
                        onCurrentLocationRequiredListener?.onCurrentLocationRequired()
                    }
                    true
                }

                else -> false
            }
        }
    }
}