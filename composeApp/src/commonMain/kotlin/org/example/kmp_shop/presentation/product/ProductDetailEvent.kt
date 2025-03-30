package org.example.kmp_shop.presentation.product

import org.example.kmp_shop.domain.model.Product

sealed interface ProductDetailEvent {

    data object Initial: ProductDetailEvent

    sealed interface Snackbar {
        data object ProductAddedToCartSuccess: ProductDetailEvent
        data class ProductAddedFailure(val errMsg: String): ProductDetailEvent
    }

    sealed interface OnClick {
        data class AddProductToCart(val product: Product): ProductDetailEvent
    }
}