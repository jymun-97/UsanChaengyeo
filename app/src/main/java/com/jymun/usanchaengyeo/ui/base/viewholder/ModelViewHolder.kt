package com.jymun.usanchaengyeo.ui.base.viewholder

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.jymun.usanchaengyeo.data.model.Model
import com.jymun.usanchaengyeo.ui.base.adapter.AdapterListener
import com.jymun.usanchaengyeo.util.resources.ResourcesProvider

abstract class ModelViewHolder<M : Model>(
    binding: ViewDataBinding,
    protected val resourcesProvider: ResourcesProvider
) : RecyclerView.ViewHolder(binding.root) {

    abstract fun bindData(model: M, adapterListener: AdapterListener?)
}