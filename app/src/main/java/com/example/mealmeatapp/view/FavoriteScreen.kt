package com.example.mealmeatapp.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mealmeatapp.ui.theme.*
import com.example.mealmeatapp.view.component.BottomNavigationBar
import com.example.mealmeatapp.viewmodel.FavoriteViewModel
import com.example.mealmeatapp.view.component.*
import com.example.mealmeatapp.viewmodel.ProfileViewModel
import com.example.mealmeatapp.viewmodel.RecipeDetailViewModel

@Composable
fun FavoriteScreen(
    navController: NavController,
    favoriteViewModel: FavoriteViewModel,
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
                Spacer(modifier = Modifier.height(24.dp))

                TitleFavorite()

                Spacer(modifier = Modifier.height(24.dp))
            }


            items(profileViewModel.favoriteRecipe) { recipe ->
                RecipeItemLargeFavorite(
                    navController = navController,
                    recipeDetailViewModel = recipeDetailViewModel,
                    favoriteViewModel = favoriteViewModel,
                    recipe = recipe,
                    profileViewModel = profileViewModel,
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
fun FavoriteScreenPreview() {
    MealtimeAppTheme {
        FavoriteScreen(
            navController = rememberNavController(),
            favoriteViewModel = FavoriteViewModel(),
            recipeDetailViewModel = RecipeDetailViewModel(),
            profileViewModel = ProfileViewModel()
        )
    }
}
