package com.example.mealmeatapp.view.component

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import com.example.mealmeatapp.R
import com.example.mealmeatapp.apimodel.recipe.Recipe
import com.example.mealmeatapp.model.MealType
import com.example.mealmeatapp.viewmodel.ProfileViewModel
import com.example.mealmeatapp.viewmodel.RecipeDetailViewModel
import com.example.mealmeatapp.viewmodel.RecipePlannerViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MealPlannerComponent(
    weekStart: LocalDate,
    onPreviousWeek: () -> Unit,
    onNextWeek: () -> Unit,
    navController: NavController,
    profileViewModel: ProfileViewModel,
    recipeDetailViewModel: RecipeDetailViewModel,
    recipePlannerViewModel: RecipePlannerViewModel,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        item {
            Text(
                text = "Meal Planner",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            WeekSelector(weekStart, onPreviousWeek, onNextWeek)
            WeeklyMealPlan(
                weekStart = weekStart,
                navController = navController,
                profileViewModel = profileViewModel,
                recipeDetailViewModel = recipeDetailViewModel,
                recipePlannerViewModel = recipePlannerViewModel
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeekSelector(
    weekStart: LocalDate,
    onPreviousWeek: () -> Unit,
    onNextWeek: () -> Unit
) {
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val weekEnd = weekStart.plusDays(6)
    val weekRange = "${weekStart.format(formatter)} - ${weekEnd.format(formatter)}"

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onPreviousWeek) {
            Icon(
                painter = painterResource(id = R.drawable.previous),
                contentDescription = "Previous week"
            )
        }
        Text(
            text = weekRange,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )
        IconButton(onClick = onNextWeek) {
            Icon(
                painter = painterResource(id = R.drawable.next),
                contentDescription = "Next week"
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeeklyMealPlan(
    weekStart: LocalDate,
    navController: NavController,
    profileViewModel: ProfileViewModel,
    recipeDetailViewModel: RecipeDetailViewModel,
    recipePlannerViewModel: RecipePlannerViewModel
) {
    val daysOfWeek = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")

    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
        daysOfWeek.forEachIndexed { index, day ->
            val date = weekStart.plusDays(index.toLong())
            val formatter = DateTimeFormatter.ofPattern("dd/MM")

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                shape = MaterialTheme.shapes.medium,
                elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
                border = BorderStroke(2.dp, Color(0xFFFF9100))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "$day - ${date.format(formatter)}",
                        style = MaterialTheme.typography.titleSmall
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    DayMealPlan(
                        date = date,
                        navController = navController,
                        profileViewModel = profileViewModel,
                        recipeDetailViewModel = recipeDetailViewModel,
                        recipePlannerViewModel = recipePlannerViewModel
                    )
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DayMealPlan(
    date: LocalDate,
    navController: NavController,
    profileViewModel: ProfileViewModel,
    recipeDetailViewModel: RecipeDetailViewModel,
    recipePlannerViewModel: RecipePlannerViewModel
) {
    val mealTypes = listOf(MealType.BREAKFAST, MealType.LUNCH, MealType.DINNER)
    var showDialog by remember { mutableStateOf(false) }
    var selectedMealType by remember { mutableStateOf<MealType?>(null) }
    val mealsByType = recipePlannerViewModel.mealsFor(date)

    Column {
        mealTypes.forEach { mealType ->
            val mealsForDay = mealsByType[mealType] ?: emptyList()

            Column(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = mealType.name.lowercase().replaceFirstChar { it.uppercase() },
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.add),
                        contentDescription = "Add meal",
                        modifier = Modifier
                            .size(32.dp)
                            .padding(8.dp)
                            .clickable {
                                selectedMealType = mealType
                                showDialog = true
                            }
                    )
                }

                if (mealsForDay.isEmpty()) {
                    Text(
                        text = "No dish selected",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                        modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                    )
                } else {
                    mealsForDay.forEach { recipe ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, top = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = recipe.title,
                                style = MaterialTheme.typography.bodySmall,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.weight(1f)
                            )
                            Icon(
                                painter = painterResource(id = R.drawable.delete),
                                contentDescription = "Remove meal",
                                modifier = Modifier
                                    .size(24.dp)
                                    .clickable {
                                        recipePlannerViewModel.removeMeal(date, mealType, recipe)
                                    }
                            )
                        }
                    }
                }
            }
        }
    }

    if (showDialog && selectedMealType != null) {
        RecipeSelectionDialog(
            onDismiss = { showDialog = false },
            onRecipeSelected = { recipe ->
                recipePlannerViewModel.scheduleMeal(date, selectedMealType!!, recipe)
                showDialog = false
            },
            recipePlannerViewModel = recipePlannerViewModel,
            profileViewModel = profileViewModel,
            navController = navController,
            recipeDetailViewModel = recipeDetailViewModel
        )
    }
}

@Composable
fun RecipeSelectionDialog(
    onDismiss: () -> Unit,
    onRecipeSelected: (Recipe) -> Unit,
    recipePlannerViewModel: RecipePlannerViewModel,
    profileViewModel: ProfileViewModel,
    navController: NavController,
    recipeDetailViewModel: RecipeDetailViewModel
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(decorFitsSystemWindows = false, usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF0F8E0))
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .align(Alignment.Center),
                shape = MaterialTheme.shapes.medium,
                elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
                border = BorderStroke(2.dp, Color(0xFFFF9100))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Select a Recipe",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color(0xFFFF9100),
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    recipePlannerViewModel.errorMessage?.let { error ->
                        Text(
                            text = error,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                    }
                    if (recipePlannerViewModel.allRecipes.isEmpty()) {
                        Text(
                            text = "No recipes available",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp),
                            textAlign = TextAlign.Center
                        )
                    } else {
                        LazyColumn(modifier = Modifier.heightIn(max = 300.dp)) {
                            items(recipePlannerViewModel.allRecipes) { recipe ->
                                RecipeItemLargePlan(
                                    navController = navController,
                                    recipeDetailViewModel = recipeDetailViewModel,
                                    profileViewModel = profileViewModel,
                                    recipe = recipe,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable { onRecipeSelected(recipe) }
                                        .background(Color(0xFFFFF3E0), RoundedCornerShape(4.dp))
                                        .padding(vertical = 4.dp)
                                )
                            }
                        }
                    }
                    TextButton(
                        onClick = onDismiss,
                        modifier = Modifier.align(Alignment.End),
                        colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFFFF9100))
                    ) {
                        Text("Cancel")
                    }
                }
            }
        }
    }
}