package com.wundermobility.test.carfleet.ui.viewmodel

import android.view.View
import androidx.databinding.BindingAdapter
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

@BindingAdapter("visible")
fun View.toggleVisibility(loading: Boolean) {
    if (loading) {
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.GONE
    }
}