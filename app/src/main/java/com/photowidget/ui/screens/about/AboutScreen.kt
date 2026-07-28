package com.photowidget.ui.screens.about

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.photowidget.R
import com.photowidget.ui.components.core.ButtonSize
import com.photowidget.ui.components.core.ButtonVariant
import com.photowidget.ui.components.core.CardPadding
import com.photowidget.ui.components.core.CardVariant
import com.photowidget.ui.components.core.PhotoWidgetButton
import com.photowidget.ui.components.core.PhotoWidgetCard
import com.photowidget.ui.photoWidgetSafeAreaPadding
import com.photowidget.ui.screens.common.ScreenHeader
import com.photowidget.ui.theme.Lora

/** "About.dc.html". */
@Composable
fun AboutScreen(onBack: () -> Unit, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val versionName = remember {
        runCatching { context.packageManager.getPackageInfo(context.packageName, 0).versionName }.getOrNull() ?: "1.0"
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .photoWidgetSafeAreaPadding(),
    ) {
        ScreenHeader(title = stringResource(R.string.settings_about), onBack = onBack)

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            androidx.compose.foundation.layout.Box(
                modifier = Modifier
                    .size(88.dp)
                    .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(28.dp)),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = stringResource(R.string.app_name),
                    style = MaterialTheme.typography.bodyMedium.copy(fontStyle = FontStyle.Italic, fontFamily = Lora),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                )
            }

            Text(
                text = stringResource(R.string.about_description),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 16.dp),
            )
            Text(
                text = stringResource(R.string.about_version, versionName),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 2.dp),
            )

            PhotoWidgetCard(
                variant = CardVariant.Default,
                padding = CardPadding.Large,
                modifier = Modifier.fillMaxWidth().padding(top = 24.dp),
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = stringResource(R.string.about_support_title),
                        style = MaterialTheme.typography.titleLarge.copy(fontStyle = FontStyle.Italic, fontFamily = Lora, fontSize = MaterialTheme.typography.bodyLarge.fontSize),
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                    Text(
                        text = stringResource(R.string.about_support_text),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 12.dp, bottom = 16.dp),
                    )
                    PhotoWidgetButton(
                        text = stringResource(R.string.about_support_button),
                        onClick = { openPlayStoreListing(context) },
                        variant = ButtonVariant.Secondary,
                        size = ButtonSize.Medium,
                        icon = Icons.Rounded.Favorite,
                    )
                }
            }

            Text(
                text = stringResource(R.string.launch_byline),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 24.dp),
            )
        }
    }
}

private fun openPlayStoreListing(context: android.content.Context) {
    val packageName = context.packageName
    try {
        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName")))
    } catch (_: ActivityNotFoundException) {
        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$packageName")))
    }
}
