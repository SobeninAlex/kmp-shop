package org.example.kmp_shop

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import org.example.kmp_shop.presentation.home.HomeScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        HomeScreen()
    }
}