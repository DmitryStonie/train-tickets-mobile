package com.yurin.train_tickets_mobile.data

import com.yurin.train_tickets_mobile.data.model.toDomainTrip
import com.yurin.train_tickets_mobile.domain.entity.Trip
import jakarta.inject.Inject

class SearchRepository @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun getDestinations(): List<String> {
        return apiService.getTrips().map { it.destination }.distinct()
    }

    suspend fun getDeparture(): List<String> {
        return apiService.getTrips().map { it.departure }.distinct()
    }

    suspend fun getTrips(departure: String, destination: String): List<Trip> {
        return apiService.getTrips(departure, destination).map { it.toDomainTrip() }
    }

}