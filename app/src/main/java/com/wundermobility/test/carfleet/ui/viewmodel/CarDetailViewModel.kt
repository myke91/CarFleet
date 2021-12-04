package com.wundermobility.test.carfleet.ui.viewmodel

import android.util.Log
import android.view.View
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import com.wundermobility.test.carfleet.CarRepository
import com.wundermobility.test.carfleet.model.CarDetail
import com.wundermobility.test.carfleet.model.RentalStatus
import com.wundermobility.test.carfleet.util.Constants.TAG
import kotlinx.coroutines.*

class CarDetailViewModel : CarFleetViewModel() {
    var job: Job? = null
    val car: MutableLiveData<CarDetail> = MutableLiveData<CarDetail>()
    val rentalStatus: MutableLiveData<RentalStatus> = MutableLiveData<RentalStatus>()

    init {
        isLoading.value = false
    }

    fun getCarDetail(id: String) {
        isLoading.value = true

        job = CoroutineScope(Dispatchers.IO).launch {
            val response = CarRepository.instance().getCarDetail(id)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    Log.i(TAG, "Retrieved details for car with carId ${response.body()?.carId}")
                    car.postValue(response.body())
                    isLoading.value = false
                } else {
                    Log.e(TAG, "Error : ${response.message()} ")
                    onError("Error : ${response.message()} ")
                }
            }
        }
    }

    fun rentCar() {
        isLoading.value = true

        job = CoroutineScope(Dispatchers.IO).launch {
            val params = mapOf(
                "carId" to car.value?.carId.toString()
            )
            val response = CarRepository.instance().rentCar(params)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    Log.i(TAG, "Successfully performed quick retail for car with carId " +
                            "${response.body()?.carId} from ${response.body()?.startTime} to ${response.body()?.endTime}")
                    isLoading.value = false
                    rentalStatus.postValue(response.body())
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