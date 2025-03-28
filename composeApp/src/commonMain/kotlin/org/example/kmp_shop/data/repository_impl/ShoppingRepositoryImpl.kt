package org.example.kmp_shop.data.repository_impl

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.example.kmp_shop.data.dto.ProductDTO
import org.example.kmp_shop.data.mapper.toEntity
import org.example.kmp_shop.domain.entity.Product
import org.example.kmp_shop.domain.repository.ShoppingRepository
import org.example.kmp_shop.utils.NetworkResult

private const val BASE_URL = "https://fakestoreapi.com"

class ShoppingRepositoryImpl(
    private val httpClient: HttpClient
) : ShoppingRepository {

    override fun getAllProducts(): Flow<NetworkResult<List<Product>>>  = flow {
        emit(NetworkResult.Loading())
        runCatching {
            httpClient.get("$BASE_URL/products")
                .body<List<ProductDTO>>()
                .map { it.toEntity() }
        }.onSuccess { result ->
            emit(NetworkResult.Success(data = result))
        }.onFailure { error ->
            emit(NetworkResult.Error(message = error.message.toString()))
        }
    }
}