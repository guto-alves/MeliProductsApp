package com.example.meliproducts.data.model

import com.squareup.moshi.Json

data class ProductDescription(
    val text: String?,
    @Json(name = "plain_text")
    val plainText: String
)

