@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.meliproducts.ui.searchresult

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.meliproducts.R
import com.example.meliproducts.ui.components.EmptyView
import com.example.meliproducts.ui.components.ErrorView
import com.example.meliproducts.ui.components.LoadingView

@Composable
fun SearchResultScreen(
    searchTerm: String,
    onProductClick: (productId: String, categoryId: String) -> Unit,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SearchResultViewModel = hiltViewModel(),
) {
    val state = viewModel.uiState.collectAsState()

    LaunchedEffect(searchTerm) {
        viewModel.search(searchTerm)
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = searchTerm
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                navigationIcon = {
                    IconButton(onClick = navigateUp) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back_button)
                        )
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
        ) {
            when (val uiState = state.value) {
                is SearchResultUiState.Loading -> LoadingView()
                is SearchResultUiState.Error -> ErrorView(uiState.message)
                is SearchResultUiState.Success -> {
                    if (uiState.products.isEmpty()) {
                        EmptyView()
                    } else {
                        LazyColumn(modifier = Modifier.fillMaxSize()) {
                            items(uiState.products.size) { index ->
                                val product = uiState.products[index]
                                ProductListItem(
                                    product = product,
                                    onClick = {
                                        onProductClick(product.id, product.categoryId)
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

