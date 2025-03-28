package org.example.kmp_shop.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import org.example.kmp_shop.domain.repository.ShoppingRepository
import org.example.kmp_shop.utils.NetworkResult

class HomeViewModel(
    private val repository: ShoppingRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeScreenState())
    val uiState = _uiState.asStateFlow()

    init {
        getAllProducts()
    }

    fun onEvent(event: HomeEvent) = when (event) {
        is HomeEvent.Test -> {}
    }

    private fun getAllProducts() = repository.getAllProducts()
        .onEach { result ->
            when (result) {
                is NetworkResult.Error -> {
                    _uiState.update { oldState ->
                        oldState.copy(errorMessage = result.message, loading = false)
                    }
                }

                is NetworkResult.Loading -> {
                    _uiState.update { oldState ->
                        oldState.copy(loading = true)
                    }
                }

                is NetworkResult.Success -> {
                    _uiState.update { oldState ->
                        oldState.copy(loading = false, data = result.data)
                    }
                }
            }
        }.launchIn(viewModelScope)
}