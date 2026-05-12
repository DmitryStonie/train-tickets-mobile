package com.yurin.train_tickets_mobile.ui.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.yurin.train_tickets_mobile.R

@Composable
fun SuccessScreen(
    onMainScreen: () -> Unit
) {
    BackHandler() {
        onMainScreen()
    }

    Column(
        Modifier
            .padding(16.dp)
    ) {

        Spacer(modifier = Modifier.weight(1f))

        Image(
            painterResource(R.drawable.accept),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 24.dp)
        )

        Text(
            stringResource(R.string.ticket_paid),
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(bottom = 24.dp)
                .align(Alignment.CenterHorizontally)
        )

        Button(
            onClick = onMainScreen,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.on_main_screen))
        }

        Spacer(modifier = Modifier.weight(1f))

    }

}