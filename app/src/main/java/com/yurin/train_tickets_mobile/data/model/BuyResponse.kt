package com.yurin.train_tickets_mobile.data.model

import kotlinx.serialization.Serializable

@Serializable
data class BuyResponse (
    val status: String,
    val bookingId: Int,
    val message: String
)