package com.yurin.train_tickets_mobile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yurin.train_tickets_mobile.domain.usecase.AuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authUseCase: AuthUseCase
): ViewModel() {

    private val _screenState = MutableStateFlow<AuthScreenState>(AuthScreenState.Initial)
    val screenState = _screenState.asStateFlow()

    private val _username = MutableStateFlow("")
    val username = _username.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _screenState.value = AuthScreenState.Error(throwable.message ?: "Что-то пошло не так")
    }

    fun login() {
        _screenState.value = AuthScreenState.Loading
        viewModelScope.launch(coroutineExceptionHandler) {
            authUseCase(_username.value, _password.value)
            _screenState.value = AuthScreenState.Success
        }
    }

    fun changeUsername(username: String) {
        _username.value = username
    }

    fun changePassword(password: String) {
        _password.value = password
    }

    fun setInitialState() {
        _screenState.value = AuthScreenState.Initial
    }
}