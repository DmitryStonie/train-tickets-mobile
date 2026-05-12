package com.yurin.train_tickets_mobile.data

import com.yurin.train_tickets_mobile.data.model.Booking
import com.yurin.train_tickets_mobile.data.model.toDomainBooking
import com.yurin.train_tickets_mobile.data.model.toDomainGroup
import com.yurin.train_tickets_mobile.domain.entity.SeatsGroup
import jakarta.inject.Inject

class SeatsRepository @Inject constructor(
    private val apiService: ApiService,
) {
    suspend fun getSeats(tripId: Int): List<SeatsGroup> {
        return apiService.getSeats(tripId).groups.map { it.toDomainGroup() }
    }

    suspend fun bookSeat(seatId: Int): com.yurin.train_tickets_mobile.domain.entity.Booking {
        return apiService.bookSeat(Booking(seatId)).toDomainBooking()
    }
}