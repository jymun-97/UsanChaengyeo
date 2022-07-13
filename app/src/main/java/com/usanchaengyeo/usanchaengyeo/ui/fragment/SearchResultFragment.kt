package com.usanchaengyeo.usanchaengyeo.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.usanchaengyeo.usanchaengyeo.R
import com.usanchaengyeo.usanchaengyeo.databinding.FragmentSearchResultBinding
import com.usanchaengyeo.usanchaengyeo.ui.adapter.AddressAdapter
import com.usanchaengyeo.usanchaengyeo.ui.viewmodel.AddressViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchResultFragment : Fragment() {

    private lateinit var binding: FragmentSearchResultBinding
    private lateinit var addressAdapter: AddressAdapter
    private val addressViewModel by activityViewModels<AddressViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSearchResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            viewmodel = addressViewModel
            lifecycleOwner = viewLifecycleOwner
        }

        initRecyclerView()
        addressViewModel.addressList.observe(viewLifecycleOwner) {
            addressAdapter.submitList(it)
        }
    }

    private fun initRecyclerView() {
        addressAdapter = AddressAdapter(
            itemClicked = {
                addressViewModel.apply {
                    selectedAddress.postValue(it)
                    addHistory(it)
                }
                findNavController().navigate(SearchAddressFragmentDirections.actionSearchAddressFragmentToHomeFragment())
            }
        )

        binding.searchResultRecyclerView.apply {
            adapter = addressAdapter

            val decoration = DividerItemDecoration(requireActivity(), 1)
            requireActivity().getDrawable(R.drawable.recyclerview_divider)
                ?.let { decoration.setDrawable(it) }

            addItemDecoration(decoration)
        }
    }
}