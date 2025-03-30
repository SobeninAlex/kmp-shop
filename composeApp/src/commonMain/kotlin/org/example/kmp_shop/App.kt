package org.example.kmp_shop

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import org.example.kmp_shop.navigation.LocalNavController
import org.example.kmp_shop.navigation.MainGraph
import org.example.kmp_shop.presentation.home.HomeScreen
import org.example.kmp_shop.presentation.product.ProductDetailScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        val navController = rememberNavController()

        CompositionLocalProvider(
            LocalNavController provides navController
        ) {
            NavHost(
                navController = navController,
                startDestination = MainGraph.HomeRoute,
                exitTransition = {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        tween(500)
                    )
                },
                enterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        tween(500)
                    )
                },
                popEnterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        tween(500)
                    )
                },
                popExitTransition = {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        tween(500)
                    )
                }
            ) {
                composable<MainGraph.HomeRoute> {
                    HomeScreen()
                }

                composable<MainGraph.ProductDetailRoute> {
                    val args = it.toRoute<MainGraph.ProductDetailRoute>()
                    ProductDetailScreen(
                        productId = args.productId,
                        productTitle = args.productTitle
                    )
                }

                composable<MainGraph.CartRoute> {

                }
            }
        }
    }
}