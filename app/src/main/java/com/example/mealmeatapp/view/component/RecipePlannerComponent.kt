package com.example.mealmeatapp.view.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.mealmeatapp.R
import com.example.mealmeatapp.apimodel.recipe.Recipe
import com.example.mealmeatapp.apimodel.recipe.RecipeRepository
import com.example.mealmeatapp.viewmodel.ProfileViewModel
import com.example.mealmeatapp.viewmodel.RecipeDetailViewModel


@Composable
fun RecipeItemLargePlan(
    navController: NavController,
    recipeDetailViewModel: RecipeDetailViewModel,
    profileViewModel: ProfileViewModel,
    recipe: Recipe?,
    modifier: Modifier = Modifier,
    ) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = modifier
            .clickable {
                recipeDetailViewModel.updateModel(recipe)
                navController.navigate("recipe_detail")
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Circular image
            Image(
                painter = rememberAsyncImagePainter(recipe?.image),
                contentDescription = recipe?.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)

            )

            Spacer(modifier = Modifier.width(12.dp))

            // Meal details
            Column(
                modifier = Modifier.weight(1f)
            ) {
                // Meal name (bold)
                Text(
                    text = recipe?.title ?: "",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))

                // Calorie info (small, faded)
                Text(
                    text = RecipeRepository().getNutritionValue(recipe, "Calories").second,
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                    color = colorResource(id = R.color.gray).copy(alpha = 0.6f)
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Nutrient row with circles protein, fat, carbs
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    NutrientItemWithBar(
                        name = "Protein",
                        recipe = recipe,
                        color = Green
                    )
                    NutrientItemWithBar(
                        name = "Fat",
                        recipe = recipe,
                        color = Yellow
                    )
                    NutrientItemWithBar(
                        name = "Carbohydrates",
                        recipe = recipe,
                        color = Blue
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Favorite and Plan icons
            Column(
                horizontalAlignment = Alignment.End
            ) {
                val isFavorite = profileViewModel.favoriteRecipe.any { it.id == recipe?.id }
                val isPlanned  = profileViewModel.addedRecipe   .any { it.id == recipe?.id }

                Icon(
                    painter = painterResource(id = if (isFavorite) R.drawable.favorite_fill else R.drawable.favorite),
                    contentDescription = "Favorite",
                    tint = if (isFavorite) colorResource(id = R.color.orange) else MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {
                            if (isFavorite) profileViewModel.removeFavoriteRecipe(recipe)
                            else           profileViewModel.addFavoriteRecipe(recipe)
                        }
                )

                Spacer(modifier = Modifier.height(8.dp))
                Icon(
                    painter = painterResource(id = if (isPlanned) R.drawable.check_circle else R.drawable.add),
                    contentDescription = if (isPlanned) "Planned" else "Add to Plan",
                    tint = if (isPlanned) colorResource(id = R.color.orange) else MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {
                            if (isPlanned) profileViewModel.removeRecipe(recipe)
                            else           profileViewModel.addRecipe(recipe)
                        }
                )
            }
        }
    }
}