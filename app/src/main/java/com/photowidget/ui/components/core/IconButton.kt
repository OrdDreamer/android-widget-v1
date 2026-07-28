package com.photowidget.ui.components.core

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.photowidget.ui.theme.accentPrimaryHover
import com.photowidget.ui.theme.photoWidgetShadow
import com.photowidget.ui.theme.surfaceRaised

enum class IconButtonVariant { Ghost, Filled, Accent }
enum class IconButtonSize { Small, Medium, Large }

private fun sizes(size: IconButtonSize): Pair<Dp, Dp> = when (size) {
    IconButtonSize.Small -> 32.dp to 16.dp
    IconButtonSize.Medium -> 40.dp to 20.dp
    IconButtonSize.Large -> 48.dp to 24.dp
}

/** Design-system IconButton (core/IconButton.jsx): circular, ghost/filled/accent, sizes sm/md/lg. */
@Composable
fun PhotoWidgetIconButton(
    icon: ImageVector,
    contentDescription: String?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    variant: IconButtonVariant = IconButtonVariant.Ghost,
    size: IconButtonSize = IconButtonSize.Medium,
    enabled: Boolean = true,
) {
    val (boxSize, iconSize) = sizes(size)
    val interactionSource = remember { MutableInteractionSource() }

    val backgroundModifier = when (variant) {
        IconButtonVariant.Ghost -> Modifier
        IconButtonVariant.Filled -> Modifier
            .photoWidgetShadow(elevation = com.photowidget.ui.theme.PhotoWidgetElevation.sm, shape = CircleShape)
            .background(surfaceRaised(), CircleShape)
        IconButtonVariant.Accent -> Modifier.background(MaterialTheme.colorScheme.primaryContainer, CircleShape)
    }

    val contentColor = when (variant) {
        IconButtonVariant.Ghost, IconButtonVariant.Filled -> MaterialTheme.colorScheme.onSurface
        IconButtonVariant.Accent -> accentPrimaryHover()
    }

    IconButton(
        onClick = onClick,
        modifier = modifier.size(boxSize).then(backgroundModifier),
        enabled = enabled,
        interactionSource = interactionSource,
        colors = IconButtonDefaults.iconButtonColors(contentColor = contentColor, disabledContentColor = contentColor.copy(alpha = 0.4f)),
    ) {
        Icon(imageVector = icon, contentDescription = contentDescription, tint = contentColor, modifier = Modifier.size(iconSize))
    }
}
