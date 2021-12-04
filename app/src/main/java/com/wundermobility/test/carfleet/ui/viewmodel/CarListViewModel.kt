package com.wundermobility.test.carfleet.ui.viewmodel


import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.wundermobility.test.carfleet.model.Car
import com.wundermobility.test.carfleet.CarRepository
import com.wundermobility.test.carfleet.util.Constants.TAG
import kotlinx.coroutines.*


class CarListViewModel : CarFleetViewModel() {
    var job: Job? = null
    val carList: MutableLiveData<List<Car>> = MutableLiveData<List<Car>>()

    init {
        isLoading.value = false
        carList.value = listOf()
        getCars();
    }

    fun getCars() {
        isLoading.value = true

        job = CoroutineScope(Dispatchers.IO).launch {
            val response = CarRepository().getCars()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    Log.i(TAG, "Retrieved ${response.body()?.size} cars")
                    carList.postValue(response.body())
                    isLoading.value = false
                } else {
                    Log.e(TAG, "Error : ${response.message()} ")
                    onError("Error : ${response.message()} ")
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

}