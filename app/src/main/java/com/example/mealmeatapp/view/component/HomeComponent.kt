package com.example.mealmeatapp.view.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.White
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
import androidx.navigation.compose.currentBackStackEntryAsState
import coil3.compose.AsyncImage
import com.example.mealmeatapp.apimodel.recipe.Recipe
import com.example.mealmeatapp.R
import com.example.mealmeatapp.apimodel.recipe.RecipeRepository
import com.example.mealmeatapp.view.BottomNavItem
import com.example.mealmeatapp.viewmodel.HomeViewModel


// Home Screen
@Composable
fun SearchBoxHome(
    homeViewModel: HomeViewModel
) {
    OutlinedTextField(
        value = homeViewModel.query.value,
        onValueChange = { homeViewModel.onQueryChange(it) },
        label = { Text("Search for a meal") },
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(vertical = 8.dp),
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.search), // search
                contentDescription = "Search Icon",
                modifier = Modifier.size(20.dp)
            )
        },
        shape = RoundedCornerShape(8.dp),
        textStyle = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp)
    )
}

@Composable
fun TitleHome() {
    // Heading
    Text(
        text = stringResource(id = R.string.title_home),
        style = MaterialTheme.typography.headlineSmall.copy(fontSize = 20.sp),
    )

    // Subheading
    Text(
        text = stringResource(id = R.string.des_home),
        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp),
        color = colorResource(id = R.color.gray),
        modifier = Modifier.padding(vertical = 8.dp)
    )
}

@Composable
fun RandomRecipeButton(
    homeViewModel: HomeViewModel
) {
    Button(
        onClick = { homeViewModel.onRandomRecipeClick() },
        modifier = Modifier
            .fillMaxWidth()
            .height(45.dp),
        shape = RoundedCornerShape(22.5.dp),
        colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.orange))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.shuffle), // shuffle
                contentDescription = "Random Icon",
                tint = White,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "Random Meal",
                color = White,
                style = MaterialTheme.typography.labelLarge.copy(fontSize = 14.sp)
            )
        }
    }
}


@Composable
fun RecipeItemLarge(
    homeViewModel: HomeViewModel,
    recipe: Recipe,
    modifier: Modifier = Modifier

//    isFavorite: Boolean,
//    isPlanned: Boolean,
//    onFavoriteClick: () -> Unit,
//    onPlanClick: () -> Unit,
//    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = modifier
            .clickable {
                // Chuyển tới trang chi tiết công thức
                //onClick()
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Circular image
            AsyncImage(
                model = recipe.imageUrl,
                contentDescription = recipe.title,
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
                    text = recipe.title,
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
            /*Column(
                horizontalAlignment = Alignment.End
            ) {
                Icon(
                    painter = painterResource(id = if (isFavorite) R.drawable.favorite_fill else R.drawable.favorite),
                    contentDescription = "Favorite",
                    tint = if (isFavorite) colorResource(id = R.color.orange) else MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {
                             Xử lý thêm vào yêu thích
                             onFavoriteClick()
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
                             Xử lý thêm vào thực đơn
                             onPlanClick()
                        }
                )
            }*/
        }
    }
}


@Composable
fun NutrientItemWithBar(
    name: String,
    recipe: Recipe,
    color: Color
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Vertical progress bar
        val nutritionValue = RecipeRepository().getNutritionValue(recipe, name)
        Box(
            modifier = Modifier
                .width(4.dp)
                .height(40.dp)
                .clip(RoundedCornerShape(2.dp))
                .background(colorResource(id = R.color.gray).copy(alpha = 0.2f))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(nutritionValue.first.coerceIn(0f, 1f))
                    .clip(RoundedCornerShape(2.dp))
                    .background(color)
                    .align(Alignment.BottomStart)
            )
        }

        Spacer(modifier = Modifier.width(6.dp))

        // Nutrient info
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = nutritionValue.second,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                ),
                color = Black
            )
            Text(
                text = name,
                style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                color = Black
            )
        }
    }
}



@Composable
fun BottomNavigationBar(
    navController: NavController,
    plannedCount: Int
) {
    val navItems = listOf(
        BottomNavItem("home", R.drawable.home, "Home"),
        BottomNavItem("planner", R.drawable.calendar_month_fill, "Planner ($plannedCount)"),
        BottomNavItem("favorite", R.drawable.favorite_fill, "Favorite"),
        BottomNavItem("setting", R.drawable.setting, "Setting"),
    )

    NavigationBar(
        containerColor = White
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        navItems.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = item.label,
                        modifier = Modifier.size(20.dp)
                    )
                },
                label = {
                    Text(
                        text = item.label,
                        fontSize = 10.sp
                    )
                },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = colorResource(id = R.color.orange),
                    selectedTextColor = colorResource(id = R.color.orange),
                    unselectedIconColor = Black,
                    unselectedTextColor = Black
                )
            )
        }
    }
}

/*@Composable
fun CategoryButton(

) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        categories.forEach { category ->
            Button(
                onClick = { homeViewModel.onCategoryChange(category) },
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 2.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (homeViewModel.selectedCategory.value == category) colorResource(id = R.color.orange) else White,
                    contentColor = if (homeViewModel.selectedCategory.value == category) White else Black
                ),
                contentPadding = PaddingValues(8.dp)
            ) {
                Text(
                    text = category.uppercase(),
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp),
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}*/
