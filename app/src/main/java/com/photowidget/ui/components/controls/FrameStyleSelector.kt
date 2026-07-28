package com.photowidget.ui.components.controls

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.photowidget.R
import com.photowidget.ui.theme.RadiusLg

/** The 4 swatch values from controls/FrameStyleSelector.jsx. Callers map this to WidgetShape+FrameStyle. */
enum class FrameStyleOption { None, Rounded, Circle, Polaroid }

private val swatchGradient = Brush.linearGradient(listOf(Color(0xFFCAA27A), Color(0xFF8A6A52)))

/** Design-system FrameStyleSelector (controls/FrameStyleSelector.jsx): 4 visual shape swatches. */
@Composable
fun PhotoWidgetFrameStyleSelector(
    value: FrameStyleOption,
    onValueChange: (FrameStyleOption) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(14.dp)) {
        FrameStyleOption.entries.forEach { option ->
            val selected = option == value
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.clickable { onValueChange(option) },
            ) {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .then(
                            if (selected) {
                                Modifier.background(MaterialTheme.colorScheme.primary, swatchOuterShape(option)).padding(2.dp)
                            } else {
                                Modifier
                            },
                        ),
                    contentAlignment = Alignment.Center,
                ) {
                    when (option) {
                        FrameStyleOption.Polaroid -> Box(
                            modifier = Modifier
                                .size(52.dp)
                                .background(Color.White, RoundedCornerShape(4.dp))
                                .padding(6.dp),
                        ) {
                            Box(modifier = Modifier.background(swatchGradient, RoundedCornerShape(2.dp)).clip(RoundedCornerShape(2.dp)).size(40.dp))
                        }

                        else -> Box(
                            modifier = Modifier
                                .size(52.dp)
                                .clip(swatchInnerShape(option))
                                .background(swatchGradient),
                        )
                    }
                }
                Text(
                    text = labelFor(option),
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal,
                    color = if (selected) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 8.dp),
                )
            }
        }
    }
}

private fun swatchOuterShape(option: FrameStyleOption) = when (option) {
    FrameStyleOption.Circle -> CircleShape
    FrameStyleOption.Polaroid -> RoundedCornerShape(4.dp)
    else -> RoundedCornerShape(RadiusLg)
}

private fun swatchInnerShape(option: FrameStyleOption) = when (option) {
    FrameStyleOption.None -> RoundedCornerShape(0.dp)
    FrameStyleOption.Rounded -> RoundedCornerShape(RadiusLg)
    FrameStyleOption.Circle -> CircleShape
    FrameStyleOption.Polaroid -> RoundedCornerShape(2.dp)
}

@Composable
private fun labelFor(option: FrameStyleOption): String = when (option) {
    FrameStyleOption.None -> stringResource(R.string.style_none)
    FrameStyleOption.Rounded -> stringResource(R.string.style_rounded)
    FrameStyleOption.Circle -> stringResource(R.string.style_circle)
    FrameStyleOption.Polaroid -> stringResource(R.string.style_polaroid)
}
