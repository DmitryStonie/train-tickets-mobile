package com.yurin.train_tickets_mobile.data

import com.yurin.train_tickets_mobile.data.model.BuyRequest
import com.yurin.train_tickets_mobile.data.model.toDomainBooking
import com.yurin.train_tickets_mobile.domain.entity.Booking
import jakarta.inject.Inject

class BookingRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getBooking(bookingId: Int): Booking {
        return apiService.getBooking(bookingId).toDomainBooking()
    }

    suspend fun buyTicket(bookingId: Int, lastName: String, firstName: String, middleName: String) {
        apiService.buyTicket(bookingId, BuyRequest(lastName, firstName, middleName))
    }

    suspend fun cancelBooking(bookingId: Int) {
        apiService.cancelBooking(bookingId)
    }
}