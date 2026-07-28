package com.photowidget.ui.components.controls

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.East
import androidx.compose.material.icons.rounded.North
import androidx.compose.material.icons.rounded.NorthEast
import androidx.compose.material.icons.rounded.NorthWest
import androidx.compose.material.icons.rounded.RadioButtonChecked
import androidx.compose.material.icons.rounded.South
import androidx.compose.material.icons.rounded.SouthEast
import androidx.compose.material.icons.rounded.SouthWest
import androidx.compose.material.icons.rounded.West
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.photowidget.R
import com.photowidget.data.ImageAlignment
import com.photowidget.ui.theme.RadiusMd

private val grid = listOf(
    listOf(ImageAlignment.TOP_LEFT, ImageAlignment.TOP, ImageAlignment.TOP_RIGHT),
    listOf(ImageAlignment.LEFT, ImageAlignment.CENTER, ImageAlignment.RIGHT),
    listOf(ImageAlignment.BOTTOM_LEFT, ImageAlignment.BOTTOM, ImageAlignment.BOTTOM_RIGHT),
)

private fun iconFor(alignment: ImageAlignment): ImageVector = when (alignment) {
    ImageAlignment.TOP_LEFT -> Icons.Rounded.NorthWest
    ImageAlignment.TOP -> Icons.Rounded.North
    ImageAlignment.TOP_RIGHT -> Icons.Rounded.NorthEast
    ImageAlignment.LEFT -> Icons.Rounded.West
    ImageAlignment.CENTER -> Icons.Rounded.RadioButtonChecked
    ImageAlignment.RIGHT -> Icons.Rounded.East
    ImageAlignment.BOTTOM_LEFT -> Icons.Rounded.SouthWest
    ImageAlignment.BOTTOM -> Icons.Rounded.South
    ImageAlignment.BOTTOM_RIGHT -> Icons.Rounded.SouthEast
}

@Composable
private fun labelFor(alignment: ImageAlignment): String = when (alignment) {
    ImageAlignment.CENTER -> stringResource(R.string.alignment_center)
    ImageAlignment.TOP -> stringResource(R.string.alignment_top)
    ImageAlignment.BOTTOM -> stringResource(R.string.alignment_bottom)
    ImageAlignment.LEFT -> stringResource(R.string.alignment_left)
    ImageAlignment.RIGHT -> stringResource(R.string.alignment_right)
    ImageAlignment.TOP_LEFT -> stringResource(R.string.alignment_top_left)
    ImageAlignment.TOP_RIGHT -> stringResource(R.string.alignment_top_right)
    ImageAlignment.BOTTOM_LEFT -> stringResource(R.string.alignment_bottom_left)
    ImageAlignment.BOTTOM_RIGHT -> stringResource(R.string.alignment_bottom_right)
}

/** Design-system AlignmentPad (controls/AlignmentPad.jsx): 3x3 directional grid over [ImageAlignment]. */
@Composable
fun PhotoWidgetAlignmentPad(
    value: ImageAlignment,
    onValueChange: (ImageAlignment) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(RadiusMd))
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        grid.forEach { row ->
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                row.forEach { alignment ->
                    val selected = alignment == value
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .background(
                                if (selected) MaterialTheme.colorScheme.primary else androidx.compose.ui.graphics.Color.Transparent,
                                RoundedCornerShape(8.dp),
                            )
                            .clickable { onValueChange(alignment) },
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(
                            imageVector = iconFor(alignment),
                            contentDescription = labelFor(alignment),
                            tint = if (selected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(if (alignment == ImageAlignment.CENTER) 10.dp else 18.dp),
                        )
                    }
                }
            }
        }
    }
}
