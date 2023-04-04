package com.jymun.usanchaengyeo.ui.search_address

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.view.MenuProvider
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.jymun.usanchaengyeo.R
import com.jymun.usanchaengyeo.data.model.address.Address
import com.jymun.usanchaengyeo.databinding.FragmentSearchAddressBinding
import com.jymun.usanchaengyeo.ui.base.BaseFragment
import com.jymun.usanchaengyeo.ui.base.LoadState
import com.jymun.usanchaengyeo.ui.base.adapter.ModelRecyclerAdapter
import com.jymun.usanchaengyeo.ui.search_address.address.AddressAdapterListener
import com.jymun.usanchaengyeo.util.resources.ResourcesProvider
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchAddressFragment : BaseFragment<SearchAddressViewModel, FragmentSearchAddressBinding>() {

    @Inject
    lateinit var resourcesProvider: ResourcesProvider

    override val viewModel: SearchAddressViewModel by activityViewModels()

    override fun getViewDataBinding() = FragmentSearchAddressBinding.inflate(layoutInflater)

    override fun setUpBinding() = binding.apply {
        viewModel = this@SearchAddressFragment.viewModel
        lifecycleOwner = viewLifecycleOwner
    }

    override fun observeState() = viewModel.loadState.observe(viewLifecycleOwner) {
        if (it is LoadState.Error) {
            Toast.makeText(
                requireActivity(),
                it.exception.getMessage(resourcesProvider),
                Toast.LENGTH_LONG
            ).show()
        }
    }

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
                    viewModel.addHistory(address)

                    moveToForecastFragment()
                }
            })
        }
    }

    private fun moveToForecastFragment() = findNavController().navigate(
        SearchAddressFragmentDirections.actionFragmentSearchAddressToFragmentForecast()
    )

    override val menuProvider = object : MenuProvider {
        override fun onPrepareMenu(menu: Menu) {
            menu.findItem(R.id.refresh).isVisible = false
        }

        override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
            menuInflater.inflate(R.menu.toolbar_menu, menu)
        }

        override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
            if (menuItem.itemId == R.id.current_location) {
                onCurrentLocationRequiredListener?.onCurrentLocationRequired()
                moveToForecastFragment()

                return true
            }
            return false
        }
    }
}