package com.example.mealmeatapp.view

import android.app.Activity
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import com.example.mealmeatapp.ui.theme.MealtimeAppTheme
import com.example.mealmeatapp.view.component.BottomNavigationBar
import com.example.mealmeatapp.view.component.MealPlannerComponent
import com.example.mealmeatapp.viewmodel.ProfileViewModel
import com.example.mealmeatapp.viewmodel.RecipeDetailViewModel
import com.example.mealmeatapp.viewmodel.RecipePlannerViewModel
import java.time.LocalDate
import java.time.DayOfWeek
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.safeDrawing

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun MealPlannerScreen(
    navController: NavController,
    profileViewModel: ProfileViewModel,
    recipeDetailViewModel: RecipeDetailViewModel,
    recipePlannerViewModel: RecipePlannerViewModel
) {
    // Lấy Activity từ LocalContext
    val context = LocalContext.current
    val activity = context as? Activity ?: throw IllegalStateException("No Activity found")

    // Lấy thông tin kích thước màn hình
    val windowSizeClass = calculateWindowSizeClass(activity = activity)
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp

    // Điều chỉnh padding dựa trên kích thước màn hình cho mọi thiết bị
    val horizontalPadding = when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Compact -> 12.dp // Điện thoại nhỏ (e.g., < 600dp)
        WindowWidthSizeClass.Medium -> 16.dp  // Điện thoại lớn, tablet nhỏ (600dp - 840dp)
        WindowWidthSizeClass.Expanded -> 24.dp // Tablet, foldable (> 840dp)
        else -> 10.dp
    }
    val verticalPadding = 8.dp // Padding dọc linh hoạt, điều chỉnh cho mọi tỷ lệ

    // Quản lý tuần hiện tại
    val currentWeekStart = remember {
        mutableStateOf(LocalDate.now().with(DayOfWeek.MONDAY))
    }

    Scaffold(
        bottomBar = { BottomNavigationBar(navController) },
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing)
    ) { innerPadding ->
        MealPlannerComponent(
            weekStart = currentWeekStart.value,
            onPreviousWeek = {
                currentWeekStart.value = currentWeekStart.value.minusWeeks(1)
                println("Navigated to previous week: ${currentWeekStart.value}")
            },
            onNextWeek = {
                currentWeekStart.value = currentWeekStart.value.plusWeeks(1)
                println("Navigated to next week: ${currentWeekStart.value}")
            },
            navController = navController,
            profileViewModel = profileViewModel,
            recipeDetailViewModel = recipeDetailViewModel,
            recipePlannerViewModel = recipePlannerViewModel,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = horizontalPadding, vertical = verticalPadding)
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun MealPlannerScreenPreview() {
    MealtimeAppTheme {
        val recipePlannerViewModel = RecipePlannerViewModel().apply {
            setPreviewMode(true)
        }
        // Trong preview, không có Activity thực, nên tạm thời mock hoặc bỏ qua calculateWindowSizeClass
        // Để tránh lỗi, bạn có thể cung cấp một giá trị mặc định nếu cần
        MealPlannerScreen(
            navController = rememberNavController(),
            profileViewModel = ProfileViewModel(),
            recipeDetailViewModel = RecipeDetailViewModel(),
            recipePlannerViewModel = recipePlannerViewModel
        )
    }
}