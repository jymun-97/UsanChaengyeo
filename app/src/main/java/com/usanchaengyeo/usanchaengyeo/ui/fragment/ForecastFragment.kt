package com.usanchaengyeo.usanchaengyeo.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.usanchaengyeo.usanchaengyeo.databinding.FragmentForecastBinding
import com.usanchaengyeo.usanchaengyeo.ui.adapter.ForecastAdapter
import com.usanchaengyeo.usanchaengyeo.ui.adapter.InfoAdapter
import com.usanchaengyeo.usanchaengyeo.ui.viewmodel.ForecastViewModel
import com.usanchaengyeo.usanchaengyeo.util.Const.INFO_LIST
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForecastFragment : Fragment() {

    private lateinit var binding: FragmentForecastBinding
    private lateinit var forecastAdapter: ForecastAdapter
    private val forecastViewModel by activityViewModels<ForecastViewModel>()
    private val args by navArgs<ForecastFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentForecastBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.forecastRecyclerView.apply {
            forecastAdapter = ForecastAdapter()
            adapter = forecastAdapter
            layoutManager = GridLayoutManager(requireActivity(), 6)
        }

        binding.infoRecyclerView.apply {
            adapter = InfoAdapter().also { it.submitList(INFO_LIST) }
            layoutManager = LinearLayoutManager(requireActivity())

        }

        binding.apply {
            viewmodel = forecastViewModel
            lifecycleOwner = viewLifecycleOwner
        }.also {
            forecastViewModel.selectedAddress = args.selectedAddress
            forecastViewModel.runForecast()
        }
    }
}