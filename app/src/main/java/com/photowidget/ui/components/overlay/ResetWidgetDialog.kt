package com.photowidget.ui.components.overlay

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.photowidget.R
import com.photowidget.ui.theme.PhotoWidgetTheme

/**
 * Thin [PhotoWidgetDialog] usage for the widget-list "Reset" confirm. `delete_widget_*` strings
 * predate this rebuild but are already semantically Reset copy ("clears photo/settings, widget
 * stays pinned") — reused verbatim rather than duplicated under new keys.
 */
@Composable
fun ResetWidgetDialog(onConfirm: () -> Unit, onDismiss: () -> Unit) {
    PhotoWidgetDialog(
        title = stringResource(R.string.delete_widget_title),
        message = stringResource(R.string.delete_widget_message),
        confirmText = stringResource(R.string.delete_widget_confirm),
        onConfirm = onConfirm,
        dismissText = stringResource(R.string.cancel),
        onDismiss = onDismiss,
    )
}

@PreviewLightDark
@Composable
private fun ResetWidgetDialogPreview() {
    PhotoWidgetTheme {
        ResetWidgetDialog(onConfirm = {}, onDismiss = {})
    }
}
