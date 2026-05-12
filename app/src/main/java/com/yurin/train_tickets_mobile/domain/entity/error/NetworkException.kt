package com.yurin.train_tickets_mobile.domain.entity.error

data class NetworkException(override val message: String) : AppException(message)