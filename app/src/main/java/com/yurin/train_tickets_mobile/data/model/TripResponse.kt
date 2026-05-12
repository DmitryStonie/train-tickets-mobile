package com.yurin.train_tickets_mobile.data.model

import com.yurin.train_tickets_mobile.domain.entity.SeatCategory
import com.yurin.train_tickets_mobile.domain.entity.Trip
import kotlinx.serialization.Serializable

@Serializable
data class TripResponse (
    val id: Int,
    val trainNumber: String,
    val departure: String,
    val destination: String,
    val departureTime: String,
    val arrivalTime: String,
    val availableSeatsCount: Int,
    val minPrice: Double,
    val maxPrice: Double,
    val availableCategories: List<String>
)

fun TripResponse.toDomainTrip(): Trip = Trip(
    id = id,
    trainNumber = trainNumber,
    departure = departure,
    destination = destination,
    departureTime = departureTime,
    arrivalTime = arrivalTime,
    availableSeatsNumber = availableSeatsCount,
    minPrice = minPrice,
    maxPrice = maxPrice,
    availableCategories = availableCategories.map { it.toSeatCategory() }
)

fun String.toSeatCategory(): SeatCategory {
    return when(this){
        SeatCategory.ECONOMY.name -> SeatCategory.ECONOMY
        "STANDARD" -> SeatCategory.STANDARD
        "BUSINESS" -> SeatCategory.BUSINESS
        "LUXURY" -> SeatCategory.LUXURY
        else -> throw IllegalArgumentException("Invalid seat category")
    }
}