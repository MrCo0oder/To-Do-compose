package com.example.todo.ui.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.todo.R
import com.example.todo.util.largeIconSize
import com.example.todo.util.largePadding

@Composable
fun ErrorContent(message: String, retry: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.errorContainer),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_error),
            contentDescription = "Empty Content",
            tint = MaterialTheme.colorScheme.onErrorContainer,
            modifier = Modifier
                .size(largeIconSize)
        )
        Text(
            text = message,
            color = MaterialTheme.colorScheme.onErrorContainer,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(largePadding)
        )
        IconButton(
            onClick = retry,
            modifier = Modifier.padding(largePadding)
        ) {
            Icon(imageVector = Icons.Filled.Refresh, contentDescription = "Retry")
        }
    }
}