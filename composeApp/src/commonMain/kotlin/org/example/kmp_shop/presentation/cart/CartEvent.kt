package org.example.kmp_shop.presentation.cart

import org.example.kmp_shop.domain.model.Product

sealed interface CartEvent {

    data object Initial: CartEvent

    sealed interface Snackbar {
        data object ProductRemovedSuccess : CartEvent
        data class ProductRemoveFailure(val errorMsg: String): CartEvent
    }

    sealed interface OnClick {
        data class RemoveProduct(val product: Product): CartEvent
    }
}