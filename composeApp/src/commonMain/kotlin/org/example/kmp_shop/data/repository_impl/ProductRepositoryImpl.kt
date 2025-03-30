package org.example.kmp_shop.data.repository_impl

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.example.kmp_shop.data.database.dao.ProductDAO
import org.example.kmp_shop.data.mapper.toEntity
import org.example.kmp_shop.data.network.dto.ProductDTO
import org.example.kmp_shop.data.mapper.toModel
import org.example.kmp_shop.domain.model.Product
import org.example.kmp_shop.domain.repository.ProductRepository

private const val BASE_URL = "https://fakestoreapi.com"

class ProductRepositoryImpl(
    private val httpClient: HttpClient,
    private val productDAO: ProductDAO
) : ProductRepository {

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

    override fun getProductsFromCart(): Flow<List<Product>> = flow {
        productDAO.getProductsFromCart().collect { list ->
            emit(list.map { it.toModel() })
        }
    }

    override suspend fun addProductToCart(product: Product) {
        productDAO.upsert(product.toEntity())
    }

    override suspend fun removeProduct(product: Product) {
        productDAO.delete(product.toEntity())
    }
}