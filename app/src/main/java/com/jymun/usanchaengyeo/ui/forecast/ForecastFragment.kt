package com.jymun.usanchaengyeo.ui.forecast

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.jymun.usanchaengyeo.data.model.address.Address
import com.jymun.usanchaengyeo.data.model.forecast.Forecast
import com.jymun.usanchaengyeo.data.model.forecast.ForecastInfo
import com.jymun.usanchaengyeo.databinding.FragmentForecastBinding
import com.jymun.usanchaengyeo.ui.base.BaseFragment
import com.jymun.usanchaengyeo.ui.base.adapter.ModelRecyclerAdapter
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

    override fun getViewDataBinding() = FragmentForecastBinding.inflate(layoutInflater)

    override fun setUpBinding() = binding.apply {
        viewModel = this@ForecastFragment.viewModel
        lifecycleOwner = viewLifecycleOwner
    }

    override fun observeState() {}

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

    fun submitSelectedAddress(address: Address?) {
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
}