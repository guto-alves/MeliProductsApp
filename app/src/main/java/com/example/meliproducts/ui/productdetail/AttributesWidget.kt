package com.example.meliproducts.ui.productdetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.meliproducts.R
import com.example.meliproducts.data.model.Attribute


@Composable
fun ProductAttributesWidget(
    attributes: List<Attribute>,
    modifier: Modifier = Modifier
) {
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        AttributesDialog(
            attributes = attributes,
            onDismissRequest = { showDialog = false }
        )
    }

    Column(modifier.fillMaxWidth()) {
        Spacer(Modifier.height(16.dp))
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth(),
            thickness = 0.5.dp,
        )
        Spacer(Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.product_attributes),
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(Modifier.height(16.dp))
        Column {
            attributes
                .filter {
                    !listOf(
                        "BRAND", "ANATEL_HOMOLOGATION_NUMBER",
                        "CELLPHONES_ANATEL_HOMOLOGATION_NUMBER"
                    ).contains(it.id)
                }
                .subList(0, 6).forEach {
                    AttributeItemRow(attribute = it)
                }
        }
        TextButton(
            onClick = {
                showDialog = true
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Row {
                Text(
                    text = stringResource(R.string.check_all_attributes),
                    modifier = Modifier.weight(1f),
                )
                Spacer(Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

@Composable
private fun AttributeItemRow(
    attribute: Attribute,
    modifier: Modifier = Modifier
) {
    Row(
        modifier.padding(vertical = 4.dp)
    ) {
        Text(
            attribute.name,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
        )
        Spacer(Modifier.width(16.dp))
        Text(
            attribute.valuesDescription,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
        )
    }
    HorizontalDivider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        thickness = 0.2.dp,
    )
}

@Composable
private fun AttributesDialog(
    attributes: List<Attribute>,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        modifier = modifier,
        title = {
            Text(
                text = stringResource(R.string.product_attributes),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
            )
        },
        text = {
            LazyColumn {
                items(attributes) {
                    AttributeItemRow(attribute = it)
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismissRequest) {
                Text(stringResource(R.string.back))
            }
        }
    )
}