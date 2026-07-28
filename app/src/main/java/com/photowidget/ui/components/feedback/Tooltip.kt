package com.photowidget.ui.components.feedback

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.photowidget.ui.theme.RadiusSm
import com.photowidget.ui.theme.SurfaceInverse
import com.photowidget.ui.theme.TextInverse

/** Design-system Tooltip (feedback/Tooltip.jsx) on top of M3's [TooltipBox]/[PlainTooltip], recolored ink-on-cream. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoWidgetTooltip(
    text: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    TooltipBox(
        positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider(),
        tooltip = {
            PlainTooltip(containerColor = SurfaceInverse, contentColor = TextInverse, shape = RoundedCornerShape(RadiusSm)) {
                Text(text = text, style = MaterialTheme.typography.labelSmall)
            }
        },
        state = rememberTooltipState(),
        modifier = modifier,
    ) {
        content()
    }
}
