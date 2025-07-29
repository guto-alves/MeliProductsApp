package com.example.meliproducts.data.model

import com.squareup.moshi.Json

data class Product(
    val id: String,
    val title: String,
    val condition: String,
    @Json(name = "category_id")
    val categoryId: String,
    val thumbnail: String,
    @Json(name = "currency_id")
    val currencyId: String,
    val price: Double,
    @Json(name = "original_price")
    val originalPrice: Double?,
    @Json(name = "official_store_name")
    val officialStoreName: String?,
    val attributes: List<Attribute>,
    @Json(name = "available_quantity")
    val availableQuantity: Int,
    val shipping: Shipping
)
