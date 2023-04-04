package com.jymun.usanchaengyeo.ui.history

import com.jymun.usanchaengyeo.data.model.history.History
import com.jymun.usanchaengyeo.ui.base.adapter.AdapterListener

interface HistoryAdapterListener : AdapterListener {

    fun onHistoryItemClicked(history: History)
}