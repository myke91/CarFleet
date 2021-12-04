package com.wundermobility.test.carfleet.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class CarFleetViewModel() : ViewModel() {
    var isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val errorMessage: MutableLiveData<String> = MutableLiveData()

    fun onError(message: String) {
        errorMessage.value = message
        isLoading.value = false
    }
}