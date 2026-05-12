package com.yurin.train_tickets_mobile.domain.entity

data class Seat (
    val id: Int,
    val carriageNumber: String,
    val seatNumber: String,
    val price: Double,
    val category: SeatCategory,
    val status: Status
)