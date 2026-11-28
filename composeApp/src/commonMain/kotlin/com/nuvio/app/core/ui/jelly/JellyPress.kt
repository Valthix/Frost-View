package com.nuvio.app.core.ui.jelly

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.graphicsLayer

/**
 * Clickable interaction with a damped, elastic compression and settle.
 * Use instead of clickable on buttons, chips and other custom interactive surfaces.
 */
fun Modifier.jellyClickable(
    enabled: Boolean = true,
    pressedScale: Float = 0.965f,
    onClick: () -> Unit,
): Modifier = composed {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (enabled && isPressed) pressedScale else 1f,
        animationSpec = spring(
            dampingRatio = 0.48f,
            stiffness = Spring.StiffnessMediumLow,
            visibilityThreshold = 0.001f,
        ),
        label = "frost_jelly_press",
    )
    this.graphicsLayer {
        scaleX = scale
        scaleY = scale
    }.clickable(
        enabled = enabled,
        interactionSource = interactionSource,
        indication = null,
        onClick = onClick,
    )
}
