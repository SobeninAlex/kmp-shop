package org.example.kmp_shop.presentation.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.kmp_shop.domain.model.Product
import org.example.kmp_shop.domain.repository.ProductRepository

class ProductDetailViewModel(
    private val repository: ProductRepository,
    private val productId: Int,
) : ViewModel() {

    private val _screenState = MutableStateFlow(ProductDetailScreenState())
    val screenState = _screenState.asStateFlow()

    private var _event = Channel<ProductDetailEvent>()
    val event = _event.receiveAsFlow()

    init {
        getProductDetail()
    }

    fun onEvent(event: ProductDetailEvent) {
        when(event) {
            is ProductDetailEvent.OnClick.AddProductToCart -> addProductToCart(event.product)
            else -> {}
        }
    }

    private fun addProductToCart(product: Product) {
        viewModelScope.launch {
            runCatching {
                repository.addProductToCart(product)
            }.onSuccess {
                _event.send(ProductDetailEvent.Snackbar.ProductAddedToCartSuccess)
            }.onFailure { error ->
                _event.send(ProductDetailEvent.Snackbar.ProductAddedFailure(error.message.toString()))
            }
        }
    }

    private fun getProductDetail() {
        repository.getProductDetail(productId)
            .onStart {
                _screenState.update { it.copy(loading = true) }
            }
            .catch { error ->
                _screenState.update { oldState ->
                    oldState.copy(
                        loading = false,
                        errorMessage = error.message.toString()
                    )
                }
            }
            .onEach { result ->
                _screenState.update { oldState ->
                    oldState.copy(
                        loading = false,
                        product = result
                    )
                }
            }.launchIn(viewModelScope)
    }
}