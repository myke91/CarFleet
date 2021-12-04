package com.wundermobility.test.carfleet.model

data class CarDetail(
    var carId: Int = 0,
    var title: String = "",
    var isClean: Boolean = false,
    var isDamaged: Boolean = false,
    var licencePlate: String = "",
    var fuelLevel: Int = 0,
    var vehicleStateId: Int = 0,
    var hardwareId: String = "",
    var vehicleTypeId: Int = 0,
    var pricingTime: String = "",
    var pricingParking: String = "",
    var isActivatedByHardware: Boolean = false,
    var locationId: Int = 0,
    var address: String = "",
    var zipCode: String = "",
    var city: String = "",
    var lat: Double = 0.0,
    var lon: Double = 0.0,
    var reservationState: Int = 0,
    var damageDescription: String = "",
    var vehicleTypeImageUrl: String = ""
)