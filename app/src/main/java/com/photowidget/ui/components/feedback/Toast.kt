package com.photowidget.ui.components.feedback

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.photowidget.ui.theme.DurationBase
import com.photowidget.ui.theme.PhotoWidgetElevation
import com.photowidget.ui.theme.RadiusMd
import com.photowidget.ui.theme.StatusDanger
import com.photowidget.ui.theme.SurfaceInverse
import com.photowidget.ui.theme.TextInverse
import com.photowidget.ui.theme.photoWidgetShadow
import com.photowidget.ui.theme.statusSuccess

enum class ToastTone { Default, Success, Danger }

/** Design-system Toast (feedback/Toast.jsx): fade + 8dp slide-up, one of 3 tones, ink-on-cream regardless of app theme. */
@Composable
fun PhotoWidgetToast(
    text: String,
    visible: Boolean,
    modifier: Modifier = Modifier,
    tone: ToastTone = ToastTone.Default,
    icon: ImageVector? = null,
) {
    val background = when (tone) {
        ToastTone.Default -> SurfaceInverse
        ToastTone.Success -> statusSuccess()
        ToastTone.Danger -> StatusDanger
    }
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(tween(DurationBase)) + slideInVertically(tween(DurationBase)) { it / 4 },
        exit = fadeOut(tween(DurationBase)) + slideOutVertically(tween(DurationBase)) { it / 4 },
        modifier = modifier,
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .photoWidgetShadow(PhotoWidgetElevation.lg, RoundedCornerShape(RadiusMd))
                .background(background, RoundedCornerShape(RadiusMd))
                .padding(horizontal = 18.dp, vertical = 12.dp),
        ) {
            if (icon != null) {
                Icon(imageVector = icon, contentDescription = null, tint = TextInverse)
            }
            Text(text = text, style = MaterialTheme.typography.bodyMedium, color = TextInverse)
        }
    }
}
