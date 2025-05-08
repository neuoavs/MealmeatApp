package com.example.mealmeatapp.ui.theme.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mealmeatapp.R
import com.example.mealmeatapp.ui.theme.*
import com.example.mealmeatapp.ui.theme.controller.HomeViewModel
import com.example.mealmeatapp.ui.theme.model.Meal

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
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
            BottomNavigationBar(navController, viewModel.plannedMeals.size)
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
                OutlinedTextField(
                    value = viewModel.searchQuery.value,
                    onValueChange = { viewModel.onSearchQueryChange(it) },
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

                Spacer(modifier = Modifier.height(16.dp))

                // Category buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    categories.forEach { category ->
                        Button(
                            onClick = { viewModel.onCategoryChange(category) },
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 2.dp),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (viewModel.selectedCategory.value == category) Orange else White,
                                contentColor = if (viewModel.selectedCategory.value == category) White else Black
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

                Spacer(modifier = Modifier.height(24.dp))

                // Heading
                Text(
                    text = stringResource(id = R.string.title_home),
                    style = MaterialTheme.typography.headlineSmall.copy(fontSize = 20.sp),
                )

                // Subheading
                Text(
                    text = stringResource(id = R.string.des_home),
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp),
                    color = Gray,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                // Button "Random Meal"
                Button(
                    onClick = { viewModel.onRandomMealClick() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(45.dp),
                    shape = RoundedCornerShape(22.5.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Orange)
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

                Spacer(modifier = Modifier.height(24.dp))
            }

            // Display meals based on selected category
            items(viewModel.meals.value) { meal ->
                if (viewModel.selectedCategory.value == "All" || meal.category == viewModel.selectedCategory.value) {
                    MealItemLarge(
                        meal = meal,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        isFavorite = viewModel.favoriteMeals.contains(meal),
                        isPlanned = viewModel.plannedMeals.contains(meal),
                        onFavoriteClick = { viewModel.toggleFavorite(meal) },
                        onPlanClick = { viewModel.addPlannedMeal(meal) },
                        onClick = { navController.navigate("foodDetail/${meal.id}") }
                    )
                }
            }
        }
    }
}

@Composable
fun MealItemLarge(
    meal: Meal,
    modifier: Modifier = Modifier,
    isFavorite: Boolean,
    isPlanned: Boolean,
    onFavoriteClick: () -> Unit,
    onPlanClick: () -> Unit,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = modifier
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Circular image
            Image(
                painter = painterResource(id = meal.imageResId),
                contentDescription = meal.name,
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
                    text = meal.name,
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
                    text = "${meal.calories} kcal • ${meal.weight}g",
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                    color = Gray.copy(alpha = 0.6f)
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Nutrient row with circles
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    NutrientItemWithBar(
                        label = "Protein",
                        value = meal.protein,
                        maxValue = 50,
                        color = Green
                    )
                    NutrientItemWithBar(
                        label = "Fat",
                        value = meal.fat,
                        maxValue = 50,
                        color = Yellow
                    )
                    NutrientItemWithBar(
                        label = "Carbs",
                        value = meal.carbs,
                        maxValue = 50,
                        color = Blue
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Favorite and Plan icons
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Icon(
                    painter = painterResource(id = if (isFavorite) R.drawable.favorite_fill else R.drawable.favorite),
                    contentDescription = "Favorite",
                    tint = if (isFavorite) Orange else MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { onFavoriteClick() }
                )
                Spacer(modifier = Modifier.height(8.dp))
                Icon(
                    painter = painterResource(id = if (isPlanned) R.drawable.check_circle else R.drawable.add),
                    contentDescription = if (isPlanned) "Planned" else "Add to Plan",
                    tint = if (isPlanned) Orange else MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { onPlanClick() }
                )
            }
        }
    }
}

@Composable
fun NutrientItemWithBar(label: String, value: Int, maxValue: Int, color: Color) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Vertical progress bar
        Box(
            modifier = Modifier
                .width(4.dp)
                .height(40.dp)
                .clip(RoundedCornerShape(2.dp))
                .background(Gray.copy(alpha = 0.2f))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight((value.toFloat() / maxValue).coerceIn(0f, 1f))
                    .clip(RoundedCornerShape(2.dp))
                    .background(color)
                    .align(Alignment.BottomStart)
            )
        }

        Spacer(modifier = Modifier.width(6.dp))

        // Nutrient info
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "${value}g",
                style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                ),
                color = Black
            )
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                color = Black
            )
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController, plannedCount: Int) {
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
                    selectedIconColor = Orange,
                    selectedTextColor = Orange,
                    unselectedIconColor = Black,
                    unselectedTextColor = Black
                )
            )
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