package com.example.mealmeatapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mealmeatapp.ui.theme.MealtimeAppTheme
import com.example.mealmeatapp.view.FavoriteScreen
import com.example.mealmeatapp.view.HomeScreen
import com.example.mealmeatapp.view.MealPlannerScreen
import com.example.mealmeatapp.view.MealtimeScreen
import com.example.mealmeatapp.view.ProfileScreen
import com.example.mealmeatapp.view.ProfileSetupScreen
import com.example.mealmeatapp.view.RecipeDetailScreen
import com.example.mealmeatapp.view.SettingScreen
import com.example.mealmeatapp.view.SignInScreen
import com.example.mealmeatapp.view.SignUpScreen
import com.example.mealmeatapp.view.ChatScreen
import com.example.mealmeatapp.viewmodel.FavoriteViewModel
import com.example.mealmeatapp.viewmodel.HomeViewModel
import com.example.mealmeatapp.viewmodel.ProfileSetUpViewModel
import com.example.mealmeatapp.viewmodel.ProfileViewModel
import com.example.mealmeatapp.viewmodel.RecipeDetailViewModel
import com.example.mealmeatapp.viewmodel.RecipePlannerViewModel
import com.example.mealmeatapp.viewmodel.SettingViewModel
import com.example.mealmeatapp.viewmodel.SignInViewModel
import com.example.mealmeatapp.viewmodel.SignUpViewModel

/**
 * Activity chính của ứng dụng, thiết lập điều hướng và giao diện Compose.
 */
class MainActivity : ComponentActivity() {
    // Khởi tạo các ViewModel bằng cách sử dụng delegate by viewModels()
    private val signInViewModel: SignInViewModel by viewModels()
    private val signUpViewModel: SignUpViewModel by viewModels()
    private val profileSetUpViewModel: ProfileSetUpViewModel by viewModels()
    private val homeViewModel: HomeViewModel by viewModels()
    private val recipeDetailViewModel: RecipeDetailViewModel by viewModels()
    private val favoriteViewModel: FavoriteViewModel by viewModels()
    private val settingViewModel: SettingViewModel by viewModels()
    private val profileViewModel: ProfileViewModel by viewModels()
    private val recipePlannerViewModel: RecipePlannerViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MealtimeAppTheme {
                // Tạo NavController để quản lý điều hướng
                val navController = rememberNavController()
                Scaffold { innerPadding ->
                    // Thiết lập NavHost để điều hướng giữa các màn hình
                    NavHost(
                        navController = navController,
                        startDestination = "meal_time",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        // Màn hình khởi đầu
                        composable("meal_time") {
                            MealtimeScreen(
                                navController = navController
                            )
                        }

                        // Màn hình đăng nhập
                        composable("sign_in") {
                            SignInScreen(
                                navController = navController,
                                signInViewModel = signInViewModel,
                                profileViewModel = profileViewModel,
                                homeViewModel = homeViewModel,
                                recipePlannerViewModel = recipePlannerViewModel
                            )
                        }

                        // Màn hình đăng ký
                        composable("sign_up") {
                            SignUpScreen(
                                navController = navController,
                                signUpViewModel = signUpViewModel,
                                profileViewModel = profileViewModel
                            )
                        }

                        // Màn hình thiết lập hồ sơ
                        composable("profile_set_up") {
                            ProfileSetupScreen(
                                navController = navController,
                                profileSetUpViewModel = profileSetUpViewModel,
                                profileViewModel = profileViewModel,
                                homeViewModel = homeViewModel
                            )
                        }

                        // Màn hình chính (Home)
                        composable("home") {
                            HomeScreen(
                                navController = navController,
                                homeViewModel = homeViewModel,
                                recipeDetailViewModel = recipeDetailViewModel,
                                profileViewModel = profileViewModel,
                                recipePlannerViewModel = recipePlannerViewModel
                            )
                        }

                        // Màn hình chi tiết công thức
                        composable("recipe_detail") {
                            RecipeDetailScreen(
                                navController = navController,
                                recipeDetailViewModel = recipeDetailViewModel,
                                profileViewModel = profileViewModel
                            )
                        }

                        // Màn hình lập kế hoạch bữa ăn
                        composable("planner") {
                            MealPlannerScreen(
                                navController = navController,
                                profileViewModel = profileViewModel,
                                recipeDetailViewModel = recipeDetailViewModel,
                                recipePlannerViewModel = recipePlannerViewModel
                            )
                        }

                        // Màn hình danh sách yêu thích
                        composable("favorite") {
                            FavoriteScreen(
                                navController = navController,
                                favoriteViewModel = favoriteViewModel,
                                recipeDetailViewModel = recipeDetailViewModel,
                                profileViewModel = profileViewModel
                            )
                        }

                        // Màn hình cài đặt
                        composable("setting") {
                            SettingScreen(
                                navController = navController,
                                settingViewModel = settingViewModel,
                                profileViewModel = profileViewModel
                            )
                        }

                        composable("chat") {
                            ChatScreen(navController = navController)
                        }

                        // Màn hình hồ sơ người dùng
                        composable("profile") {
                            ProfileScreen(
                                navController = navController,
                                profileViewModel = profileViewModel
                            )
                        }
                    }
                }
            }
        }
    }
}