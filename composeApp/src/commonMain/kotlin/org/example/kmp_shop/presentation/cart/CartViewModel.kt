package org.example.kmp_shop.presentation.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.kmp_shop.domain.model.Product
import org.example.kmp_shop.domain.repository.ProductRepository

class CartViewModel(
    private val repository: ProductRepository
) : ViewModel() {

    private val _screenState = MutableStateFlow(CartScreenState())
    val screenState = _screenState.asStateFlow()

    private val _event = Channel<CartEvent>()
    val event = _event.receiveAsFlow()

    init {
        getProductsFromCart()
    }

    fun onEvent(event: CartEvent) = when (event) {
        is CartEvent.OnClick.RemoveProduct -> removeProduct(event.product)
        else -> {}
    }

    private fun getProductsFromCart() {
        repository.getProductsFromCart()
            .onStart {
                _screenState.update { it.copy(loading = true) }
            }
            .catch { error ->
                _screenState.update { oldState ->
                    oldState.copy(
                        loading = true,
                        errorMessage = error.message.toString()
                    )
                }
            }
            .onEach { result ->
                _screenState.update { oldState ->
                    oldState.copy(
                        loading = false,
                        products = result
                    )
                }
            }
            .shareIn(
                scope = viewModelScope,
                started = SharingStarted.Lazily,
                replay = 1
            )
            .launchIn(viewModelScope)
    }

    private fun removeProduct(product: Product) {
        viewModelScope.launch {
            runCatching {
                repository.removeProduct(product)
            }.onSuccess {
                _event.send(CartEvent.Snackbar.ProductRemovedSuccess)
            }.onFailure { error ->
                _event.send(CartEvent.Snackbar.ProductRemoveFailure(errorMsg = error.message.toString()))
            }
        }
    }
}