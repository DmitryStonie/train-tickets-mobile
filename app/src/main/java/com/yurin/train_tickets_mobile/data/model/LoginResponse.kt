package com.yurin.train_tickets_mobile.data.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse (
    val accessToken: String,
    val tokenType: String,
    val expiresIn: Int,
)