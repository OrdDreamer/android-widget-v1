package com.photowidget.ui.components.core

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.photowidget.ui.theme.PhotoWidgetElevation
import com.photowidget.ui.theme.RadiusLg
import com.photowidget.ui.theme.photoWidgetShadow
import com.photowidget.ui.theme.surfaceRaised

enum class CardVariant { Default, Raised, Sunken }
enum class CardPadding { None, Small, Medium, Large }

private fun paddingDp(padding: CardPadding) = when (padding) {
    CardPadding.None -> 0.dp
    CardPadding.Small -> 16.dp
    CardPadding.Medium -> 24.dp
    CardPadding.Large -> 32.dp
}

/** Design-system Card (core/Card.jsx): fixed 20dp radius, default/raised/sunken surface treatment. */
@Composable
fun PhotoWidgetCard(
    modifier: Modifier = Modifier,
    variant: CardVariant = CardVariant.Default,
    padding: CardPadding = CardPadding.Medium,
    content: @Composable () -> Unit,
) {
    val shape = RoundedCornerShape(RadiusLg)
    val background = when (variant) {
        CardVariant.Default -> MaterialTheme.colorScheme.surface
        CardVariant.Raised -> surfaceRaised()
        CardVariant.Sunken -> MaterialTheme.colorScheme.surfaceVariant
    }
    val border = when (variant) {
        CardVariant.Raised -> null
        else -> BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    }
    val shadowModifier = when (variant) {
        CardVariant.Default -> Modifier.photoWidgetShadow(PhotoWidgetElevation.sm, shape)
        CardVariant.Raised -> Modifier.photoWidgetShadow(PhotoWidgetElevation.md, shape)
        CardVariant.Sunken -> Modifier
    }

    Column(
        modifier = modifier
            .then(shadowModifier)
            .background(background, shape)
            .let { if (border != null) it.border(border, shape) else it }
            .padding(PaddingValues(paddingDp(padding))),
    ) {
        content()
    }
}
