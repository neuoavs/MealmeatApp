package com.example.mealmeatapp.ui.theme.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.ui.draw.alpha
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
import androidx.navigation.compose.rememberNavController
import com.example.mealmeatapp.R
import com.example.mealmeatapp.ui.theme.*
import com.example.mealmeatapp.ui.theme.controller.HomeViewModel
import com.example.mealmeatapp.ui.theme.model.Meal
import com.example.mealmeatapp.ui.theme.view.BottomNavigationBar
import kotlin.random.Random // Thêm import này

// Danh sách các ngày trong tuần
private val daysOfWeek = listOf(
    "Monday",
    "Tuesday",
    "Wednesday",
    "Thursday",
    "Friday",
    "Saturday",
    "Sunday"
)

@Composable
fun MealPlannerScreen(
    viewModel: HomeViewModel,
    navController: NavController
) {
    println("Entered MealPlannerScreen")
    // Lưu trữ trạng thái mở rộng của từng ngày
    val expandedDays = remember { mutableStateMapOf<String, Boolean>().apply {
        daysOfWeek.forEach { day -> this[day] = false }
    }}

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

                Text(
                    text = stringResource(id = R.string.title_planner),
                    style = MaterialTheme.typography.headlineSmall.copy(fontSize = 20.sp),
                )

                Text(
                    text = stringResource(id = R.string.des_planner),
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp),
                    color = Gray,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                // Các nút chức năng bổ sung
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = { navController.navigate("workoutPlan") },
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 4.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Orange)
                    ) {
                        Text(
                            text = "Workout Plan",
                            color = White,
                            style = MaterialTheme.typography.labelLarge.copy(fontSize = 12.sp)
                        )
                    }
                    Button(
                        onClick = { navController.navigate("shoppingList") },
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 4.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Orange)
                    ) {
                        Text(
                            text = "Shopping List",
                            color = White,
                            style = MaterialTheme.typography.labelLarge.copy(fontSize = 12.sp)
                        )
                    }
                    Button(
                        onClick = { navController.navigate("nutritionInfo") },
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 4.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Orange)
                    ) {
                        Text(
                            text = "Nutrition Info",
                            color = White,
                            style = MaterialTheme.typography.labelLarge.copy(fontSize = 12.sp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }

            // Hiển thị lịch ăn uống theo ngày
            items(daysOfWeek) { day ->
                DayMealSection(
                    day = day,
                    meals = viewModel.plannedMeals.filter { it.dayOfWeek == day },
                    viewModel = viewModel,
                    navController = navController,
                    isExpanded = expandedDays[day] ?: false,
                    onExpandClick = { expandedDays[day] = !(expandedDays[day] ?: false) }
                )
            }

            // Nút thêm bữa ăn mới
            item {
                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { viewModel.addRandomPlannedMeal() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(45.dp),
                    shape = RoundedCornerShape(22.5.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Orange)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.add),
                            contentDescription = "Add Meal",
                            tint = White,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Add New Meal",
                            color = White,
                            style = MaterialTheme.typography.labelLarge.copy(fontSize = 14.sp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }

            // Tổng kết kế hoạch
            if (viewModel.plannedMeals.isNotEmpty()) {
                item {
                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Weekly Meal Summary (${viewModel.plannedMeals.size})",
                        style = MaterialTheme.typography.titleMedium.copy(fontSize = 18.sp),
                        color = Orange,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    viewModel.plannedMeals.forEach { meal ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "${meal.name} (${meal.category}) - ${meal.dayOfWeek}",
                                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp),
                                modifier = Modifier.weight(1f)
                            )
                            IconButton(
                                onClick = { viewModel.removePlannedMeal(meal) },
                                modifier = Modifier.size(24.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.remove), // delete
                                    contentDescription = "Remove",
                                    tint = Orange
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { viewModel.clearPlannedMeals() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(45.dp),
                        shape = RoundedCornerShape(22.5.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Orange)
                    ) {
                        Text(
                            text = "Clear All Plans",
                            color = White,
                            style = MaterialTheme.typography.labelLarge.copy(fontSize = 14.sp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
fun DayMealSection(
    day: String,
    meals: List<Meal>,
    viewModel: HomeViewModel,
    navController: NavController,
    isExpanded: Boolean,
    onExpandClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onExpandClick() },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = day,
                    style = MaterialTheme.typography.titleMedium.copy(fontSize = 18.sp),
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    painter = painterResource(id = if (isExpanded) R.drawable.keyboard_arrow_up else R.drawable.keyboard_arrow_down),// arrow_drop_down and arrow_drop_up
                    contentDescription = "Expand/Collapse",
                    tint = Orange,
                    modifier = Modifier.size(24.dp)
                )
            }

            if (isExpanded) {
                Spacer(modifier = Modifier.height(8.dp))

                if (meals.isEmpty()) {
                    Text(
                        text = "No meals planned for $day.",
                        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp),
                        color = Gray,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                } else {
                    meals.groupBy { it.category }.forEach { (mealType, mealList) ->
                        Text(
                            text = mealType,
                            style = MaterialTheme.typography.titleSmall.copy(fontSize = 16.sp),
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                        mealList.forEach { meal ->
                            MealItemLarge(
                                meal = meal,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                isSelected = true,
                                onSelectClick = { viewModel.removePlannedMeal(meal) },
                                onClick = { navController.navigate("foodDetail/${meal.id}") }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MealItemLarge(
    meal: Meal,
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    onSelectClick: () -> Unit,
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
                    NutrientCircle(
                        label = "Protein",
                        value = "${meal.protein}g",
                        color = Green
                    )
                    NutrientCircle(
                        label = "Fat",
                        value = "${meal.fat}g",
                        color = Yellow
                    )
                    NutrientCircle(
                        label = "Carbs",
                        value = "${meal.carbs}g",
                        color = Blue
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Select icon
            Icon(
                painter = painterResource(id = if (isSelected) R.drawable.check_circle else R.drawable.add),
                contentDescription = if (isSelected) "Selected" else "Add to Plan",
                tint = if (isSelected) Orange else MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onSelectClick() }
            )
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
            .size(60.dp)
            .clip(CircleShape)
            .border(2.dp, color, CircleShape)
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
                color = Black
            )
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall.copy(fontSize = 12.sp),
                color = Black
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MealPlannerScreenPreview() {
    MealtimeAppTheme {
        MealPlannerScreen(HomeViewModel(), rememberNavController())
    }
}

