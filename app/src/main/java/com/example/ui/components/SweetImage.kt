package com.example.ui.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.model.SweetItem

@Composable
fun SweetImage(
  sweetItem: SweetItem,
  contentDescription: String?,
  modifier: Modifier = Modifier,
  contentScale: ContentScale = ContentScale.Crop,
) {
  SweetImage(
    imageRes = sweetItem.imageRes,
    customImageUri = sweetItem.customImageUri,
    contentDescription = contentDescription,
    modifier = modifier,
    contentScale = contentScale,
  )
}

@Composable
fun SweetImage(
  imageRes: Int,
  customImageUri: String?,
  contentDescription: String?,
  modifier: Modifier = Modifier,
  contentScale: ContentScale = ContentScale.Crop,
) {
  val trimmedUri = customImageUri?.trim()
  if (!trimmedUri.isNullOrEmpty()) {
    AsyncImage(
      model = ImageRequest.Builder(LocalContext.current)
        .data(trimmedUri)
        .crossfade(true)
        .placeholder(imageRes)
        .error(imageRes)
        .fallback(imageRes)
        .build(),
      contentDescription = contentDescription,
      contentScale = contentScale,
      modifier = modifier,
    )
  } else {
    Image(
      painter = painterResource(id = imageRes),
      contentDescription = contentDescription,
      contentScale = contentScale,
      modifier = modifier,
    )
  }
}
