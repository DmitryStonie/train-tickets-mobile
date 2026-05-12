package com.yurin.train_tickets_mobile.domain.entity

data class SeatsGroup(
    val category: SeatCategory,
    val seats: List<Seat>
)
