package com.jymun.usanchaengyeo.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<VM : BaseViewModel, B : ViewDataBinding> : Fragment() {

    abstract val viewModel: VM

    private var _binding: B? = null
    protected val binding: B get() = _binding!!

    abstract fun getViewDataBinding(): B

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = getViewDataBinding()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpBinding()
        observeState()
    }

    abstract fun setUpBinding(): B

    abstract fun observeState()
}