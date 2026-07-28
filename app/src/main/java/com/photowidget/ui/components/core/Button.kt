package com.photowidget.ui.components.core

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.Button as Material3Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.photowidget.ui.theme.AccentPrimary
import com.photowidget.ui.theme.AccentSecondary
import com.photowidget.ui.theme.Cream50
import com.photowidget.ui.theme.DurationFast
import com.photowidget.ui.theme.RadiusMd
import com.photowidget.ui.theme.calmTween

enum class ButtonVariant { Primary, Secondary, Outline, Ghost }
enum class ButtonSize { Small, Medium, Large }

private data class ButtonSizeSpec(val horizontal: Dp, val vertical: Dp, val height: Dp, val iconSize: Dp)

private fun sizeSpec(size: ButtonSize): ButtonSizeSpec = when (size) {
    ButtonSize.Small -> ButtonSizeSpec(14.dp, 6.dp, 32.dp, 16.dp)
    ButtonSize.Medium -> ButtonSizeSpec(20.dp, 10.dp, 44.dp, 18.dp)
    ButtonSize.Large -> ButtonSizeSpec(26.dp, 13.dp, 52.dp, 20.dp)
}

/**
 * Design-system Button (core/Button.jsx): variant primary/secondary/outline/ghost, size sm/md/lg,
 * fixed 14dp radius, optional leading icon, 0.97x press-scale instead of M3's ripple-only feedback.
 */
@Composable
fun PhotoWidgetButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    variant: ButtonVariant = ButtonVariant.Primary,
    size: ButtonSize = ButtonSize.Medium,
    icon: ImageVector? = null,
    enabled: Boolean = true,
) {
    val spec = sizeSpec(size)
    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.97f else 1f,
        animationSpec = calmTween(DurationFast),
        label = "buttonPressScale",
    )
    val shape = RoundedCornerShape(RadiusMd)
    val contentPadding = PaddingValues(horizontal = spec.horizontal, vertical = spec.vertical)
    val textStyle = when (size) {
        ButtonSize.Small -> MaterialTheme.typography.bodySmall
        ButtonSize.Medium -> MaterialTheme.typography.bodyMedium
        ButtonSize.Large -> MaterialTheme.typography.bodyLarge
    }.copy(fontWeight = FontWeight.SemiBold)

    val content: @Composable () -> Unit = {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (icon != null) {
                Icon(imageVector = icon, contentDescription = null, modifier = Modifier.height(spec.iconSize))
            }
            Text(text = text, style = textStyle, maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
    }

    val buttonModifier = modifier
        .scale(scale)
        .height(spec.height)

    when (variant) {
        ButtonVariant.Primary -> Material3Button(
            onClick = onClick,
            modifier = buttonModifier,
            enabled = enabled,
            shape = shape,
            colors = ButtonDefaults.buttonColors(containerColor = AccentPrimary, contentColor = Cream50),
            contentPadding = contentPadding,
            interactionSource = interactionSource,
        ) { content() }

        ButtonVariant.Secondary -> Material3Button(
            onClick = onClick,
            modifier = buttonModifier,
            enabled = enabled,
            shape = shape,
            colors = ButtonDefaults.buttonColors(containerColor = AccentSecondary, contentColor = Cream50),
            contentPadding = contentPadding,
            interactionSource = interactionSource,
        ) { content() }

        ButtonVariant.Outline -> OutlinedButton(
            onClick = onClick,
            modifier = buttonModifier,
            enabled = enabled,
            shape = shape,
            colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.onSurface),
            border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
            contentPadding = contentPadding,
            interactionSource = interactionSource,
        ) { content() }

        ButtonVariant.Ghost -> TextButton(
            onClick = onClick,
            modifier = buttonModifier,
            enabled = enabled,
            shape = shape,
            colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.onSurface),
            contentPadding = contentPadding,
            interactionSource = interactionSource,
        ) { content() }
    }
}
