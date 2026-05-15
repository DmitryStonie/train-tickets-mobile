package com.yurin.train_tickets_mobile.domain.usecase

import com.yurin.train_tickets_mobile.data.SearchRepository
import com.yurin.train_tickets_mobile.domain.entity.Trip
import jakarta.inject.Inject

class GetTripsUseCase @Inject constructor(private val searchRepository: SearchRepository) {
    suspend operator fun invoke(departure: String, destination: String, departureDate: Long?): List<Trip> {
        return searchRepository.getTrips(departure, destination, departureDate)
    }
}