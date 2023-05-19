package com.flowyh.letmecook.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.flowyh.letmecook.ui.theme.spacing

@Composable
fun TextWithIcon(
  modifier: Modifier = Modifier,
  text: String,
  icon: ImageVector,
  iconDescription: String,
  textModifier: Modifier = Modifier,
  iconModifier: Modifier = Modifier,
) {
  Row(
    modifier = modifier,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Icon(
      imageVector = icon,
      contentDescription = iconDescription,
      modifier = iconModifier
    )
    Box(
      contentAlignment = Alignment.Center
    ) {
      Text(text = text, modifier = textModifier)
    }
  }
}