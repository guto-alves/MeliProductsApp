package com.example.meliproducts.ui.productdetail

import com.example.meliproducts.data.model.Category
import com.example.meliproducts.data.model.ProductDescription
import com.example.meliproducts.data.model.ProductDetail
import com.example.meliproducts.data.repository.ProductRepository
import com.example.meliproducts.data.util.Result
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ProductDetailViewModelTest {
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun productDetailViewModel_LoadProductSuccess_UpdatesUiStateToSuccess() = runTest {
        val repository = mockk<ProductRepository>()
        val detail = ProductDetail(
            id = "id",
            title = "Product",
            categoryId = "cat",
            price = 100.0,
            basePrice = 100.0,
            originalPrice = null,
            currencyId = "BRL",
            thumbnail = "url",
            pictures = emptyList(),
            attributes = emptyList(),
            shipping = mockk(),
            warranty = null
        )
        val description = ProductDescription(
            text = "Description of the product",
            plainText = "Plain description"
        )
        val category = Category(
            id = "cat",
            name = "Category Name",
            picture = "category_picture_url",
            pathFromRoot = listOf(),
        )

        coEvery { repository.getProductDetail("id") } returns Result.Success(detail)
        coEvery { repository.getProductDescription("id") } returns Result.Success(description)
        coEvery { repository.getProductCategory("cat") } returns Result.Success(category)

        val viewModel = ProductDetailViewModel(repository)
        viewModel.loadProduct("id", "cat")
        advanceUntilIdle()

        assertTrue(viewModel.uiState.value is ProductDetailUiState.Success)
    }

    @Test
    fun productDetailViewModel_LoadProductError_UpdatesUiStateToError() = runTest {
        val repository = mockk<ProductRepository>()
        coEvery { repository.getProductDetail("id") } returns Result.Error(Exception("Not found"))

        val viewModel = ProductDetailViewModel(repository)
        viewModel.loadProduct("id", "cat")
        advanceUntilIdle()

        assertTrue(viewModel.uiState.value is ProductDetailUiState.Error)
    }
}
