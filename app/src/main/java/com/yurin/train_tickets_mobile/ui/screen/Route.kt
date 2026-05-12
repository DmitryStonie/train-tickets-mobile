package com.yurin.train_tickets_mobile.ui.screen

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface Route: NavKey {
    @Serializable
    data object Auth: Route

    @Serializable
    data object Empty: Route

    @Serializable
    data object Search: Route

    @Serializable
    data class Seats(val tripId: Int): Route

    @Serializable
    data class Buy(val bookingId: Int): Route

    @Serializable
    data object Success: Route
}