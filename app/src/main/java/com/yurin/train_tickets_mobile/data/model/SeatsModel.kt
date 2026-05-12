package com.yurin.train_tickets_mobile.data.model

import kotlinx.serialization.Serializable

@Serializable
data class SeatsModel(
    val id: Int,
    val carriageNumber: String,
    val seatNumber: String,
    val price: Double,
    val category: String,
    val status: String,
)
