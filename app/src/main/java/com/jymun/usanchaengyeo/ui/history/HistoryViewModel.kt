package com.jymun.usanchaengyeo.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jymun.harusekki.ui.base.BaseViewModel
import com.jymun.usanchaengyeo.data.model.history.History
import com.jymun.usanchaengyeo.domain.history.AddHistoryUseCase
import com.jymun.usanchaengyeo.domain.history.DeleteHistoryUseCase
import com.jymun.usanchaengyeo.domain.history.LoadHistoryUseCase
import com.jymun.usanchaengyeo.util.dispatcher.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    dispatcherProvider: DispatcherProvider,
    private val loadHistoryUseCase: LoadHistoryUseCase,
    private val addHistoryUseCase: AddHistoryUseCase,
    private val deleteHistoryUseCase: DeleteHistoryUseCase
) : BaseViewModel(dispatcherProvider) {

    private val _historyList = MutableLiveData<List<History>?>()
    val historyList: LiveData<List<History>?>
        get() = _historyList

    fun loadHistory() = onMainDispatcher {
        _historyList.postValue(
            loadHistoryUseCase()
        )
    }
}