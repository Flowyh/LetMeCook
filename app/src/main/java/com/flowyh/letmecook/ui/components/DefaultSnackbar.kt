package com.flowyh.letmecook.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.flowyh.letmecook.ui.theme.spacing

@Composable
fun DefaultSnackBar(
  data: SnackbarData
) {
  Snackbar(
    modifier = Modifier
      .fillMaxWidth(0.8f)
      .padding(MaterialTheme.spacing.medium),
    containerColor = MaterialTheme.colorScheme.primaryContainer,
    contentColor = MaterialTheme.colorScheme.primary,
    action = {
      TextButton(
        onClick = { data.performAction() },
        colors = ButtonDefaults.textButtonColors(
          contentColor = MaterialTheme.colorScheme.primary
        )
      ) { Text(data.visuals.actionLabel ?: "") }
    }
  ) {
    Box(
      Modifier
        .fillMaxWidth(),
      contentAlignment = Alignment.Center
    ) {
      Text(
        data.visuals.message
      )
    }
  }
}