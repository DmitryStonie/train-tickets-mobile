package com.yurin.train_tickets_mobile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yurin.train_tickets_mobile.domain.usecase.CheckUserLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class MainViewModel @Inject constructor(
    private val checkUserLoginUseCase: CheckUserLoginUseCase
) : ViewModel() {

    private val _isUserLogged = MutableStateFlow<Boolean?>(null)
    val isUserLogged = _isUserLogged.asStateFlow()

    init {
        checkUserLogin()
    }

    fun checkUserLogin() {
        viewModelScope.launch {
            _isUserLogged.value = checkUserLoginUseCase()
        }
    }
}