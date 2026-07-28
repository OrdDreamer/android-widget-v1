package com.photowidget.ui.components.core

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.photowidget.ui.theme.StatusDanger
import com.photowidget.ui.theme.StatusDangerSubtleBg
import com.photowidget.ui.theme.Terracotta700
import com.photowidget.ui.theme.statusSuccess

enum class BadgeTone { Neutral, Accent, Success, Danger }

/** Design-system Badge (core/Badge.jsx): pill, uppercase caption, 4 semantic tones. */
@Composable
fun PhotoWidgetBadge(text: String, modifier: Modifier = Modifier, tone: BadgeTone = BadgeTone.Neutral) {
    val (background, content) = when (tone) {
        BadgeTone.Neutral -> MaterialTheme.colorScheme.surfaceVariant to MaterialTheme.colorScheme.onSurfaceVariant
        BadgeTone.Accent -> MaterialTheme.colorScheme.primaryContainer to Terracotta700
        BadgeTone.Success -> MaterialTheme.colorScheme.secondaryContainer to statusSuccess()
        BadgeTone.Danger -> StatusDangerSubtleBg to StatusDanger
    }
    Text(
        text = text.uppercase(),
        style = MaterialTheme.typography.labelSmall,
        color = content,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier
            .background(background, RoundedCornerShape(percent = 50))
            .padding(horizontal = 10.dp, vertical = 4.dp),
    )
}
