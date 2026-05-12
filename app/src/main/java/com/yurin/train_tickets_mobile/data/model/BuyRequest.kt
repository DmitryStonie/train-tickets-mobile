package com.yurin.train_tickets_mobile.data.model

import kotlinx.serialization.Serializable

@Serializable
data class BuyRequest (
    val lastName: String,
    val firstName: String,
    val middleName: String,
)