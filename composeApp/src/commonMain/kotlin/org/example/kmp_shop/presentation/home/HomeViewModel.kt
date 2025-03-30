package org.example.kmp_shop.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import org.example.kmp_shop.domain.repository.ProductRepository

class HomeViewModel(
    private val repository: ProductRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeScreenState())
    val uiState = _uiState.asStateFlow()

    init {
        getAllProducts()
    }

    private fun getAllProducts() {
        repository.allProducts
            .onStart {
                _uiState.update { it.copy(loading = true) }
            }
            .catch { error ->
                _uiState.update { oldState ->
                    oldState.copy(
                        loading = false,
                        errorMessage = error.message.toString()
                    )
                }
            }
            .onEach { result ->
                _uiState.update { oldState ->
                    oldState.copy(loading = false, productList = result)
                }
            }.launchIn(viewModelScope)
    }
}