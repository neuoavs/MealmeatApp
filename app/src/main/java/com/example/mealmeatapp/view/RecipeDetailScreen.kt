package com.example.mealmeatapp.view

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.mealmeatapp.R
import com.example.mealmeatapp.apimodel.recipe.RecipeRepository
import com.example.mealmeatapp.ui.theme.MealtimeAppTheme
import com.example.mealmeatapp.view.component.BottomNavigationBar
import com.example.mealmeatapp.view.component.IngredientContent
import com.example.mealmeatapp.view.component.InstructionContent
import com.example.mealmeatapp.view.component.NutrientCircle
import com.example.mealmeatapp.viewmodel.ProfileViewModel
import com.example.mealmeatapp.viewmodel.RecipeDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailScreen(
    navController: NavController,
    recipeDetailViewModel: RecipeDetailViewModel,
    profileViewModel: ProfileViewModel
) {
    val recipeRepository = RecipeRepository()
    val isFavorite = profileViewModel.favoriteRecipe.any { it.id == recipeDetailViewModel.recipe.value?.id }
    val isPlanned  = profileViewModel.addedRecipe   .any { it.id == recipeDetailViewModel.recipe.value?.id }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController)
        },
        topBar = {
            TopAppBar(
                title = { Text(text = "")},//stringResource(R.string.profile_info)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.arrow_back_ios),
                            contentDescription = "Back button"
                        )
                    }
                }
            )
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

                    val painter = rememberAsyncImagePainter(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data("https://img.spoonacular.com/recipes/324694-556x370.jpeg")
                            .crossfade(true)
                            .build()
                    )

                    Image(
                        painter = painter,
                        contentDescription =recipeDetailViewModel.recipe.value?.title,
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
                            if (isFavorite) profileViewModel.removeFavoriteRecipe(recipeDetailViewModel.recipe.value)
                            else profileViewModel.addFavoriteRecipe(recipeDetailViewModel.recipe.value)
                        },
                        modifier = Modifier
                            .size(52.dp)
                            .background(color = colorResource(id = R.color.white), shape = CircleShape)
                            .clip(CircleShape)
                    ) {
                        Icon(
                            painter = painterResource(
                                id = if (isFavorite) R.drawable.favorite_fill else R.drawable.favorite
                            ),
                            contentDescription = "Favorite",
                            tint = colorResource(id = R.color.red),
                            modifier = Modifier.fillMaxSize(0.6f) // icon chiếm 60% diện tích nút
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
                        size = 72.dp,
                        valuePair = recipeRepository.getNutritionValue(recipeDetailViewModel.recipe.value, "Calories"),
                        progressColor = colorResource(id = R.color.orange)
                    )
                    NutrientCircle(
                        label = "Protein",
                        size = 72.dp,
                        valuePair = recipeRepository.getNutritionValue(recipeDetailViewModel.recipe.value, "Protein"),
                        progressColor = Green
                    )
                    NutrientCircle(
                        label = "Fat",
                        size = 72.dp,
                        valuePair = recipeRepository.getNutritionValue(recipeDetailViewModel.recipe.value, "Fat"),
                        progressColor = Yellow
                    )
                    NutrientCircle(
                        label = "Carbs",
                        size = 72.dp,
                        valuePair = recipeRepository.getNutritionValue(recipeDetailViewModel.recipe.value, "Carbohydrates"),
                        progressColor = Blue
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

                Button(
                    onClick = {
                        if (isPlanned) profileViewModel.removeRecipe(recipeDetailViewModel.recipe.value)
                        else profileViewModel.addRecipe(recipeDetailViewModel.recipe.value)
                    },
                    modifier = Modifier,
                    enabled = true,
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.orange),
                        contentColor = Color.White
                    ),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                ) {

                    Icon(
                        painter = painterResource(id = if (isPlanned) R.drawable.check_circle else R.drawable.add),
                        contentDescription = if (isPlanned) "Planned" else "Add to Plan",
                        tint = if (isPlanned) colorResource(id = R.color.orange) else colorResource(id = R.color.white),
                        modifier = Modifier
                            .size(32.dp)
                            .padding(end = 8.dp)
                    )
                    Text(
                        text = if (isPlanned) "Planned" else "Add to Plan",
                        style = MaterialTheme.typography.labelLarge.copy(fontSize = 14.sp)
                    )
                }

            }
        }

    }
}
@Preview(showBackground = true)
@Composable
fun FoodDetailScreenPreview() {
    MealtimeAppTheme {
        RecipeDetailScreen(
            navController = rememberNavController(),
            recipeDetailViewModel = RecipeDetailViewModel(),
            profileViewModel = ProfileViewModel()
        )
    }
}
