package com.jymun.usanchaengyeo.ui.base.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import com.jymun.usanchaengyeo.data.model.Model
import com.jymun.usanchaengyeo.data.model.ModelType
import com.jymun.usanchaengyeo.databinding.ItemAddressBinding
import com.jymun.usanchaengyeo.databinding.ItemForecastBinding
import com.jymun.usanchaengyeo.databinding.ItemForecastInfoBinding
import com.jymun.usanchaengyeo.ui.forecast.ForecastInfoViewHolder
import com.jymun.usanchaengyeo.ui.forecast.ForecastViewHolder
import com.jymun.usanchaengyeo.ui.history.HistoryViewHolder
import com.jymun.usanchaengyeo.ui.search_address.address.AddressViewHolder
import com.jymun.usanchaengyeo.util.resources.ResourcesProvider

object ModelViewHolderMapper {

    @Suppress("UNCHECKED_CAST")
    fun <M : Model> map(
        parent: ViewGroup,
        type: ModelType,
        resourcesProvider: ResourcesProvider
    ): ModelViewHolder<M> {
        val inflater = LayoutInflater.from(parent.context)
        return when (type) {
            ModelType.ADDRESS -> AddressViewHolder(
                ItemAddressBinding.inflate(inflater, parent, false),
                resourcesProvider
            )
            ModelType.HISTORY -> HistoryViewHolder(
                ItemAddressBinding.inflate(inflater, parent, false),
                resourcesProvider
            )
            ModelType.FORECAST -> ForecastViewHolder(
                ItemForecastBinding.inflate(inflater, parent, false),
                resourcesProvider
            )
            ModelType.FORECAST_INFO -> ForecastInfoViewHolder(
                ItemForecastInfoBinding.inflate(inflater, parent, false),
                resourcesProvider
            )
        } as ModelViewHolder<M>
    }
}