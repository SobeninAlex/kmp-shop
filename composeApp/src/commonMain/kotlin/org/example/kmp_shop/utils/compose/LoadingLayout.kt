package org.example.kmp_shop.utils.compose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.delay

@Composable
fun LoadingLayout(
    modifier: Modifier = Modifier,
    loading: Boolean,
    animationMills: Long = 0,
    loadingContent: @Composable BoxScope.() -> Unit = {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            DotsLoadingIndicator()
        }
    },
    content: @Composable BoxScope.() -> Unit
) {
    var animationStarted by remember { mutableStateOf(false) }
    val showLoading = loading || animationStarted

    LaunchedEffect(showLoading) {
        animationStarted = loading
        delay(animationMills)
        animationStarted = false
    }

    Box(
        modifier = modifier
    ) {
        AnimatedVisibility(
            visible = !showLoading,
            enter = fadeIn(tween(500)),
            exit = fadeOut(tween(500))
        ) {
            Box {
                content()
            }
        }

        AnimatedVisibility(
            visible = showLoading,
            enter = fadeIn(tween(500)),
            exit = fadeOut(tween(500))
        ) {
            Box {
                loadingContent()
            }
        }
    }
}