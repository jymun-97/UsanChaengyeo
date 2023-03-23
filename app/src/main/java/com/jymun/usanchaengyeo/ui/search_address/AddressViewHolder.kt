package com.jymun.usanchaengyeo.ui.search_address

import com.jymun.usanchaengyeo.data.model.address.Address
import com.jymun.usanchaengyeo.databinding.ItemAddressBinding
import com.jymun.usanchaengyeo.ui.base.adapter.AdapterListener
import com.jymun.usanchaengyeo.ui.base.viewholder.ModelViewHolder
import com.jymun.usanchaengyeo.util.resources.ResourcesProvider

class AddressViewHolder(
    private val binding: ItemAddressBinding,
    resourcesProvider: ResourcesProvider
) : ModelViewHolder<Address>(binding, resourcesProvider) {

    override fun bindData(model: Address, adapterListener: AdapterListener?) {
        binding.address = model
    }
}