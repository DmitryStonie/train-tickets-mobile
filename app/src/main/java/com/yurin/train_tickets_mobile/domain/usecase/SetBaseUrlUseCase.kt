package com.yurin.train_tickets_mobile.domain.usecase

import com.yurin.train_tickets_mobile.data.AuthRepository
import javax.inject.Inject

class SetBaseUrlUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke(url: String) {
        authRepository.setBaseUrl(url)
    }
}