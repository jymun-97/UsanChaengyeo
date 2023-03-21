package com.jymun.usanchaengyeo.ui.forecast

import androidx.fragment.app.viewModels
import com.jymun.usanchaengyeo.databinding.FragmentForecastBinding
import com.jymun.usanchaengyeo.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForecastFragment : BaseFragment<ForecastViewModel, FragmentForecastBinding>() {

    override val viewModel: ForecastViewModel by viewModels()

    override fun getViewDataBinding() = FragmentForecastBinding.inflate(layoutInflater)

    override fun setUpBinding() = binding.apply { }

    override fun observeState() {}
}