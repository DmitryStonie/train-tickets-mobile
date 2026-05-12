package com.yurin.train_tickets_mobile.domain.entity

data class Trip (
    val id: Int,
    val trainNumber: String,
    val departure: String,
    val destination: String,
    val departureTime: String,
    val arrivalTime: String,
    val availableSeatsNumber: Int,
    val minPrice: Double,
    val maxPrice: Double,
    val availableCategories: List<SeatCategory>
)