package com.example.mealmeatapp.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mealmeatapp.ui.theme.*
import com.example.mealmeatapp.viewmodel.HomeViewModel
import com.example.mealmeatapp.view.component.*
import androidx.compose.foundation.lazy.items
import androidx.navigation.compose.rememberNavController
import com.example.mealmeatapp.viewmodel.ProfileViewModel
import com.example.mealmeatapp.viewmodel.RecipeDetailViewModel
import kotlin.getValue

@Suppress("TYPE_INTERSECTION_AS_REIFIED_WARNING",
    "INFERRED_TYPE_VARIABLE_INTO_EMPTY_INTERSECTION_WARNING"
)
class HomeActivity : ComponentActivity() {

    private val homeViewModel: HomeViewModel by viewModels()
    private val recipeDetailViewModel: RecipeDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MealtimeAppTheme {
                HomeActivityContent()
            }
        }
    }

    @Composable
    private fun HomeActivityContent() {
        // Tạo NavController riêng cho HomeActivity
        val navController = rememberNavController()
        HomeScreen(
            navController = navController,
            homeViewModel = homeViewModel,
            recipeDetailViewModel = recipeDetailViewModel,
            profileViewModel = ProfileViewModel()
        )
    }
}


@Composable
fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel,
    recipeDetailViewModel: RecipeDetailViewModel,
    profileViewModel: ProfileViewModel
) {
    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController) // Mục menu
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
                SearchBoxHome(homeViewModel = homeViewModel)

                Spacer(modifier = Modifier.height(16.dp))

                // Category buttons
                CategoryButton(homeViewModel = homeViewModel)

                Spacer(modifier = Modifier.height(24.dp))

                TitleHome()

                Spacer(modifier = Modifier.height(24.dp))
            }


            items(homeViewModel.recipes.value) { recipe ->
                RecipeItemLargeHome(
                    navController = navController,
                    recipeDetailViewModel = recipeDetailViewModel,
                    homeViewModel = homeViewModel,
                    recipe = recipe,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                )
            }
        }

    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    MealtimeAppTheme {
        HomeScreen(
            navController = rememberNavController(),
            homeViewModel = HomeViewModel(),
            recipeDetailViewModel = RecipeDetailViewModel(),
            profileViewModel = ProfileViewModel()
        )
    }
}