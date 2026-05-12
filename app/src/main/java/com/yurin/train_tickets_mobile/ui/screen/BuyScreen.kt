package com.yurin.train_tickets_mobile.ui.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.yurin.train_tickets_mobile.R
import com.yurin.train_tickets_mobile.domain.entity.Ticket
import com.yurin.train_tickets_mobile.presentation.BookingScreenState
import com.yurin.train_tickets_mobile.presentation.BookingViewModel
import com.yurin.train_tickets_mobile.presentation.PaymentState
import com.yurin.train_tickets_mobile.ui.component.ErrorDialog
import com.yurin.train_tickets_mobile.ui.component.Loading

@Composable
fun BuyScreen(
    bookingViewModel: BookingViewModel,
    onSuccess: () -> Unit,
    onBackClick: () -> Unit,
) {
    val screenState by bookingViewModel.screenState.collectAsState()

    LaunchedEffect(Unit) {
        bookingViewModel.getBooking()
    }

    BackHandler() {
        bookingViewModel.cancelBooking()
        onBackClick()
    }

    when (val state = screenState) {
        BookingScreenState.Loading -> {
            Loading()
        }
        is BookingScreenState.Error -> {
            ErrorDialog(
                message = state.message,
                onRetry = {
                    bookingViewModel.getBooking()
                }
            )
        }
        is BookingScreenState.Content -> {
            Screen(state, bookingViewModel, onSuccess, onBackClick)
        }
    }
}

@Composable
fun Screen(
    screenState: BookingScreenState.Content,
    bookingViewModel: BookingViewModel,
    onSuccess: () -> Unit,
    onBackClick: () -> Unit,
) {
    var lastName by remember { mutableStateOf("") }
    var firstName by remember { mutableStateOf("") }
    var middleName by remember { mutableStateOf("") }

    when (val state = screenState.paymentState) {
        is PaymentState.Error -> {
            ErrorDialog(
                message = state.message,
                onRetry = {
                    bookingViewModel.setPaymentInitialState()
                }
            )
        }
        PaymentState.Success -> {
            onSuccess()
        }

        else -> {}
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 24.dp, bottom = 24.dp)
    ) {
        Row(modifier = Modifier.padding(vertical = 8.dp)) {
            Text(
                stringResource(R.string.buy_ticket),
                style = MaterialTheme.typography.headlineLarge,
            )

            Spacer(modifier = Modifier.weight(1f))

            IconButton(
                onClick = {
                    bookingViewModel.cancelBooking()
                    onBackClick()
                },
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Icon(painterResource(R.drawable.close_24dp), contentDescription = null)
            }
        }

        Ticket(ticket = screenState.booking.ticket)

        TextField(
            value = lastName,
            onValueChange = { lastName = it },
            label = {
                Text(stringResource(R.string.last_name))
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 10.dp)
        )

        TextField(
            value = firstName,
            onValueChange = { firstName = it },
            label = {
                Text(stringResource(R.string.first_name))
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
        )

        TextField(
            value = middleName,
            onValueChange = { middleName = it },
            label = {
                Text(stringResource(R.string.middle_name))
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                if (lastName.isNotEmpty() && firstName.isNotEmpty() && middleName.isNotEmpty())
                    bookingViewModel.buyTicket(lastName, firstName, middleName)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            when (screenState.paymentState) {
                PaymentState.Loading -> {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.background,
                        trackColor = MaterialTheme.colorScheme.background,
                        modifier = Modifier.height(30.dp)
                    )
                }

                else -> {
                    Text(stringResource(R.string.buy))
                }
            }
        }
    }
}

@Composable
fun Ticket(ticket: Ticket) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surfaceContainerHighest)
            .padding(6.dp)
    ) {
        Text(
            ticket.trainNumber,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(ticket.departure, style = MaterialTheme.typography.titleMedium)

            Icon(painterResource(R.drawable.arrow_right_alt_24dp), contentDescription = null)

            Text(ticket.destination, style = MaterialTheme.typography.titleMedium)
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(formatDateTime(ticket.departureTime))

            Icon(painterResource(R.drawable.arrow_right_alt_24dp), contentDescription = null)

            Text(formatDateTime(ticket.arrivalTime))
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Number(stringResource(R.string.carriage), ticket.carriageNumber)

            Number(stringResource(R.string.seat), ticket.seatNumber)
        }

        Text(
            "${ticket.price} руб.",
            textAlign = TextAlign.End,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.fillMaxWidth()
        )
    }
}