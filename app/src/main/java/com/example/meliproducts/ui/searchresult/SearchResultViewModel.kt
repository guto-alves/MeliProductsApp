package com.example.meliproducts.ui.searchresult

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.meliproducts.data.model.Product
import com.example.meliproducts.data.repository.ProductRepository
import com.example.meliproducts.data.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class SearchResultUiState {
    data object Loading : SearchResultUiState()
    data class Success(val products: List<Product>) : SearchResultUiState()
    data class Error(val message: String) : SearchResultUiState()
}

@HiltViewModel
class SearchResultViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<SearchResultUiState>(SearchResultUiState.Loading)
    val uiState: StateFlow<SearchResultUiState> = _uiState.asStateFlow()

    fun search(term: String) {
        viewModelScope.launch {
            _uiState.value = SearchResultUiState.Loading

            val result = repository.searchProducts(term.lowercase())

            if (result is Result.Success) {
                _uiState.value = SearchResultUiState.Success(result.data)
            }
            if (result is Result.Error) {
                _uiState.value =
                    SearchResultUiState.Error(result.exception.message ?: "Unknown error")
            }
        }
    }
}

