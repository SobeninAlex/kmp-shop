package org.example.kmp_shop.presentation.home

import org.example.kmp_shop.domain.model.Product

data class HomeScreenState(
    val loading: Boolean = false,
    val productList: List<Product> = emptyList(),
    val errorMessage: String = ""
)