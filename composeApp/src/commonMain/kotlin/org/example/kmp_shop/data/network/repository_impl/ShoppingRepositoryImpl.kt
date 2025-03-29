package org.example.kmp_shop.data.network.repository_impl

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.example.kmp_shop.data.network.dto.ProductDTO
import org.example.kmp_shop.data.mapper.toModel
import org.example.kmp_shop.domain.model.Product
import org.example.kmp_shop.domain.repository.ShoppingRepository

private const val BASE_URL = "https://fakestoreapi.com"

class ShoppingRepositoryImpl(
    private val httpClient: HttpClient
) : ShoppingRepository {

    override val allProducts: Flow<List<Product>> = flow {
        val result = httpClient.get("$BASE_URL/products")
            .body<List<ProductDTO>>()
            .map { it.toModel() }
        emit(result)
    }

    override fun getProductDetail(productId: Int): Flow<Product> = flow {
        val result = httpClient.get("$BASE_URL/products/$productId")
            .body<ProductDTO>()
            .toModel()
        emit(result)
    }
}