package com.yurin.train_tickets_mobile.presentation

import android.R
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yurin.train_tickets_mobile.domain.usecase.BuyTicketUseCase
import com.yurin.train_tickets_mobile.domain.usecase.CancelBookingUseCase
import com.yurin.train_tickets_mobile.domain.usecase.GetBookingUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = BookingViewModel.BookingViewModelFactory::class)
class BookingViewModel @AssistedInject constructor(
    private val getBookingUseCase: GetBookingUseCase,
    private val buyTicketUseCase: BuyTicketUseCase,
    private val cancelBookingUseCase: CancelBookingUseCase,
    @Assisted private val bookingId: Int,
) : ViewModel() {

    private val _screenState = MutableStateFlow<BookingScreenState>(BookingScreenState.Loading)
    val screenState = _screenState.asStateFlow()

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _screenState.value = BookingScreenState.Error(throwable.message ?: "Что-то пошло не так")
    }

    private val coroutineExceptionHandler2 = CoroutineExceptionHandler { _, throwable ->
        if (_screenState.value is BookingScreenState.Content) {
            _screenState.update { currentState ->
                (currentState as BookingScreenState.Content).copy(
                    paymentState = PaymentState.Error(throwable.message ?: "Что-то пошло не так")
                )
            }
        }
    }

    fun getBooking() {
        _screenState.value = BookingScreenState.Loading
        viewModelScope.launch(coroutineExceptionHandler) {
            val booking = getBookingUseCase(bookingId)
            _screenState.value = BookingScreenState.Content(booking, PaymentState.Initial)
        }
    }

    fun cancelBooking() {
        viewModelScope.launch {
            cancelBookingUseCase(bookingId)
        }
    }

    fun setPaymentInitialState() {
        if (_screenState.value is BookingScreenState.Content) {
            _screenState.update { currentState ->
                (currentState as BookingScreenState.Content).copy(
                    paymentState = PaymentState.Initial
                )
            }
        }
    }

    fun buyTicket(lastName: String, firstName: String, middleName: String){
        if (_screenState.value is BookingScreenState.Content) {
            _screenState.update { currentState ->
                (currentState as BookingScreenState.Content).copy(
                    paymentState = PaymentState.Loading
                )
            }
        }
        viewModelScope.launch(coroutineExceptionHandler2) {
            buyTicketUseCase(bookingId, lastName, firstName, middleName)
            if (_screenState.value is BookingScreenState.Content) {
                _screenState.update { currentState ->
                    (currentState as BookingScreenState.Content).copy(
                        paymentState = PaymentState.Success
                    )
                }
            }
        }
    }

    @AssistedFactory
    interface BookingViewModelFactory {
        fun create(bookingId: Int): BookingViewModel
    }
}