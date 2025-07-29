package com.example.meliproducts.ui.searchresult

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.meliproducts.data.model.Product
import com.example.meliproducts.data.model.Shipping
import com.example.meliproducts.ui.theme.MeliProductsTheme
import java.text.NumberFormat

@Composable
fun ProductListItem(
    product: Product,
    onClick: () -> Unit
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp, horizontal = 8.dp)
                .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = product.thumbnail,
            contentDescription = product.title,
            modifier = Modifier.size(120.dp),
            contentScale = ContentScale.Fit
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                product.title,
                style = MaterialTheme.typography.titleMedium,
            )
            Spacer(modifier = Modifier.height(2.dp))
            if (product.officialStoreName != null) {
                Text(
                    text = "Por ${product.officialStoreName}",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            if (product.originalPrice != null) {
                Text(
                    text = NumberFormat.getCurrencyInstance()
                        .format(product.originalPrice),
                    style = MaterialTheme.typography.labelSmall.copy(
                        textDecoration = TextDecoration.LineThrough
                    ),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            Text(
                text = NumberFormat.getCurrencyInstance()
                    .format(product.price),
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.SemiBold
                )
            )
        }
    }
}

@Preview
@Composable
fun ProductListItemPreview() {
    MeliProductsTheme {
        ProductListItem(
            product = Product(
                id = "MLB123456789",
                title = "Produto de Exemplo",
                price = 199.99,
                originalPrice = 249.99,
                categoryId = "",
                availableQuantity = 1,
                currencyId = "BRL",
                thumbnail = "http://http2.mlstatic.com/D_619667-MLA47781882790_102021-I.jpg",
                condition = "Novo",
                officialStoreName = "Loja Oficial Exemplo",
                attributes = listOf(),
                shipping = Shipping(
                    freeShipping = true,
                    logisticType = "me2",
                ),
            ),
            onClick = {}
        )
    }
}

