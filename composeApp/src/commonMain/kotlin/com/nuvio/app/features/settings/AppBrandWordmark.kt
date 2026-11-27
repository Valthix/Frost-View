package com.nuvio.app.features.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import com.nuvio.app.core.ui.appTheme
import nuvio.composeapp.generated.resources.Res
import nuvio.composeapp.generated.resources.app_brand_name
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ResourceEnvironment
import org.jetbrains.compose.resources.decodeToImageBitmap
import org.jetbrains.compose.resources.getDrawableResourceBytes
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.rememberResourceEnvironment

@Composable
internal fun AppBrandWordmark(
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    icon: AppIconOption? = null,
) {
    Text(
        text = stringResource(Res.string.app_brand_name),
        modifier = modifier.semantics {
            if (contentDescription != null) this.contentDescription = contentDescription
        },
        style = MaterialTheme.typography.headlineSmall,
        color = MaterialTheme.colorScheme.onSurface,
        fontWeight = FontWeight.Bold,
    )
}

@Composable
internal fun PreloadAppBrandWordmark() {
    val resource = rememberAppBrandWordmarkResource(icon = null)
    val environment = rememberResourceEnvironment()
    LaunchedEffect(resource, environment) {
        AppBrandWordmarkCache.load(resource, environment)
    }
}

@Composable
private fun rememberAppBrandWordmarkResource(icon: AppIconOption?): DrawableResource {
    val state by remember {
        AppIconRepository.ensureLoaded()
        AppIconRepository.state
    }.collectAsStateWithLifecycle()
    return icon?.wordmarkResource ?: MaterialTheme.appTheme.wordmarkResource(state.selected)
}

private object AppBrandWordmarkCache {
    private val bitmaps = MutableStateFlow<Map<DrawableResource, ImageBitmap>>(emptyMap())

    operator fun get(resource: DrawableResource): ImageBitmap? = bitmaps.value[resource]

    suspend fun load(resource: DrawableResource, environment: ResourceEnvironment): ImageBitmap {
        get(resource)?.let { return it }
        val bitmap = withContext(Dispatchers.Default) {
            getDrawableResourceBytes(environment, resource).decodeToImageBitmap()
        }
        bitmaps.update { cached -> cached + (resource to bitmap) }
        return bitmap
    }
}
