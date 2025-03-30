package org.example.kmp_shop.navigation

import kotlinx.serialization.Serializable

sealed interface MainGraph {

    @Serializable
    data object HomeRoute : MainGraph

    @Serializable
    data class ProductDetailRoute(
        val productId: Int,
        val productTitle: String
    ) : MainGraph

    @Serializable
    data object CartRoute : MainGraph
}