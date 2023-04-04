package com.jymun.usanchaengyeo.ui.history

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.jymun.usanchaengyeo.R
import com.jymun.usanchaengyeo.data.model.history.History
import com.jymun.usanchaengyeo.databinding.FragmentHistoryBinding
import com.jymun.usanchaengyeo.ui.base.BaseFragment
import com.jymun.usanchaengyeo.ui.base.LoadState
import com.jymun.usanchaengyeo.ui.base.adapter.ModelRecyclerAdapter
import com.jymun.usanchaengyeo.util.resources.ResourcesProvider
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HistoryFragment : BaseFragment<HistoryViewModel, FragmentHistoryBinding>() {

    @Inject
    lateinit var resourcesProvider: ResourcesProvider

    private var onHistorySelectedListener: OnHistorySelectedListener? = null

    override val viewModel: HistoryViewModel by viewModels()

    override fun getViewDataBinding() = FragmentHistoryBinding.inflate(layoutInflater)

    override fun setUpBinding() = binding.apply {
        viewModel = this@HistoryFragment.viewModel
        lifecycleOwner = viewLifecycleOwner
    }

    override fun observeState() = viewModel.loadState.observe(viewLifecycleOwner) {
        if (it is LoadState.Error) {
            Toast.makeText(
                requireActivity(),
                it.exception.getMessage(resourcesProvider),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is OnHistorySelectedListener) onHistorySelectedListener = context
        else return
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initHistoryRecyclerView()
        initSwipeHelper()

        viewModel.loadHistory()
    }

    private fun initHistoryRecyclerView() = binding.historyRecyclerView.apply {
        layoutManager = LinearLayoutManager(requireActivity())
        adapter = ModelRecyclerAdapter<History>(resourcesProvider).apply {
            addAdapterListener(object : HistoryAdapterListener {
                override fun onHistoryItemClicked(history: History) {
                    viewModel.addHistory(history)
                    onHistorySelectedListener?.onHistorySelected(history)
                    moveToForecastFragment()
                }
            })
        }
    }

    private fun moveToForecastFragment() = findNavController().navigate(
        HistoryFragmentDirections.actionFragmentHistoryToFragmentForecast()
    )

    private fun initSwipeHelper() {
        val historyItemTouchHelper = ItemTouchHelper(
            HistoryItemTouchCallback(
                resourcesProvider = resourcesProvider,
                onRightSwiped = { viewModel.pinHistory(it) },
                onLeftSwiped = { history ->
                    viewModel.deleteHistory(history)
                    Snackbar.make(
                        binding.root,
                        resourcesProvider.getString(R.string.deleted_history),
                        Snackbar.LENGTH_LONG
                    )
                        .setAction(R.string.cancel) { viewModel.addHistory(history) }
                        .setActionTextColor(resourcesProvider.getColor(R.color.gray_light))
                        .show()
                }
            )
        )
        historyItemTouchHelper.attachToRecyclerView(binding.historyRecyclerView)
    }

    override val menuProvider = object : MenuProvider {
        override fun onPrepareMenu(menu: Menu) {
            menu.findItem(R.id.refresh).isVisible = false
        }

        override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
            menuInflater.inflate(R.menu.toolbar_menu, menu)
        }

        override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
            if (menuItem.itemId == R.id.current_location) {
                onCurrentLocationRequiredListener?.onCurrentLocationRequired()
                moveToForecastFragment()

                return true
            }
            return false
        }
    }
}