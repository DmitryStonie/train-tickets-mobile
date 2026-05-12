package com.yurin.train_tickets_mobile.domain.usecase

import com.yurin.train_tickets_mobile.data.AuthRepository
import javax.inject.Inject

class CheckUserLoginUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke(): Boolean {
        return authRepository.checkUserLogin()
    }
}