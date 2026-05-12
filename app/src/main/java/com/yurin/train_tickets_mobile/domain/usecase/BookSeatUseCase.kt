package com.yurin.train_tickets_mobile.domain.usecase

import com.yurin.train_tickets_mobile.data.SeatsRepository
import com.yurin.train_tickets_mobile.domain.entity.Booking
import javax.inject.Inject

class BookSeatUseCase @Inject constructor(private val seatsRepository: SeatsRepository) {
    suspend operator fun invoke(seatId: Int): Booking {
        return seatsRepository.bookSeat(seatId)
    }
}