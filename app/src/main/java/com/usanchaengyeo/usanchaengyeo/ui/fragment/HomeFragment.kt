package com.usanchaengyeo.usanchaengyeo.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.usanchaengyeo.usanchaengyeo.databinding.FragmentHomeBinding
import com.usanchaengyeo.usanchaengyeo.ui.viewmodel.AddressViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val addressViewModel by activityViewModels<AddressViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            viewmodel = addressViewModel
            lifecycleOwner = viewLifecycleOwner
        }

        binding.locationTextView.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSearchAddressFragment())
        }
        binding.checkButton.setOnClickListener {
            val selectedAddress = addressViewModel.selectedAddress.value
            selectedAddress?.let {
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToForecastFragment(it)
                )
            }
        }
    }
}