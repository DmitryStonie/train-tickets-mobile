package com.yurin.train_tickets_mobile.presentation

sealed interface AuthScreenState {
    data object Initial: AuthScreenState
    data object Loading: AuthScreenState
    data class Error(val message: String): AuthScreenState
    data object Success: AuthScreenState
}