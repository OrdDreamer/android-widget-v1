package com.photowidget.ui.components.feedback

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Error
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.photowidget.ui.theme.RadiusMd
import com.photowidget.ui.theme.StatusDanger
import com.photowidget.ui.theme.StatusDangerSubtleBg
import com.photowidget.ui.theme.StatusDangerSubtleBorder

/** Design-system InlineErrorBanner (feedback/InlineErrorBanner.jsx). */
@Composable
fun PhotoWidgetInlineErrorBanner(text: String, modifier: Modifier = Modifier, onDismiss: (() -> Unit)? = null) {
    val shape = RoundedCornerShape(RadiusMd)
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .background(StatusDangerSubtleBg, shape)
            .border(1.dp, StatusDangerSubtleBorder, shape)
            .padding(horizontal = 14.dp, vertical = 12.dp),
    ) {
        Icon(imageVector = Icons.Rounded.Error, contentDescription = null, tint = StatusDanger, modifier = Modifier.size(18.dp))
        Text(text = text, style = MaterialTheme.typography.bodySmall, color = StatusDanger, modifier = Modifier.weight(1f))
        if (onDismiss != null) {
            IconButton(onClick = onDismiss, modifier = Modifier.size(18.dp)) {
                Icon(imageVector = Icons.Rounded.Close, contentDescription = null, tint = StatusDanger)
            }
        }
    }
}
