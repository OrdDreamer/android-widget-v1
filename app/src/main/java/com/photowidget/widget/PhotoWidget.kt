package com.photowidget.widget

import android.appwidget.AppWidgetManager
import android.content.Context
import android.graphics.Bitmap
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.SizeMode
import androidx.compose.ui.unit.DpSize
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.fillMaxSize
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import androidx.compose.runtime.Composable
import com.photowidget.PhotoWidgetApp
import com.photowidget.data.WidgetConfig
import com.photowidget.data.WidgetShape
import kotlin.math.min

class PhotoWidget : GlanceAppWidget() {

    override val sizeMode: SizeMode = SizeMode.Responsive(
        setOf(
            DpSize(110.dp, 110.dp),
            DpSize(180.dp, 110.dp),
            DpSize(110.dp, 180.dp),
            DpSize(250.dp, 250.dp),
            DpSize(320.dp, 180.dp),
        ),
    )

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val app = context.applicationContext as PhotoWidgetApp
        val appWidgetId = GlanceAppWidgetManager(context).getAppWidgetId(id)
        val config = app.widgetConfigRepository.getConfig(appWidgetId)

        val options = AppWidgetManager.getInstance(context).getAppWidgetOptions(appWidgetId)
        val minWidthDp = options.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH, 110)
        val minHeightDp = options.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_HEIGHT, 110)
        val density = context.resources.displayMetrics.density
        val widthPx = (minWidthDp * density).toInt().coerceAtLeast(1)
        val heightPx = (minHeightDp * density).toInt().coerceAtLeast(1)
        val bitmap = WidgetImageLoader.loadBitmap(context, config, widthPx, heightPx, density)

        provideContent {
            PhotoWidgetContent(config, bitmap, minWidthDp, minHeightDp)
        }
    }
}

@Composable
private fun PhotoWidgetContent(
    config: WidgetConfig,
    bitmap: Bitmap?,
    minWidthDp: Int,
    minHeightDp: Int,
) {
    val cornerRadiusDp = when (config.shape) {
        WidgetShape.RECTANGLE -> 0
        WidgetShape.ROUNDED_RECT -> config.cornerRadiusDp
        WidgetShape.CIRCLE -> min(minWidthDp, minHeightDp) / 2
    }

    val modifier = GlanceModifier
        .fillMaxSize()
        .then(
            if (cornerRadiusDp > 0) {
                GlanceModifier.cornerRadius(cornerRadiusDp.dp)
            } else {
                GlanceModifier
            },
        )

    if (bitmap != null) {
        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center,
        ) {
            Image(
                provider = ImageProvider(bitmap),
                contentDescription = null,
                modifier = GlanceModifier.fillMaxSize(),
            )
        }
    } else {
        Box(
            modifier = modifier
                .background(ColorProvider(android.graphics.Color.parseColor("#E0E0E0"))),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "Фото не обрано",
                style = TextStyle(color = ColorProvider(android.graphics.Color.DKGRAY)),
            )
        }
    }
}
