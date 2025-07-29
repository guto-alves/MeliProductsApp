package com.example.meliproducts

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.meliproducts.ui.productdetail.ProductDetailScreen
import com.example.meliproducts.ui.productdetail.ProductDetailViewModel
import com.example.meliproducts.ui.search.SearchScreen
import com.example.meliproducts.ui.searchresult.SearchResultScreen

const val ROUTE_SEARCH = "search"
const val ROUTE_RESULTS = "results"
const val ROUTE_DETAILS = "product_details"

@Composable
fun MeliProductsNavGraph(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = ROUTE_SEARCH,
        modifier = Modifier.fillMaxSize()
    ) {
        composable(route = ROUTE_SEARCH) {
            SearchScreen(
                onSearch = { query ->
                    navController.navigate("$ROUTE_RESULTS/$query")
                },
            )
        }
        composable(
            route = "$ROUTE_RESULTS/{searchTerm}",
            arguments = listOf(
                navArgument("searchTerm") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val searchTerm = backStackEntry.arguments?.getString("searchTerm") ?: ""

            SearchResultScreen(
                searchTerm = searchTerm,
                onProductClick = { productId, categoryId ->
                    navController.navigate("$ROUTE_DETAILS/$productId/$categoryId")
                },
                navigateUp = { navController.navigateUp() },
            )
        }
        composable(
            route = "$ROUTE_DETAILS/{productId}/{categoryId}",
            arguments = listOf(
                navArgument("productId") { type = NavType.StringType },
                navArgument("categoryId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val productDetailViewModel: ProductDetailViewModel = hiltViewModel()
            val productId = backStackEntry.arguments?.getString("productId") ?: ""
            val categoryId = backStackEntry.arguments?.getString("categoryId") ?: ""

            LaunchedEffect(productId, categoryId) {
                if (productId.isNotBlank() && categoryId.isNotBlank()) {
                    productDetailViewModel.loadProduct(productId, categoryId)
                }
            }

            ProductDetailScreen(
                uiState = productDetailViewModel.uiState.collectAsState(),
                onBack = { navController.navigateUp() }
            )
        }
    }
}
