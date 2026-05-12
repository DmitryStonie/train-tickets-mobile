package com.yurin.train_tickets_mobile.domain.usecase

import com.yurin.train_tickets_mobile.data.BookingRepository
import javax.inject.Inject

class CancelBookingUseCase @Inject constructor(private val bookingRepository: BookingRepository) {
    suspend operator fun invoke(bookingId: Int) {
        bookingRepository.cancelBooking(bookingId)
    }
}