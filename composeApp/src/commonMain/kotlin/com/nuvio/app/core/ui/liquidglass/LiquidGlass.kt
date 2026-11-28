package com.nuvio.app.core.ui.liquidglass

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect

/**
 * Reusable Frost View glass material.
 *
 * Place a Haze source behind this surface in the screen content for live background blur.
 * The layered tint and specular edge remain as a graceful fallback when blur is unavailable.
 */
@Composable
fun LiquidGlassSurface(
    modifier: Modifier = Modifier,
    hazeState: HazeState? = null,
    shape: Shape = RoundedCornerShape(28.dp),
    tint: Color = Color(0xFFB9DFFF),
    tintAlpha: Float = 0.075f,
    blurRadius: Dp = 28.dp,
    elevation: Dp = 16.dp,
    content: @Composable BoxScope.() -> Unit,
) {
    Box(
        modifier = modifier
            .shadow(elevation, shape, clip = false)
            .clip(shape)
            .then(
                if (hazeState != null) Modifier.hazeEffect(state = hazeState) {
                    this.blurRadius = blurRadius
                    backgroundColor = Color(0xFF101923)
                    tints = listOf(HazeTint(tint.copy(alpha = tintAlpha)))
                    noiseFactor = 0.035f
                } else Modifier
            )
            .background(
                Brush.linearGradient(
                    0f to Color.White.copy(alpha = 0.105f),
                    0.42f to tint.copy(alpha = tintAlpha),
                    1f to Color(0xFF07111D).copy(alpha = 0.58f),
                )
            )
            .border(
                width = 1.dp,
                brush = Brush.linearGradient(
                    listOf(
                        Color.White.copy(alpha = 0.42f),
                        tint.copy(alpha = 0.17f),
                        Color.White.copy(alpha = 0.055f),
                    )
                ),
                shape = shape,
            ),
        content = content,
    )
}

/** A full-width glass card with consistent internal spacing. */
@Composable
fun LiquidGlassCard(
    modifier: Modifier = Modifier,
    hazeState: HazeState? = null,
    shape: Shape = RoundedCornerShape(28.dp),
    content: @Composable BoxScope.() -> Unit,
) {
    LiquidGlassSurface(
        modifier = modifier.fillMaxWidth(),
        hazeState = hazeState,
        shape = shape,
        content = content,
    )
}
