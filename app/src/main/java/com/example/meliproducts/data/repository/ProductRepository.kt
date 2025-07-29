package com.example.meliproducts.data.repository

import com.example.meliproducts.data.model.Category
import com.example.meliproducts.data.model.Product
import com.example.meliproducts.data.model.ProductDescription
import com.example.meliproducts.data.model.ProductDetail
import com.example.meliproducts.data.util.Result

interface ProductRepository {
    suspend fun searchProducts(query: String): Result<List<Product>>

    suspend fun getProductDetail(id: String): Result<ProductDetail>

    suspend fun getProductDescription(id: String): Result<ProductDescription>

    suspend fun getProductCategory(id: String): Result<Category>
}

