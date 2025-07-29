@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.meliproducts.ui.productdetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.meliproducts.R
import com.example.meliproducts.data.model.ProductDescription
import com.example.meliproducts.ui.components.EmptyView
import com.example.meliproducts.ui.components.ErrorView
import com.example.meliproducts.ui.components.LoadingView
import java.text.NumberFormat

@Composable
fun ProductDetailScreen(
    uiState: State<ProductDetailUiState>,
    onBack: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back_button)
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            when (val state = uiState.value) {
                is ProductDetailUiState.Loading -> LoadingView()
                is ProductDetailUiState.Error -> ErrorView(state.message)
                is ProductDetailUiState.Success -> {
                    val detail = state.detail
                    val padding1 = Modifier.padding(horizontal = 16.dp)

                    LazyColumn(Modifier.fillMaxSize()) {
                        item {
                            Spacer(Modifier.height(12.dp))
                            Text(
                                detail.title,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                modifier = padding1
                            )
                            Spacer(Modifier.height(6.dp))

                            val carouselItems = detail.pictures.map { picture ->
                                CarouselItem(url = picture.url)
                            }
                            ImageCarousel(items = carouselItems)

                            Spacer(Modifier.height(12.dp))
                            ProductPriceWidget(
                                basePrice = detail.basePrice,
                                price = detail.price,
                                modifier = padding1
                            )
                            Spacer(Modifier.height(8.dp))
                            if (detail.shipping.freeShipping)
                                Text(
                                    text = stringResource(R.string.free_shipping),
                                    style = MaterialTheme.typography.bodyMedium,
                                    modifier = padding1
                                )
                            Spacer(Modifier.height(8.dp))
                            detail.warranty?.let {
                                Text(
                                    text = it,
                                    style = MaterialTheme.typography.bodyMedium,
                                    modifier = padding1
                                )
                            }
                            Spacer(Modifier.height(12.dp))
                        }
                        item {
                            ProductAttributesWidget(
                                attributes = detail.attributes,
                                modifier = Modifier
                                    .padding(horizontal = 16.dp, vertical = 8.dp)
                            )
                        }
                        item {
                            if (state.description != null &&
                                state.description.plainText.isNotBlank() &&
                                state.description.plainText != "."
                            ) {
                                DescriptionWidget(
                                    productDescription = state.description,
                                    modifier = Modifier
                                        .padding(horizontal = 16.dp, vertical = 8.dp)
                                )
                            }
                        }
                    }
                }
                ProductDetailUiState.Idle -> EmptyView("Selecione um produto")
            }
        }
    }
}


@Composable
fun ProductPriceWidget(
    basePrice: Double,
    price: Double,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        if (basePrice < price) {
            Text(
                text = NumberFormat.getCurrencyInstance()
                    .format(basePrice),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.labelSmall.copy(
                    textDecoration = TextDecoration.LineThrough
                ),
            )
        }
        Text(
            text = NumberFormat.getCurrencyInstance()
                .format(price),
            style = MaterialTheme.typography.displayMedium.copy(
                fontWeight = FontWeight.Bold
            )
        )
    }
}


@Composable
fun DescriptionWidget(
    productDescription: ProductDescription,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        Spacer(Modifier.height(16.dp))
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth(),
            thickness = 0.5.dp,
        )
        Spacer(Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.description),
            style = MaterialTheme.typography.headlineMedium,
        )
        Spacer(Modifier.height(16.dp))
        Text(
            productDescription.plainText,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(vertical = 4.dp)
        )
        Spacer(Modifier.height(16.dp))
    }
}