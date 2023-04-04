package com.jymun.usanchaengyeo.ui.forecast

import com.jymun.usanchaengyeo.data.model.forecast.ForecastInfo
import com.jymun.usanchaengyeo.databinding.ItemForecastInfoBinding
import com.jymun.usanchaengyeo.ui.base.adapter.AdapterListener
import com.jymun.usanchaengyeo.ui.base.viewholder.ModelViewHolder
import com.jymun.usanchaengyeo.util.resources.ResourcesProvider

class ForecastInfoViewHolder(
    private val binding: ItemForecastInfoBinding,
    resourcesProvider: ResourcesProvider
) : ModelViewHolder<ForecastInfo>(binding, resourcesProvider) {

    override fun bindData(model: ForecastInfo, adapterListener: AdapterListener?) {
        binding.info = model
    }
}