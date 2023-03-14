package com.jymun.usanchaengyeo.ui.base.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import com.jymun.usanchaengyeo.data.model.Model
import com.jymun.usanchaengyeo.data.model.ModelType
import com.jymun.usanchaengyeo.util.resources.ResourcesProvider

object ModelViewHolderMapper {

    @Suppress("UNCHECKED_CAST")
    fun <M : Model> map(
        parent: ViewGroup,
        type: ModelType,
        resourcesProvider: ResourcesProvider
    ): ModelViewHolder<M> {
        val inflater = LayoutInflater.from(parent.context)
        return when (type) {
            else -> {}
        } as ModelViewHolder<M>
    }
}