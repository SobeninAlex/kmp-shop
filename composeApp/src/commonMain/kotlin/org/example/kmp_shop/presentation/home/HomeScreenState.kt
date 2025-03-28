package org.example.kmp_shop.presentation.home

import org.example.kmp_shop.domain.entity.Product

data class HomeScreenState(
    val loading: Boolean = false,
    val data: List<Product> = emptyList(),
    val errorMessage: String = ""
)