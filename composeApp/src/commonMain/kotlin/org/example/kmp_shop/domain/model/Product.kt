package org.example.kmp_shop.domain.model

data class Product(
    val id: Int = 1,
    val title: String = "",
    val price: Double = 0.0,
    val description: String = "",
    val category: String = "",
    val image: String = "",
    val rating: Rating = Rating()
)