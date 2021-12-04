package com.wundermobility.test.carfleet

import com.wundermobility.test.carfleet.api.ApiClient
import com.wundermobility.test.carfleet.api.RentalApiClient


class CarRepository {
    companion object {
        fun instance() = CarRepository()
    }
    suspend fun getCars() = ApiClient.instance.getCars()

    suspend fun getCarDetail(id: String) = ApiClient.instance.getCarDetail(id)

    suspend fun rentCar(id: Map<String, String>) = RentalApiClient.instance.rentCar("Bearer df7c313b47b7ef87c64c0f5f5cebd6086bbb0fa", id)
}