package com.example.meliproducts.ui.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.meliproducts.ui.components.SearchTopAppBar

@Composable
fun SearchScreen(
    onSearch: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel(),
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            SearchTopAppBar(
                query = viewModel.query,
                onValueChange = { viewModel.updateQuery(it) },
                onSearch = {
                    onSearch(viewModel.query)
                    viewModel.updateQuery("")
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            // TODO: mostrar produtos vistos recentemente, sugestões etc.
        }
    }
}
