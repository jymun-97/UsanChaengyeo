package com.jymun.usanchaengyeo.ui.search_address

import androidx.fragment.app.activityViewModels
import com.jymun.usanchaengyeo.databinding.FragmentSearchAddressBinding
import com.jymun.usanchaengyeo.ui.MainViewModel
import com.jymun.usanchaengyeo.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchAddressFragment : BaseFragment<MainViewModel, FragmentSearchAddressBinding>() {

    override val viewModel: MainViewModel by activityViewModels()

    override fun getViewDataBinding() = FragmentSearchAddressBinding.inflate(layoutInflater)

    override fun setUpBinding() = binding.apply { }

    override fun observeState() {}
}