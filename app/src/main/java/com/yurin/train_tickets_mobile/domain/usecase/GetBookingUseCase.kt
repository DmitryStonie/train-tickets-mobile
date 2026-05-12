package com.yurin.train_tickets_mobile.domain.usecase

import com.yurin.train_tickets_mobile.data.BookingRepository
import com.yurin.train_tickets_mobile.domain.entity.Booking
import jakarta.inject.Inject

class GetBookingUseCase @Inject constructor(private val bookingRepository: BookingRepository) {
    suspend operator fun invoke(bookingId: Int): Booking {
        return bookingRepository.getBooking(bookingId)
    }
}