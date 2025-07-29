package com.example.meliproducts.data.repository

import android.content.Context
import android.util.Log
import com.example.meliproducts.data.model.Category
import com.example.meliproducts.data.model.Product
import com.example.meliproducts.data.model.ProductDescription
import com.example.meliproducts.data.model.ProductDetail
import com.example.meliproducts.data.util.Result
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.FileNotFoundException
import java.io.IOException

private const val TAG = "LocalProductRepository"

private data class SearchResponse(
    val results: List<Product>
)

class LocalProductRepository(private val context: Context) : ProductRepository {
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private fun readAssetFile(fileName: String): String {
        val assetManager = context.assets
        assetManager.open(fileName).bufferedReader().use {
            return it.readText()
        }
    }

    override suspend fun searchProducts(query: String): Result<List<Product>> =
        withContext(Dispatchers.IO) {
            try {
                Log.d(TAG, "searchProducts: reading file for query '$query'")
                val json = readAssetFile("data/search-MLA-$query.json")
                Log.d(TAG, "searchProducts: JSON read successfully")

                val adapter = moshi.adapter(SearchResponse::class.java)
                val response = adapter.fromJson(json)

                val products = response?.results ?: emptyList()

                Log.d(TAG, "searchProducts: ${products.size} products found")
                Result.Success(products)
            } catch (e: FileNotFoundException) {
                Log.w(TAG, "searchProducts: File not found for query '$query'", e)
                Result.Error(IOException("Nenhum produto encontrado para '$query'"))
            } catch (e: Exception) {
                Log.e(TAG, "Error searching products", e)
                Result.Error(e)
            }
        }

    override suspend fun getProductDetail(id: String): Result<ProductDetail> =
        withContext(Dispatchers.IO) {
            try {
                Log.d(TAG, "getProductDetail: reading file for id '$id'")
                val json = readAssetFile("data/item-$id.json")

                val adapter = moshi.adapter(ProductDetail::class.java)
                val productDetail = adapter.fromJson(json)

                if (productDetail != null) {
                    Log.d(TAG, "getProductDetail: Product found")
                    Result.Success(productDetail)
                } else {
                    Log.w(TAG, "getProductDetail: Product not found")
                    Result.Error(IOException("Product not found"))
                }
            } catch (e: FileNotFoundException) {
                Log.w(TAG, "getProductDetail: File not found for id '$id'")
                Result.Error(FileNotFoundException(
                    "Produto com o '$id' não foi encontrado. Tente novamente mais tarde."))
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching product detail", e)
                Result.Error(e)
            }
        }

    override suspend fun getProductDescription(id: String): Result<ProductDescription> =
        withContext(Dispatchers.IO) {
            try {
                Log.d(TAG, "getProductDescription: reading file for id '$id'")
                val json = readAssetFile("data/item-$id-description.json")

                val adapter = moshi.adapter(ProductDescription::class.java)
                val productDescription = adapter.fromJson(json)

                if (productDescription != null) {
                    Log.d(TAG, "getProductDescription: Description found")
                    Result.Success(productDescription)
                } else {
                    Log.w(TAG, "getProductDescription: Description not found")
                    Result.Error(IOException("Description not found"))
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching product description", e)
                Result.Error(e)
            }
        }

    override suspend fun getProductCategory(id: String): Result<Category> =
        withContext(Dispatchers.IO) {
            try {
                Log.d(TAG, "getProductCategory: reading file for id '$id'")
                val json = readAssetFile("data/item-$id-category.json")

                val adapter = moshi.adapter(Category::class.java)
                val category = adapter.fromJson(json)

                if (category != null) {
                    Log.d(TAG, "getProductCategory: Category found")
                    Result.Success(category)
                } else {
                    Log.w(TAG, "getProductCategory: Category not found")
                    Result.Error(IOException("Category not found"))
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching product category", e)
                Result.Error(e)
            }
        }
}
