package com.example.meliproducts.data.model

import com.squareup.moshi.Json

data class ProductDetail(
    val id: String,
    val title: String,
    @Json(name = "category_id")
    val categoryId: String,
    val price: Double,
    @Json(name = "base_price")
    val basePrice: Double,
    @Json(name = "original_price")
    val originalPrice: Double?,
    @Json(name = "currency_id")
    val currencyId: String,
    val thumbnail: String,
    val pictures: List<Picture>,
    val attributes: List<Attribute>,
    val shipping: Shipping,
    val warranty: String?,
)

data class Picture(
    val id: String,
    val url: String,
    @Json(name = "secure_url")
    val secureUrl: String,
    val size: String,
    @Json(name = "max_size")
    val maxSize: String,
    val quality: String?
)