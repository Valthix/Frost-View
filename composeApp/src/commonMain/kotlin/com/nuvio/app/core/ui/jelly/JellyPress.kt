package com.nuvio.app.core.ui.jelly

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.graphicsLayer

/**
 * Tactile press feedback: a quick compression followed by a damped, elastic settle.
 * Reusable on buttons, chips, cards and other interactive surfaces.
 */
fun Modifier.jellyPress(
    enabled: Boolean = true,
    pressedScale: Float = 0.965f,
): Modifier = composed {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val target = if (enabled && isPressed) pressedScale else 1f
    val scale by animateFloatAsState(
        targetValue = target,
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
    }
}
