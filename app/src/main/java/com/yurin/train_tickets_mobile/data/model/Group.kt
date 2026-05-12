package com.yurin.train_tickets_mobile.data.model

import com.yurin.train_tickets_mobile.domain.entity.Seat
import com.yurin.train_tickets_mobile.domain.entity.SeatsGroup
import com.yurin.train_tickets_mobile.domain.entity.Status
import kotlinx.serialization.Serializable

@Serializable
data class Group(
    val category: String,
    val seats: List<SeatsModel>
)

fun Group.toDomainGroup(): SeatsGroup = SeatsGroup(
    category = category.toSeatCategory(),
    seats = seats.map { it.toDomainSeat() }
)

fun SeatsModel.toDomainSeat(): Seat = Seat(
    id = id,
    carriageNumber = carriageNumber,
    seatNumber = seatNumber,
    price = price,
    category = category.toSeatCategory(),
    status = status.toStatus()
)

fun String.toStatus(): Status {
    return when(this) {
        Status.AVAILABLE.name -> Status.AVAILABLE
        Status.SOLD.name -> Status.SOLD
        Status.BOOKED.name -> Status.BOOKED
        else -> throw IllegalArgumentException("Invalid seat status")
    }
}
