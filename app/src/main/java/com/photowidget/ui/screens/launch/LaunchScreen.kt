package com.photowidget.ui.screens.launch

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import com.photowidget.R
import com.photowidget.ui.components.feedback.PhotoWidgetProgressIndicator
import com.photowidget.ui.theme.Cream50
import com.photowidget.ui.theme.PhotoWidgetElevation
import com.photowidget.ui.theme.Sage300
import com.photowidget.ui.theme.Sage700
import com.photowidget.ui.theme.Terracotta300
import com.photowidget.ui.theme.Terracotta700
import com.photowidget.ui.theme.photoWidgetShadow
import kotlinx.coroutines.delay

private const val SplashDurationMillis = 900L

/**
 * Real cold-start splash (per user decision, not a decorative-only screen): shown once at launch
 * via [Routes.Launch], auto-advances to Home/Widget Settings — see "Launch Screen.dc.html".
 */
@Composable
fun LaunchScreen(onFinished: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(SplashDurationMillis)
        onFinished()
    }

    val dark = isSystemInDarkTheme()
    // Exact radial-gradient stops from "Launch Screen.dc.html" (`splashStyle`), not a flat fill.
    val gradientStops = if (dark) {
        arrayOf(0f to Color(0xFF2B2823), 0.6f to Color(0xFF211F1B), 1f to Color(0xFF18160F))
    } else {
        arrayOf(0f to Color(0xFFF9F4EC), 0.6f to Color(0xFFEFE6D6), 1f to Color(0xFFE6DAC3))
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .drawBehind {
                val gradientCenter = Offset(size.width * 0.5f, size.height * 0.38f)
                val radius = listOf(
                    Offset(0f, 0f),
                    Offset(size.width, 0f),
                    Offset(0f, size.height),
                    Offset(size.width, size.height),
                ).maxOf { (it - gradientCenter).getDistance() }
                drawRect(
                    brush = Brush.radialGradient(colorStops = gradientStops, center = gradientCenter, radius = radius),
                )
            },
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .width(200.dp)
                    .aspectRatio(200f / 244f)
                    .rotate(-4f)
                    .photoWidgetShadow(PhotoWidgetElevation.lg, RoundedCornerShape(8.dp))
                    .background(Cream50, RoundedCornerShape(8.dp))
                    .padding(start = 14.dp, top = 14.dp, end = 14.dp, bottom = 40.dp),
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.linearGradient(if (dark) listOf(Terracotta700, Sage700) else listOf(Terracotta300, Sage300)),
                            RoundedCornerShape(4.dp),
                        ),
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(top = 44.dp),
            ) {
                Text(
                    text = stringResource(R.string.app_name),
                    style = MaterialTheme.typography.displayMedium.copy(fontStyle = FontStyle.Italic),
                    color = MaterialTheme.colorScheme.onBackground,
                )
                Text(
                    text = stringResource(R.string.launch_subtitle),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 10.dp),
                )
                PhotoWidgetProgressIndicator(
                    indeterminate = true,
                    modifier = Modifier
                        .width(64.dp)
                        .padding(top = 22.dp),
                )
            }
        }

        Text(
            text = stringResource(R.string.launch_byline),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 40.dp),
        )
    }
}
