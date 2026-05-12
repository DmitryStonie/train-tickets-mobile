package com.yurin.train_tickets_mobile.domain.entity.error

import java.io.IOException

sealed class AppException(override val message: String) : IOException(message)

