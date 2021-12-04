package com.wundermobility.test.carfleet.model

data class RentalStatus(
    var reservationId: Int = 0,
    var carId: Int = 0,
    var cost: Int = 0,
    var drivenDistance: Int = 0,
    var startAddress: String = "",
    var userId: Int = 0,
    var fuelCardPin: String = "",
    var endTime: Long = 0L,
    var startTime: Long = 0L
)