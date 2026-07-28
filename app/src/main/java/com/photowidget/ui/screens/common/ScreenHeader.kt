package com.photowidget.ui.screens.common

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.photowidget.R
import com.photowidget.ui.components.core.IconButtonVariant
import com.photowidget.ui.components.core.PhotoWidgetIconButton
import com.photowidget.ui.theme.Lora

/** Back-button + centered italic-Lora title header shared by App Settings, Language, About, Privacy. */
@Composable
fun ScreenHeader(title: String, onBack: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        PhotoWidgetIconButton(
            icon = Icons.AutoMirrored.Rounded.ArrowBack,
            contentDescription = stringResource(R.string.navigate_back),
            onClick = onBack,
            variant = IconButtonVariant.Ghost,
        )
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium.copy(fontStyle = FontStyle.Italic, fontFamily = Lora),
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f),
        )
        androidx.compose.foundation.layout.Spacer(modifier = Modifier.size(40.dp))
    }
}
