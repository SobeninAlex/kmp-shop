package org.example.kmp_shop.domain.repository

import kotlinx.coroutines.flow.Flow
import org.example.kmp_shop.domain.entity.Product
import org.example.kmp_shop.utils.NetworkResult

interface ShoppingRepository {

    fun getAllProducts(): Flow<NetworkResult<List<Product>>>

}