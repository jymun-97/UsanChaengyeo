package com.jymun.usanchaengyeo.ui.history

import com.jymun.usanchaengyeo.R
import com.jymun.usanchaengyeo.data.model.history.History
import com.jymun.usanchaengyeo.databinding.ItemAddressBinding
import com.jymun.usanchaengyeo.ui.base.adapter.AdapterListener
import com.jymun.usanchaengyeo.ui.base.viewholder.ModelViewHolder
import com.jymun.usanchaengyeo.util.resources.ResourcesProvider

class HistoryViewHolder(
    private val binding: ItemAddressBinding,
    resourcesProvider: ResourcesProvider
) : ModelViewHolder<History>(binding, resourcesProvider) {

    override fun bindData(model: History, adapterListener: AdapterListener?) {
        binding.address = model.address
        binding.imageView.setImageDrawable(
            if (model.isPinned) resourcesProvider.getDrawable(R.drawable.ic_pin)
            else resourcesProvider.getDrawable(R.drawable.ic_history)
        )
    }
}