package com.yurin.train_tickets_mobile.presentation

import com.yurin.train_tickets_mobile.domain.entity.Booking
import com.yurin.train_tickets_mobile.domain.entity.SeatsGroup

sealed interface SeatsScreenState {
    data object Initial: SeatsScreenState
    data object Loading: SeatsScreenState
    data class Error(val message: String): SeatsScreenState
    data class Content(val seatsGroup: List<SeatsGroup>, val bookingState: BookingState): SeatsScreenState
}

sealed interface BookingState {
    data object Initial: BookingState
    data class Error(val message: String): BookingState
    data object Loading: BookingState
    data class Success(val booking: Booking): BookingState
}