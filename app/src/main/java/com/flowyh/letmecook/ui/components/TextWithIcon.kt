package com.flowyh.letmecook.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle

@Composable
fun TextWithIcon(
  modifier: Modifier = Modifier,
  textModifier: Modifier = Modifier,
  iconModifier: Modifier = Modifier,
  text: String,
  textStyle: TextStyle = TextStyle(),
  icon: ImageVector,
  iconContentDescription: String,
  iconTint: Color = Color.Unspecified
) {
  Row(
    modifier = modifier,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Icon(
      imageVector = icon,
      contentDescription = iconContentDescription,
      modifier = iconModifier,
      tint = iconTint
    )
    Box(
      contentAlignment = Alignment.Center
    ) {
      Text(text = text, style = textStyle, modifier = textModifier)
    }
  }
}

@Composable
fun ClickableIconWithText(
  modifier: Modifier = Modifier,
  textModifier: Modifier = Modifier,
  iconModifier: Modifier = Modifier,
  text: String,
  textStyle: TextStyle = TextStyle(),
  inactiveIcon: ImageVector,
  activeIcon: ImageVector,
  iconContentDescription: String,
  iconTint: Color = Color.Unspecified,
  onIconClick: (Boolean) -> Unit = {}
) {
  val interactionSource = remember { MutableInteractionSource() }
  var isActive by rememberSaveable{ mutableStateOf(false) }

  Row(
    modifier = modifier,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Icon(
      imageVector = if (isActive) activeIcon else inactiveIcon,
      contentDescription = iconContentDescription,
      modifier = iconModifier.clickable(
        interactionSource = interactionSource,
        indication = null
      ) {
        onIconClick(isActive)
        isActive = !isActive
      },
      tint = iconTint
    )
    Box(
      contentAlignment = Alignment.Center
    ) {
      Text(text = text, style = textStyle, modifier = textModifier)
    }
  }
}

