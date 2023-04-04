package com.jymun.usanchaengyeo.ui.search_address.address

import com.jymun.usanchaengyeo.data.model.address.Address
import com.jymun.usanchaengyeo.ui.base.adapter.AdapterListener

interface AddressAdapterListener : AdapterListener {

    fun onAddressItemClicked(address: Address)
}