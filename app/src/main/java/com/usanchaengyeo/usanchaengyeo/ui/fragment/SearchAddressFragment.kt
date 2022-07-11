package com.usanchaengyeo.usanchaengyeo.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.usanchaengyeo.usanchaengyeo.databinding.FragmentSearchAddressBinding
import com.usanchaengyeo.usanchaengyeo.ui.viewmodel.AddressViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchAddressFragment : Fragment() {

    private lateinit var binding: FragmentSearchAddressBinding
    private val addressViewModel by activityViewModels<AddressViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSearchAddressBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            viewmodel = addressViewModel
            lifecycleOwner = viewLifecycleOwner
        }

        replaceChildFragment(historyFragment)
        addressViewModel.inputKeyword.observe(viewLifecycleOwner) { keyword ->
            when {
                keyword.isNullOrEmpty() -> replaceChildFragment(historyFragment)

                else -> replaceChildFragment(searchResultFragment)
            }
        }
    }

    private fun replaceChildFragment(fragment: Fragment) {
        childFragmentManager.beginTransaction().apply {
            replace(binding.subFragmentContainerView.id, fragment)
            commit()
        }
    }

    companion object {
        val searchResultFragment = SearchResultFragment()
        val historyFragment = HistoryFragment()
    }
}