package com.example.mealmeatapp.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mealmeatapp.ui.theme.*
import com.example.mealmeatapp.viewmodel.HomeViewModel
import com.example.mealmeatapp.view.component.*
import kotlin.text.category

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel,
    navController: NavController
) {
    val categories = listOf(
        "All",
        "Breakfast",
        "Lunch",
        "Dinner"
    )

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController, homeViewModel.plannedMeals.size) // Số lượng công thức, List
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))

                // Search box
                SearchBoxHome(
                    homeViewModel = homeViewModel
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Category buttons
//                CategoryButton()

                Spacer(modifier = Modifier.height(24.dp))

                TitleHome()

                // Button "Random Meal"
                RandomRecipeButton(
                    homeViewModel = homeViewModel
                )

                Spacer(modifier = Modifier.height(24.dp))
            }

          // Display meals based on selected category
//            items(homeViewModel.recipe.value) { recipe ->
//                if (homeViewModel.selectedCategory.value == "All" || recipe.category == homeViewModel.selectedCategory.value) {
//                    RecipeItemLarge(
//                        homeViewModel = homeViewModel,
//                        recipe = recipe,
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(vertical = 6.dp),
//                    )
//                }
//            }
        }
    }
}


data class BottomNavItem(
    val route: String,
    val icon: Int,
    val label: String
)

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    MealtimeAppTheme {
        HomeScreen(HomeViewModel(), rememberNavController())
    }
}