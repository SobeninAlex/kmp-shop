package org.example.kmp_shop.presentation.cart

import org.example.kmp_shop.domain.model.Product

data class CartScreenState(
    val loading: Boolean = false,
    val products: List<Product> = emptyList(),
    val errorMessage: String = ""
)