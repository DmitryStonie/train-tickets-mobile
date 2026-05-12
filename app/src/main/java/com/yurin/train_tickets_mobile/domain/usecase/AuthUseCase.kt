package com.yurin.train_tickets_mobile.domain.usecase

import com.yurin.train_tickets_mobile.data.AuthRepository
import jakarta.inject.Inject

class AuthUseCase @Inject constructor(private val authRepository: AuthRepository){
    suspend operator fun invoke(username: String, password: String) {
        authRepository.login(username, password)
    }
}