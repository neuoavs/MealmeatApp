package com.example.mealmeatapp.view.component

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.mealmeatapp.R
import com.example.mealmeatapp.apimodel.recipe.Recipe
import com.example.mealmeatapp.apimodel.recipe.RecipeRepository
import com.example.mealmeatapp.viewmodel.FavoriteViewModel
import com.example.mealmeatapp.viewmodel.ProfileViewModel
import com.example.mealmeatapp.viewmodel.RecipeDetailViewModel

@Composable
fun TitleFavorite() {
    Text(
        text = stringResource(id = R.string.title_favourite),
        style = MaterialTheme.typography.headlineSmall.copy(fontSize = 20.sp),
    )

    Text(
        text = stringResource(id = R.string.des_favourite),
        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp),
        color = colorResource(id = R.color.gray),
        modifier = Modifier.padding(vertical = 8.dp)
    )
}

@Composable
fun RecipeItemLargeFavorite(
    navController: NavController,
    recipeDetailViewModel: RecipeDetailViewModel,
    favoriteViewModel: FavoriteViewModel,
    profileViewModel: ProfileViewModel,
    recipe: Recipe?,
    modifier: Modifier = Modifier
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
            AsyncImage(
                model = recipe?.image ?: "",
                contentDescription = recipe?.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape),
                placeholder = painterResource(id = R.drawable.placeholder),
                error = painterResource(id = R.drawable.error),
                onError = { error ->
                    Log.e("ImageError", "Error loading image for ${recipe?.title}: ${error.result.throwable.message}")
                }
            )

            Spacer(modifier = Modifier.width(12.dp))

            // Meal details
            Column(
                modifier = Modifier.weight(1f)
            ) {
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

                Text(
                    text = RecipeRepository().getNutritionValue(recipe, "Calories").second,
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                    color = colorResource(id = R.color.gray).copy(alpha = 0.6f)
                )

                Spacer(modifier = Modifier.height(8.dp))

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
                val isPlanned = profileViewModel.addedRecipe.any { it.id == recipe?.id }

                Icon(
                    painter = painterResource(id = if (isFavorite) R.drawable.favorite_fill else R.drawable.favorite),
                    contentDescription = "Favorite",
                    tint = if (isFavorite) colorResource(id = R.color.red) else MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {
                            if (recipe != null) {
                                if (isFavorite) profileViewModel.removeFavoriteRecipe(recipe)
                                else profileViewModel.addFavoriteRecipe(recipe)
                            }
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
                            if (recipe != null) {
                                if (isPlanned) profileViewModel.removeRecipe(recipe)
                                else profileViewModel.addRecipe(recipe)
                            }
                        }
                )
            }
        }
    }
}

@Composable
fun LoadImage() {
    Column {
        AsyncImage(
            model = "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b6/Image_created_with_a_mobile_phone.png/1280px-Image_created_with_a_mobile_phone.png",
            contentDescription = "Recipe Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape),
            placeholder = painterResource(id = R.drawable.placeholder),
            error = painterResource(id = R.drawable.error),
            onError = { error ->
                Log.e("ImageError", "Error loading image: ${error.result.throwable.message}")
            }
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RecipeItemLargeFavoritePreview() {
    MaterialTheme {
        LoadImage()
    }
}