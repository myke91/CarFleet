package com.wundermobility.test.carfleet.model

data class Car(
    var carId: Int = 0,
    var title: String = "",
    var lat: Double = 0.0,
    var lon: Double = 0.0,
    var licencePlate: String = "",
    var fuelLevel: Int = 0,
    var vehicleStateId: Int = 0,
    var vehicleTypeId: Int = 0,
    var pricingTime: String = "",
    var pricingParking: String = "",
    var reservationState: Int = 0,
    var isClean: Boolean = false,
    var isDamaged: Boolean = false,
    var distance: String = "",
    var address: String = "",
    var zipCode: String = "",
    var city: String = "",
    var locationId: Int = 0,
)