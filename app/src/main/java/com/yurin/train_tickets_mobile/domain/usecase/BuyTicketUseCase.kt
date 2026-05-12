package com.yurin.train_tickets_mobile.domain.usecase

import com.yurin.train_tickets_mobile.data.BookingRepository
import javax.inject.Inject

class BuyTicketUseCase @Inject constructor(private val bookingRepository: BookingRepository) {
    suspend operator fun invoke(
        bookingId: Int,
        lastName: String,
        firstName: String,
        middleName: String,
    ) {
        bookingRepository.buyTicket(bookingId, lastName, firstName, middleName)
    }
}