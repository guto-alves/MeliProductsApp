package com.example.meliproducts.ui.search

import org.junit.Assert.assertEquals
import org.junit.Test

class SearchViewModelTest {
    @Test
    fun searchViewModel_UpdateQuery_QueryIsUpdated() {
        val viewModel = SearchViewModel()

        viewModel.updateQuery("camisa")

        assertEquals("camisa", viewModel.query)
    }
}

