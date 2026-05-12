package com.yurin.train_tickets_mobile.data.model

import com.yurin.train_tickets_mobile.domain.entity.Ticket
import kotlinx.serialization.Serializable

@Serializable
data class TicketModel(
    val tripId: Int,
    val trainNumber: String,
    val departure: String,
    val destination: String,
    val departureTime: String,
    val arrivalTime: String,
    val carriageNumber: String,
    val seatNumber: String,
    val category: String,
    val price: Double
)

fun TicketModel.toDomainTicket(): Ticket = Ticket(
    tripId = tripId,
    trainNumber = trainNumber,
    departure = departure,
    destination = destination,
    departureTime = departureTime,
    arrivalTime = arrivalTime,
    carriageNumber = carriageNumber,
    seatNumber = seatNumber,
    category = category.toSeatCategory(),
    price = price
)
