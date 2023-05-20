package com.flowyh.letmecook.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun TextWithIcon(
  modifier: Modifier = Modifier,
  textModifier: Modifier = Modifier,
  iconModifier: Modifier = Modifier,
  text: String,
  icon: ImageVector,
  iconContentDescription: String,
) {
  Row(
    modifier = modifier,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Icon(
      imageVector = icon,
      contentDescription = iconContentDescription,
      modifier = iconModifier
    )
    Box(
      contentAlignment = Alignment.Center
    ) {
      Text(text = text, modifier = textModifier)
    }
  }
}
