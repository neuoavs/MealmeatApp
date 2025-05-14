package com.example.mealmeatapp.view

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mealmeatapp.ui.theme.MealtimeAppTheme
import com.example.mealmeatapp.view.component.BottomNavigationBar
import com.example.mealmeatapp.view.component.MealPlannerComponent
import com.example.mealmeatapp.viewmodel.ProfileViewModel
import com.example.mealmeatapp.viewmodel.RecipeDetailViewModel
import com.example.mealmeatapp.viewmodel.RecipePlannerViewModel
import java.time.LocalDate
import java.time.DayOfWeek

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MealPlannerScreen(
    navController: NavController,
    profileViewModel: ProfileViewModel,
    recipeDetailViewModel: RecipeDetailViewModel,
    recipePlannerViewModel: RecipePlannerViewModel
) {
    val currentWeekStart = remember {
        mutableStateOf(LocalDate.now().with(DayOfWeek.MONDAY))
    }

    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { padding ->
        MealPlannerComponent(
            weekStart = currentWeekStart.value,
            onPreviousWeek = {
                currentWeekStart.value = currentWeekStart.value.minusWeeks(1)
            },
            onNextWeek = {
                currentWeekStart.value = currentWeekStart.value.plusWeeks(1)
            },
            navController = navController,
            profileViewModel = profileViewModel,
            recipeDetailViewModel = recipeDetailViewModel,
            recipePlannerViewModel = recipePlannerViewModel,
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 16.dp)
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun MealPlannerScreenPreview() {
    MealtimeAppTheme {
        MealPlannerScreen(
            navController = rememberNavController(),
            profileViewModel = ProfileViewModel(),
            recipeDetailViewModel = RecipeDetailViewModel(),
            recipePlannerViewModel = RecipePlannerViewModel().apply {
                setPreviewMode(true)
            }
        )
    }
}