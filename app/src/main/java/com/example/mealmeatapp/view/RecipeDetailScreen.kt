package com.example.mealmeatapp.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.mealmeatapp.R
import com.example.mealmeatapp.apimodel.recipe.RecipeRepository
import com.example.mealmeatapp.view.component.BottomNavigationBar
import com.example.mealmeatapp.view.component.IngredientContent
import com.example.mealmeatapp.view.component.InstructionContent
import com.example.mealmeatapp.viewmodel.ProfileViewModel
import com.example.mealmeatapp.viewmodel.RecipeDetailViewModel

@Composable
fun RecipeDetailScreen(
    navController: NavController,
    recipeDetailViewModel: RecipeDetailViewModel,
    profileViewModel: ProfileViewModel
) {
    val recipeRepository = RecipeRepository()

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
                // Image Header with Gradient Overlay
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
                ) {
                    AsyncImage(
                        model = recipeDetailViewModel.recipe.value?.imageUrl.toString(),
                        contentDescription = recipeDetailViewModel.recipe.value?.title.toString(),
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.fillMaxSize()
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(Black.copy(alpha = 0.4f), Color.Transparent),
                                    startY = 0f,
                                    endY = 150f
                                )
                            )
                    )
                    // Back Button
                    IconButton(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(15.dp)
                            .size(20.dp)
                            .background(colorResource(id = R.color.white).copy(alpha = 0.1f), CircleShape)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.arrow_back_ios),
                            contentDescription = "Back",
                            tint = colorResource(id = R.color.black)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Meal Name and Favorite Button
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = recipeDetailViewModel.recipe.value?.title.toString(),
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp,
                        color = Black,
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(
                        onClick = {
                            recipeDetailViewModel.isFavorite.value = !recipeDetailViewModel.isFavorite.value
                            recipeDetailViewModel.updateFavoriteStatus()
                        },
                        modifier = Modifier
                            .size(48.dp)
                            .background(colorResource(id = R.color.white), CircleShape)
                            .shadow(4.dp, CircleShape)
                    ) {
                        Icon(
                            painter = painterResource(
                                id = if (recipeDetailViewModel.isFavorite.value) R.drawable.favorite_fill else R.drawable.favorite
                            ),
                            contentDescription = "Favorite",
                            tint = colorResource(id = R.color.red),
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Nutrient Section
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    NutrientCircle(
                        label = "Calories",
                        value = recipeRepository.getNutritionValue(recipeDetailViewModel.recipe.value, "Calories").second,
                        color = colorResource(id = R.color.orange)
                    )
                    NutrientCircle(
                        label = "Protein",
                        value = recipeRepository.getNutritionValue(recipeDetailViewModel.recipe.value, "Protein").second,
                        color = Green
                    )
                    NutrientCircle(
                        label = "Fat",
                        value = recipeRepository.getNutritionValue(recipeDetailViewModel.recipe.value, "Fat").second,
                        color = Yellow
                    )
                    NutrientCircle(
                        label = "Carbohydrates",
                        value = recipeRepository.getNutritionValue(recipeDetailViewModel.recipe.value, "Carbohydrates").second,
                        color = Blue
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Ingredients Section
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .shadow(4.dp, RoundedCornerShape(12.dp)),
                    colors = CardDefaults.cardColors(containerColor = colorResource(R.color.white))
                ) {
                    IngredientContent(
                        recipe = recipeDetailViewModel.recipe.value
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Cooking Instructions Section
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .shadow(4.dp, RoundedCornerShape(12.dp)),
                    colors = CardDefaults.cardColors(containerColor = colorResource(R.color.white))
                ) {
                    InstructionContent(
                        recipe = recipeDetailViewModel.recipe.value
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }

    }
}

@Composable
fun NutrientCircle(
    label: String,
    value: String,
    color: Color
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(60.dp) // Increased size to fit both value and label
            .clip(CircleShape)
            .border(2.dp, color, CircleShape) // Colored border with no background fill
            .padding(4.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                ),
                color = Black // Black text for value
            )
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall.copy(fontSize = 12.sp),
                color = Black // Black text for label
            )
        }
    }
}

/*
@Preview(showBackground = true)
@Composable
fun FoodDetailScreenPreview() {
    MealtimeAppTheme {
        FoodDetailScreen(
            navController = rememberNavController(),
            mealId = 1
        )
    }
}*/
