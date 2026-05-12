package com.yurin.train_tickets_mobile.ui.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.yurin.train_tickets_mobile.presentation.BookingViewModel
import com.yurin.train_tickets_mobile.presentation.MainViewModel
import com.yurin.train_tickets_mobile.presentation.SeatsViewModel

@Composable
fun MainScreen(mainViewModel: MainViewModel = hiltViewModel()) {
    val isUserLogged by mainViewModel.isUserLogged.collectAsState()

    val backStack = rememberNavBackStack(Route.Empty)

    LaunchedEffect(isUserLogged) {
        if (isUserLogged == true) {
            backStack.clearAndAdd(Route.Search)
        } else if (isUserLogged == false) {
            backStack.clearAndAdd(Route.Auth)
        }
    }

    Scaffold { paddingValues ->
        NavDisplay(
            backStack = backStack,
            entryProvider = entryProvider {
                entry<Route.Auth> {
                    AuthScreen(onLogged = {
                        backStack.clearAndAdd(Route.Search)
                    })
                }
                entry<Route.Search> {
                    SearchScreen(onTripClick = {
                        backStack.add(Route.Seats(it))
                    })
                }
                entry<Route.Seats> { route ->
                    val viewModel = hiltViewModel(
                        key = route.tripId.toString(),
                        creationCallback = { factory: SeatsViewModel.SeatsViewModelFactory ->
                            factory.create(route.tripId)
                        }
                    )
                    SeatsScreen(
                        viewModel,
                        onBookSuccess = {
                            backStack.add(Route.Buy(it))
                        }
                    )
                }
                entry<Route.Buy> { route ->
                    val viewModel = hiltViewModel(
                        key = route.bookingId.toString(),
                        creationCallback = { factory: BookingViewModel.BookingViewModelFactory ->
                            factory.create(route.bookingId)
                        }
                    )
                    BuyScreen(
                        viewModel,
                        onSuccess = {
                            backStack.clearAndAdd(Route.Success)
                        },
                        onBackClick = {
                            backStack.removeAt(backStack.lastIndex)
                        }
                    )
                }
                entry<Route.Success> {
                    SuccessScreen(onMainScreen = {
                        backStack.clearAndAdd(Route.Search)
                    })
                }
                entry<Route.Empty> {

                }
            },
            modifier = Modifier.padding(paddingValues)
        )
    }
}

private fun NavBackStack<NavKey>.clearAndAdd(key: NavKey) {
    this.clear()
    this.add(key)
}