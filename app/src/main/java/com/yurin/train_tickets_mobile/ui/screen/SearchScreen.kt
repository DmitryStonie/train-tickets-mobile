package com.yurin.train_tickets_mobile.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.yurin.train_tickets_mobile.R
import com.yurin.train_tickets_mobile.domain.entity.Trip
import com.yurin.train_tickets_mobile.presentation.SearchScreenState
import com.yurin.train_tickets_mobile.presentation.SearchViewModel
import com.yurin.train_tickets_mobile.presentation.TripListState
import com.yurin.train_tickets_mobile.ui.component.DatePickerDocked
import com.yurin.train_tickets_mobile.ui.component.DropdownMenu
import com.yurin.train_tickets_mobile.ui.component.Error
import com.yurin.train_tickets_mobile.ui.component.ErrorDialog
import com.yurin.train_tickets_mobile.ui.component.Loading
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun SearchScreen(searchViewModel: SearchViewModel = hiltViewModel(), onTripClick: (Int) -> Unit) {

    val screenState by searchViewModel.screenState.collectAsState()

    LaunchedEffect(Unit) {
        searchViewModel.getDestAndDep()
    }

    when (val state = screenState) {
        SearchScreenState.Initial -> {}
        is SearchScreenState.Error -> {
            ErrorDialog(
                message = state.message,
                onRetry = {
                    searchViewModel.getDestAndDep()
                }
            )
        }

        is SearchScreenState.Content -> {
            Screen(state, searchViewModel, onTripClick)
        }
    }
}

@Composable
fun Screen(
    screenState: SearchScreenState.Content,
    searchViewModel: SearchViewModel,
    onTripClick: (Int) -> Unit,
) {
    var selectedDeparture by remember { mutableStateOf<Int?>(null) }
    var selectedDestination by remember { mutableStateOf<Int?>(null) }
    var selectedDate by remember { mutableStateOf<Long?>(null) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 24.dp)
    ) {
        Text(
            stringResource(R.string.find_trip),
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        DropdownMenu(
            placeholder = stringResource(R.string.departure),
            options = screenState.departure,
            selectedIndex = selectedDeparture ?: -1,
            onSelect = { selectedDeparture = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        DropdownMenu(
            placeholder = stringResource(R.string.destination),
            options = screenState.destination,
            selectedIndex = selectedDestination ?: -1,
            onSelect = { selectedDestination = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        DatePickerDocked(
            selectedDate = selectedDate,
            onDateSelect = { selectedDate = it },
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Button(
            onClick = {
                if (selectedDeparture != null && selectedDestination != null) {
                    searchViewModel.getTrips(
                        screenState.departure[selectedDeparture!!],
                        screenState.destination[selectedDestination!!]
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        ) {
            Text(stringResource(R.string.find))
        }

        when (val state = screenState.tripListState) {
            TripListState.NoTrips -> {
                Text(
                    stringResource(R.string.no_such_trips),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            TripListState.Loading -> {
                Loading()
            }

            is TripListState.Error -> {
                Error(
                    message = state.message,
                    onRetryClick = {
                        if (selectedDeparture != null && selectedDestination != null) {
                            searchViewModel.getTrips(
                                screenState.departure[selectedDeparture!!],
                                screenState.destination[selectedDestination!!]
                            )
                        }
                    }
                )
            }

            is TripListState.Content -> {
                TripList(state.trips, onTripClick)
            }
        }

    }

}

@Composable
fun TripList(trips: List<Trip>, onTripClick: (Int) -> Unit) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(items = trips) { trip ->
            if (trip.availableSeatsNumber > 0) {
                Trip(trip, onTripClick)
            }
        }
    }
}

@Composable
fun Trip(trip: Trip, onTripClick: (Int) -> Unit) {
    Column(
        modifier = Modifier
            .clickable(onClick = { onTripClick(trip.id) })
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surfaceContainerHighest)
            .padding(6.dp)
    ) {
        Text(
            trip.trainNumber,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(trip.departure, style = MaterialTheme.typography.titleMedium)

            Icon(painterResource(R.drawable.arrow_right_alt_24dp), contentDescription = null)

            Text(trip.destination, style = MaterialTheme.typography.titleMedium)
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(formatDateTime(trip.departureTime))

            Icon(painterResource(R.drawable.arrow_right_alt_24dp), contentDescription = null)

            Text(formatDateTime(trip.arrivalTime))
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(stringResource(R.string.available_seats))

            Text(trip.availableSeatsNumber.toString())

            Spacer(modifier = Modifier.weight(1f))

            Text(
                "${trip.minPrice}-${trip.maxPrice} руб."
            )
        }

        for (category in trip.availableCategories) {
            HorizontalDivider(modifier = Modifier.fillMaxWidth())

            Text(stringResource(category.value), modifier = Modifier.padding(vertical = 8.dp))
        }
    }
}

fun formatDateTime(isoDate: String): String {
    val form = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
    val dateTime = LocalDateTime.parse(isoDate, form)
    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")
    val text: String = dateTime.format(formatter)
    return text
}