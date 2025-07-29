package com.example.meliproducts.ui.productdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.meliproducts.data.model.Category
import com.example.meliproducts.data.model.ProductDescription
import com.example.meliproducts.data.model.ProductDetail
import com.example.meliproducts.data.repository.ProductRepository
import com.example.meliproducts.data.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class ProductDetailUiState {
    data object Idle : ProductDetailUiState()
    data object Loading : ProductDetailUiState()
    data class Success(
        val detail: ProductDetail,
        val description: ProductDescription?,
        val category: Category?
    ) : ProductDetailUiState()

    data class Error(val message: String) : ProductDetailUiState()
}

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<ProductDetailUiState>(ProductDetailUiState.Idle)
    val uiState: StateFlow<ProductDetailUiState> = _uiState.asStateFlow()

    fun loadProduct(id: String, categoryId: String) {
        viewModelScope.launch {
            _uiState.value = ProductDetailUiState.Loading

            val result = repository.getProductDetail(id)

            if (result is Result.Success) {
                val detail = result.data
                val descriptionResult = repository.getProductDescription(id)
                val categoryResult = repository.getProductCategory(categoryId)
                val description = (descriptionResult as? Result.Success)?.data
                val category = (categoryResult as? Result.Success)?.data
                _uiState.value = ProductDetailUiState.Success(detail, description, category)
            } else if (result is Result.Error) {
                _uiState.value = ProductDetailUiState.Error(
                    result.exception.message ?: "Erro ao carregar produto"
                )
            }
        }
    }
}
