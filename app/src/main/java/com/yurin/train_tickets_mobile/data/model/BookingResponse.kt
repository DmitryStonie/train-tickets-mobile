package com.yurin.train_tickets_mobile.data.model

import com.yurin.train_tickets_mobile.domain.entity.Booking
import kotlinx.serialization.Serializable

@Serializable
data class BookingResponse(
    val id: Int,
    val seatId: Int,
    val status: String,
    val expiresAt: String,
    val ticket: TicketModel,
)

fun BookingResponse.toDomainBooking(): Booking = Booking(
    id = id,
    seatId = seatId,
    status = status.toStatus(),
    expiresAt = expiresAt,
    ticket = ticket.toDomainTicket()
)
