package com.jymun.usanchaengyeo.ui.search_address

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.jymun.usanchaengyeo.data.model.address.Address
import com.jymun.usanchaengyeo.databinding.FragmentSearchAddressBinding
import com.jymun.usanchaengyeo.ui.MainViewModel
import com.jymun.usanchaengyeo.ui.base.BaseFragment
import com.jymun.usanchaengyeo.ui.base.adapter.ModelRecyclerAdapter
import com.jymun.usanchaengyeo.ui.search_address.address.AddressAdapterListener
import com.jymun.usanchaengyeo.util.resources.ResourcesProvider
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchAddressFragment : BaseFragment<MainViewModel, FragmentSearchAddressBinding>() {

    @Inject
    lateinit var resourcesProvider: ResourcesProvider

    override val viewModel: MainViewModel by activityViewModels()

    override fun getViewDataBinding() = FragmentSearchAddressBinding.inflate(layoutInflater)

    override fun setUpBinding() = binding.apply {
        viewModel = this@SearchAddressFragment.viewModel
        lifecycleOwner = viewLifecycleOwner
    }

    override fun observeState() {}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAddressRecyclerView()
    }

    private fun initAddressRecyclerView() = binding.addressRecyclerView.apply {
        layoutManager = LinearLayoutManager(requireActivity())
        adapter = ModelRecyclerAdapter<Address>(resourcesProvider).apply {
            addAdapterListener(object : AddressAdapterListener {
                override fun onAddressItemClicked(address: Address) {
                    viewModel.updateSelectedAddress(address)
                    moveToForecastFragment()
                    // todo. add history
                }
            })
        }
    }

    private fun moveToForecastFragment() = findNavController().navigate(
        SearchAddressFragmentDirections.actionFragmentSearchAddressToFragmentForecast()
    )
}