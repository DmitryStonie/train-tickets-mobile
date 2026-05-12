package com.yurin.train_tickets_mobile.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.yurin.train_tickets_mobile.R
import com.yurin.train_tickets_mobile.presentation.AuthScreenState
import com.yurin.train_tickets_mobile.presentation.AuthViewModel
import com.yurin.train_tickets_mobile.ui.component.ErrorDialog
import com.yurin.train_tickets_mobile.ui.component.Loading

@Composable
fun AuthScreen(authViewModel: AuthViewModel = hiltViewModel(), onLogged: () -> Unit) {

    val screenState by authViewModel.screenState.collectAsState()

    when (val state = screenState) {
        is AuthScreenState.Error -> {
            Screen(state, authViewModel)
        }

        AuthScreenState.Loading -> {
            Loading()
        }

        AuthScreenState.Initial -> {
            Screen(state, authViewModel)
        }

        AuthScreenState.Success -> {
            onLogged()
        }
    }
}

@Composable
fun Screen(state: AuthScreenState, authViewModel: AuthViewModel) {

    val showPassword by remember { mutableStateOf(false) }
    val username by authViewModel.username.collectAsState()
    val password by authViewModel.password.collectAsState()

    if (state is AuthScreenState.Error) {
        ErrorDialog(
            message = state.message,
            onRetry = {
                authViewModel.setInitialState()
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 24.dp)
    ) {
        Spacer(modifier = Modifier.weight(1f))

        Text(
            stringResource(R.string.auth),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 16.dp)
        )

        TextField(
            value = username,
            onValueChange = authViewModel::changeUsername,
            label = {
                Text(stringResource(R.string.username))
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
        )

        TextField(
            value = password,
            onValueChange = authViewModel::changePassword,
            label = {
                Text(stringResource(R.string.password))
            },
            visualTransformation =  if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp)
        )

        Button(
            onClick = authViewModel::login,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.enter))
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}