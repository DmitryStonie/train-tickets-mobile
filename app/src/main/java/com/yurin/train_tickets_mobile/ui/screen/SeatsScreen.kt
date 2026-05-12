package com.yurin.train_tickets_mobile.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.yurin.train_tickets_mobile.R
import com.yurin.train_tickets_mobile.domain.entity.Status
import com.yurin.train_tickets_mobile.presentation.BookingState
import com.yurin.train_tickets_mobile.presentation.SeatsScreenState
import com.yurin.train_tickets_mobile.presentation.SeatsViewModel
import com.yurin.train_tickets_mobile.ui.component.ErrorDialog
import com.yurin.train_tickets_mobile.ui.component.Loading

@Composable
fun SeatsScreen(seatsViewModel: SeatsViewModel, onBookSuccess: (Int) -> Unit) {

    val screenState by seatsViewModel.screenState.collectAsState()

    LaunchedEffect(Unit) {
        seatsViewModel.getSeats()
    }

    when (val state = screenState) {
        SeatsScreenState.Initial -> {}
        SeatsScreenState.Loading -> {
            Loading()
        }

        is SeatsScreenState.Error -> {
            ErrorDialog(
                message = state.message,
                onRetry = {
                    seatsViewModel.getSeats()
                }
            )
        }

        is SeatsScreenState.Content -> {
            Screen(state, seatsViewModel, onBookSuccess)
        }
    }
}

@Composable
fun Screen(
    screenState: SeatsScreenState.Content,
    seatsViewModel: SeatsViewModel,
    onBookSuccess: (Int) -> Unit,
) {
    var selectedItem by remember { mutableStateOf<Int?>(null) }

    when (val state = screenState.bookingState) {
        is BookingState.Error -> {
            ErrorDialog(
                message = state.message,
                onRetry = {
                    seatsViewModel.setBookingInitialState()
                }
            )
        }
        else -> {}
    }


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp, bottom = 24.dp)
    ) {
        Text(
            stringResource(R.string.select_seat),
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            for (group in screenState.seatsGroup) {
                item {
                    Text(
                        stringResource(group.category.value),
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }

                items(items = group.seats) { seat ->
                    if (seat.status == Status.AVAILABLE) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    if (selectedItem == seat.id) MaterialTheme.colorScheme.surfaceContainerHighest else MaterialTheme.colorScheme.background
                                )
                                .clickable(onClick = {
                                    selectedItem = seat.id
                                })
                                .padding(horizontal = 16.dp)
                        ) {
                            HorizontalDivider(modifier = Modifier.fillMaxWidth())

                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                            ) {
                                Number(stringResource(R.string.carriage), seat.carriageNumber)

                                Number(stringResource(R.string.seat), seat.seatNumber)
                            }

                            Text(
                                "${seat.price} руб.",
                                textAlign = TextAlign.End,
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }
        }

        Button(
            onClick = {
                selectedItem?.let { seatsViewModel.bookSeat(it, onBookSuccess) }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            when (screenState.bookingState) {
                BookingState.Loading -> {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.background,
                        trackColor = MaterialTheme.colorScheme.background,
                        modifier = Modifier.height(30.dp)
                    )
                }

                else -> {
                    Text(stringResource(R.string.book_seat))
                }
            }
        }
    }
}

@Composable
fun Number(value: String, number: String) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(value, style = MaterialTheme.typography.titleMedium)

        Text(number, textAlign = TextAlign.Center, style = MaterialTheme.typography.headlineMedium)
    }
}