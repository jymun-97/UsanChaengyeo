package com.jymun.usanchaengyeo.ui.forecast

import com.jymun.harusekki.ui.base.BaseViewModel
import com.jymun.usanchaengyeo.util.dispatcher.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ForecastViewModel @Inject constructor(
    dispatcherProvider: DispatcherProvider
) : BaseViewModel(dispatcherProvider) {


}