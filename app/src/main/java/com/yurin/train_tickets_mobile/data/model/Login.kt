package com.yurin.train_tickets_mobile.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Login(
    val username: String,
    val password: String,
)