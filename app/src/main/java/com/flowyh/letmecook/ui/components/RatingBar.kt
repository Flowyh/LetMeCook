package com.flowyh.letmecook.ui.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.flowyh.letmecook.R
import com.flowyh.letmecook.ui.theme.NoRippleTheme

@Composable
fun RatingBar(
  modifier: Modifier = Modifier,
  rating: Float,
  maxRating: Int = 5,
  clickable: Boolean = true,
  activeTint: Color = Color.Yellow,
  inactiveTint: Color = Color.Gray,
  onRatingChanged: (Float) -> Unit
) {
  Row(
    modifier = modifier.horizontalScroll(rememberScrollState())
  ) {
    for (i in 1..maxRating) {
      val isSelected = i <= rating
      if (isSelected)
        IconButton(
          modifier = Modifier
            .aspectRatio(1f),
          interactionSource = NoRippleTheme(),
          onClick = { if (clickable) onRatingChanged(i.toFloat()) }
        ) {
          Icon(
            modifier = Modifier.fillMaxSize(),
            imageVector = Icons.Default.Star,
            contentDescription = stringResource(R.string.rating_star_content_description),
            tint = activeTint
          )
        }
      else
        IconButton(
          modifier = Modifier
            .aspectRatio(1f),
          interactionSource = NoRippleTheme(),
          onClick = { if (clickable) onRatingChanged(i.toFloat()) }
        ) {
          Icon(
            modifier = Modifier.fillMaxSize(),
            imageVector = Icons.Default.StarBorder,
            contentDescription = stringResource(R.string.rating_star_content_description),
            tint = inactiveTint
          )
        }
    }
  }
}

@Preview(showBackground = true)
@Composable
fun RatingBarPreview() {
  RatingBar(
    modifier = Modifier
      .height(200.dp)
      .fillMaxWidth(),
    rating = 3.5f,
    onRatingChanged = {}
  )
}