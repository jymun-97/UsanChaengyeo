package com.jymun.usanchaengyeo.ui.extension

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jymun.usanchaengyeo.data.model.Model
import com.jymun.usanchaengyeo.ui.base.adapter.ModelRecyclerAdapter

object BindingAdapters {

    @BindingAdapter("app:items")
    @JvmStatic
    fun setItems(recyclerView: RecyclerView, items: List<Model>?) {
        items ?: return
        (recyclerView.adapter as ModelRecyclerAdapter<*>).submitList(items.toMutableList())
    }
}