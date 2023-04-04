package com.jymun.usanchaengyeo.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import com.jymun.usanchaengyeo.ui.OnCurrentLocationRequiredListener

abstract class BaseFragment<VM : BaseViewModel, B : ViewDataBinding> : Fragment() {

    abstract val viewModel: VM

    abstract val menuProvider: MenuProvider

    private var _binding: B? = null
    protected val binding: B get() = _binding!!

    protected var onCurrentLocationRequiredListener: OnCurrentLocationRequiredListener? = null

    abstract fun getViewDataBinding(): B

    override fun onAttach(context: Context) {
        super.onAttach(context)

        onCurrentLocationRequiredListener =
            if (context is OnCurrentLocationRequiredListener) context
            else null
    }

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

        val menuHost = requireActivity() as MenuHost
        menuHost.addMenuProvider(menuProvider, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    abstract fun setUpBinding(): B

    abstract fun observeState()
}