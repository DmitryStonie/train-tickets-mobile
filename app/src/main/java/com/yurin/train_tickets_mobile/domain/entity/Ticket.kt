package com.yurin.train_tickets_mobile.domain.entity

data class Ticket (
    val tripId: Int,
    val trainNumber: String,
    val departure: String,
    val destination: String,
    val departureTime: String,
    val arrivalTime: String,
    val carriageNumber: String,
    val seatNumber: String,
    val category: SeatCategory,
    val price: Double
)