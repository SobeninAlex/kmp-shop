package org.example.kmp_shop.presentation.product

import org.example.kmp_shop.domain.model.Product

data class ProductDetailScreenState(
    val loading: Boolean = false,
    val product: Product = Product(),
    val errorMessage: String = "",
)