package com.yurin.train_tickets_mobile.data.model

import kotlinx.serialization.Serializable

@Serializable
data class SeatsResponse (
    val tripId: Int,
    val groups: List<Group>
)

