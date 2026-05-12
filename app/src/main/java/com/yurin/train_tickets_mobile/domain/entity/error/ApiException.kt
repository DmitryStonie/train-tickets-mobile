package com.yurin.train_tickets_mobile.domain.entity.error

data class UnauthorizedException(override val message: String) : AppException(message)
data class ApiException(override val message: String) : AppException(message)