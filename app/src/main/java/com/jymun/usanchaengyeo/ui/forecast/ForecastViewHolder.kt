package com.jymun.usanchaengyeo.ui.forecast

import com.jymun.usanchaengyeo.data.model.forecast.Forecast
import com.jymun.usanchaengyeo.databinding.ItemForecastBinding
import com.jymun.usanchaengyeo.ui.base.adapter.AdapterListener
import com.jymun.usanchaengyeo.ui.base.viewholder.ModelViewHolder
import com.jymun.usanchaengyeo.util.resources.ResourcesProvider

class ForecastViewHolder(
    private val binding: ItemForecastBinding,
    resourcesProvider: ResourcesProvider,
) : ModelViewHolder<Forecast>(binding, resourcesProvider) {

    override fun bindData(model: Forecast, adapterListener: AdapterListener?) {
        binding.forecast = model
    }
}