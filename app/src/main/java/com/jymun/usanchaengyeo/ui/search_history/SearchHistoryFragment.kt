package com.jymun.usanchaengyeo.ui.search_history

import androidx.fragment.app.viewModels
import com.jymun.usanchaengyeo.databinding.FragmentSearchHistoryBinding
import com.jymun.usanchaengyeo.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchHistoryFragment : BaseFragment<SearchHistoryViewModel, FragmentSearchHistoryBinding>() {

    override val viewModel: SearchHistoryViewModel by viewModels()

    override fun getViewDataBinding() = FragmentSearchHistoryBinding.inflate(layoutInflater)

    override fun setUpBinding() = binding.apply { }

    override fun observeState() {}
}