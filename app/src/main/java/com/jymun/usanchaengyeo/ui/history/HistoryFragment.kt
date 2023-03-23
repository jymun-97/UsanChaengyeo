package com.jymun.usanchaengyeo.ui.history

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.jymun.usanchaengyeo.data.model.history.History
import com.jymun.usanchaengyeo.databinding.FragmentHistoryBinding
import com.jymun.usanchaengyeo.ui.base.BaseFragment
import com.jymun.usanchaengyeo.ui.base.adapter.ModelRecyclerAdapter
import com.jymun.usanchaengyeo.util.resources.ResourcesProvider
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HistoryFragment : BaseFragment<HistoryViewModel, FragmentHistoryBinding>() {

    @Inject
    lateinit var resourcesProvider: ResourcesProvider

    override val viewModel: HistoryViewModel by viewModels()

    override fun getViewDataBinding() = FragmentHistoryBinding.inflate(layoutInflater)

    override fun setUpBinding() = binding.apply {
        viewModel = this@HistoryFragment.viewModel
        lifecycleOwner = viewLifecycleOwner
    }

    override fun observeState() {}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initHistoryRecyclerView()

        viewModel.loadHistory()
    }

    private fun initHistoryRecyclerView() = binding.historyRecyclerView.apply {
        layoutManager = LinearLayoutManager(requireActivity())
        adapter = ModelRecyclerAdapter<History>(resourcesProvider)
    }
}