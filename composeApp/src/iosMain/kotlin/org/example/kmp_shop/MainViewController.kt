package org.example.kmp_shop

import androidx.compose.ui.window.ComposeUIViewController
import org.example.kmp_shop.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    App()
}