package com.yurin.train_tickets_mobile.domain.usecase

import com.yurin.train_tickets_mobile.data.SeatsRepository
import com.yurin.train_tickets_mobile.domain.entity.SeatsGroup
import jakarta.inject.Inject

class GetSeatsUseCase @Inject constructor(private val seatsRepository: SeatsRepository) {
    suspend operator fun invoke(tripId: Int): List<SeatsGroup> {
        return seatsRepository.getSeats(tripId)
    }
}