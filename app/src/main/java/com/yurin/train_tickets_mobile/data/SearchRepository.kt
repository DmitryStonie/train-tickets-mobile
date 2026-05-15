package com.yurin.train_tickets_mobile.data

import com.yurin.train_tickets_mobile.data.model.toDomainTrip
import com.yurin.train_tickets_mobile.domain.entity.Trip
import jakarta.inject.Inject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SearchRepository @Inject constructor(
    private val apiService: ApiService,
) {

    suspend fun getDestinations(): List<String> {
        return apiService.getTrips().map { it.destination }.distinct()
    }

    suspend fun getDeparture(): List<String> {
        return apiService.getTrips().map { it.departure }.distinct()
    }

    suspend fun getTrips(departure: String, destination: String, departureDate: Long?): List<Trip> {
        return apiService.getTrips(departure, destination, departureDate?.let { formatDate(it) })
            .map { it.toDomainTrip() }
    }

    private fun formatDate(date: Long): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return formatter.format(Date(date))
    }
}