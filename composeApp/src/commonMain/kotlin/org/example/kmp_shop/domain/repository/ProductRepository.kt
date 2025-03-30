package org.example.kmp_shop.domain.repository

import kotlinx.coroutines.flow.Flow
import org.example.kmp_shop.domain.model.Product

interface ProductRepository {

    val allProducts: Flow<List<Product>>

    fun getProductDetail(productId: Int): Flow<Product>

    fun getProductsFromCart(): Flow<List<Product>>

    suspend fun addProductToCart(product: Product)

    suspend fun removeProduct(product: Product)
}