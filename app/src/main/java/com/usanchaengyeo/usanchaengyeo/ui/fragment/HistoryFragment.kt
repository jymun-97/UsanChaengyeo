package com.usanchaengyeo.usanchaengyeo.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.usanchaengyeo.usanchaengyeo.R
import com.usanchaengyeo.usanchaengyeo.databinding.FragmentHistoryBinding
import com.usanchaengyeo.usanchaengyeo.ui.adapter.AddressAdapter
import com.usanchaengyeo.usanchaengyeo.ui.viewmodel.AddressViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryFragment : Fragment() {

    private lateinit var binding: FragmentHistoryBinding
    private lateinit var addressAdapter: AddressAdapter
    private val addressViewModel by activityViewModels<AddressViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            viewmodel = addressViewModel
            lifecycleOwner = viewLifecycleOwner
        }

        initRecyclerView()
    }

    private fun initRecyclerView() {
        addressAdapter = AddressAdapter(
            itemClicked = {
                addressViewModel.apply {
                    selectedAddress.postValue(it)
                }
                findNavController().navigate(
                    SearchAddressFragmentDirections.actionSearchAddressFragmentToHomeFragment()
                )
            }
        )
        binding.historyRecyclerView.apply {
            adapter = addressAdapter

            val decoration = DividerItemDecoration(requireActivity(), 1)
            requireActivity().getDrawable(R.drawable.recyclerview_divider)
                ?.let { decoration.setDrawable(it) }

            addItemDecoration(decoration)
        }
        setItemSwipeToLeft()
    }

    private fun setItemSwipeToLeft() {
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            0, ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = true

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val target = addressAdapter.currentList[position]

                target?.let {
                    addressViewModel.deleteHistory(it)
                }
            }
        }
        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(binding.historyRecyclerView)
    }
}