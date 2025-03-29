package org.example.kmp_shop.domain.repository

import kotlinx.coroutines.flow.Flow
import org.example.kmp_shop.domain.model.Product

interface ShoppingRepository {

    val allProducts: Flow<List<Product>>

    fun getProductDetail(productId: Int): Flow<Product>

}