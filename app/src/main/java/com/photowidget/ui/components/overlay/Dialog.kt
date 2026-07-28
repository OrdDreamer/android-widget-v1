package com.photowidget.ui.components.overlay

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.photowidget.ui.components.core.ButtonSize
import com.photowidget.ui.components.core.ButtonVariant
import com.photowidget.ui.components.core.PhotoWidgetButton
import com.photowidget.ui.theme.Lora
import com.photowidget.ui.theme.PhotoWidgetElevation
import com.photowidget.ui.theme.RadiusLg
import com.photowidget.ui.theme.photoWidgetShadow
import com.photowidget.ui.theme.surfaceRaised

/**
 * Design-system Dialog (overlay/Dialog.jsx): scrim + raised panel, replacing M3 AlertDialog's
 * default chrome for every confirm dialog in the app (not just Reset — see
 * [com.photowidget.ui.components.overlay.ResetWidgetDialog]).
 */
@Composable
fun PhotoWidgetDialog(
    title: String,
    message: String,
    confirmText: String,
    onConfirm: () -> Unit,
    dismissText: String,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Dialog(onDismissRequest = onDismiss, properties = DialogProperties(usePlatformDefaultWidth = false)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            contentAlignment = Alignment.Center,
        ) {
            Column(
                modifier = modifier
                    .widthIn(max = 360.dp)
                    .photoWidgetShadow(PhotoWidgetElevation.lg, RoundedCornerShape(RadiusLg))
                    .background(surfaceRaised(), RoundedCornerShape(RadiusLg))
                    .padding(24.dp),
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge.copy(fontFamily = Lora, fontStyle = FontStyle.Italic, lineHeight = 30.sp),
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(bottom = 12.dp),
                )
                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(bottom = 24.dp),
                )
                androidx.compose.foundation.layout.Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.End),
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    PhotoWidgetButton(
                        text = dismissText,
                        onClick = onDismiss,
                        variant = ButtonVariant.Secondary,
                        size = ButtonSize.Medium,
                    )
                    PhotoWidgetButton(
                        text = confirmText,
                        onClick = onConfirm,
                        variant = ButtonVariant.Primary,
                        size = ButtonSize.Medium,
                    )
                }
            }
        }
    }
}
