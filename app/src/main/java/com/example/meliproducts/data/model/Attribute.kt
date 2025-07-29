package com.example.meliproducts.data.model

import com.squareup.moshi.Json

data class Attribute(
    val id: String,
    val name: String,
    @Json(name = "value_name")
    val valueName: String?,
    @Json(name = "value_type")
    val valueType: String?,
    val values: List<Value>,
) {
    val valuesDescription: String
        get() = values
            .filter { it.name != null }
            .joinToString(separator = ", ") { it.name!! }
}

data class Value(
    val id: String?,
    val name: String?,
)