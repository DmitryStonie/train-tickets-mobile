package com.yurin.train_tickets_mobile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yurin.train_tickets_mobile.domain.usecase.SetBaseUrlUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val setBaseUrlUseCase: SetBaseUrlUseCase
): ViewModel() {
    private val _url = MutableStateFlow("")
    val url = _url.asStateFlow()

    fun saveUrl() {
        viewModelScope.launch {
            setBaseUrlUseCase(_url.value)
        }
    }

    fun setUrl(url: String){
        _url.value = url
    }
}