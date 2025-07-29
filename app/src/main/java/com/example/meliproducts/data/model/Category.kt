package com.example.meliproducts.data.model

import com.squareup.moshi.Json

data class Category(
    val id: String,
    val name: String,
    val picture: String,
    @Json(name = "path_from_root")
    val pathFromRoot: List<PathFromRoot>
)

data class PathFromRoot(
    val id: String,
    val name: String
)

