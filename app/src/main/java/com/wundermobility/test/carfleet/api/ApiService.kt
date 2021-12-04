 package com.wundermobility.test.carfleet.api

import com.wundermobility.test.carfleet.model.Car
import com.wundermobility.test.carfleet.model.CarDetail
import com.wundermobility.test.carfleet.model.RentalStatus
import retrofit2.Response
import retrofit2.http.*

 interface ApiService {
    @POST("wunderfleet-recruiting-mobile-dev-quick-rental")
    suspend fun rentCar(@Header("Authorization") token: String, @Body id: Map<String, String>): Response<RentalStatus>

    @GET(value = "cars.json")
    suspend fun getCars(): Response<List<Car>>

     @GET(value = "cars/{carId}")
     suspend fun getCarDetail(@Path("carId") id: String): Response<CarDetail>
}