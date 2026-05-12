package com.yurin.train_tickets_mobile.ui.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.yurin.train_tickets_mobile.R

@Composable
fun ErrorDialog(message: String, onRetry: () -> Unit) {
    AlertDialog(
        onDismissRequest = {
            onRetry()
        },
        title = {
            Text(
                text = stringResource(id = R.string.error),
                style = MaterialTheme.typography.titleMedium,
            )
        },
        text = {
            Text(
                text = message,
                style = MaterialTheme.typography.labelLarge,
            )
        },
        confirmButton = {
            Button(
                onClick = onRetry,
            ) {
                Text(text = stringResource(id = R.string.retry))
            }
        },
    )
}