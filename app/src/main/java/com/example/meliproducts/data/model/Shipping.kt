package com.example.meliproducts.data.model

import com.squareup.moshi.Json

data class Shipping(
    @Json(name = "free_shipping")
    val freeShipping: Boolean,
    @Json(name = "logistic_type")
    val logisticType: String
)
