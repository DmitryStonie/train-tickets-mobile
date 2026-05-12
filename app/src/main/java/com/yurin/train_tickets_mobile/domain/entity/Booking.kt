package com.yurin.train_tickets_mobile.domain.entity

data class Booking (
    val id: Int,
    val seatId: Int,
    val status: Status,
    val expiresAt: String,
    val ticket: Ticket,
)