package com.yurin.train_tickets_mobile.presentation

import com.yurin.train_tickets_mobile.domain.entity.Booking

sealed interface BookingScreenState {
    data object Loading: BookingScreenState
    data class Error(val message: String): BookingScreenState
    data class Content(val booking: Booking, val paymentState: PaymentState): BookingScreenState
}

sealed interface PaymentState {
    data object Initial: PaymentState
    data object Loading: PaymentState
    data class Error(val message: String): PaymentState
    data object Success: PaymentState
}