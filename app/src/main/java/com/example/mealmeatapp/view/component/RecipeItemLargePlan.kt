package com.example.mealmeatapp.view.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mealmeatapp.apimodel.recipe.Recipe
import com.example.mealmeatapp.viewmodel.ProfileViewModel
import com.example.mealmeatapp.viewmodel.RecipeDetailViewModel

@Composable
fun RecipeItemLargePlan(
    navController: NavController,
    recipeDetailViewModel: RecipeDetailViewModel,
    profileViewModel: ProfileViewModel,
    recipe: Recipe,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(8.dp)
    ) {
        Text(
            text = recipe.title,
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = recipe.summary.take(50) + "...",
            style = MaterialTheme.typography.bodySmall
        )
    }
}