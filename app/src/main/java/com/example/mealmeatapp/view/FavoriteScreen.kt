package com.example.mealmateapp.ui.theme.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
import com.example.mealmeatapp.ui.theme.view.MealItemLarge
import com.example.mealmeatapp.ui.theme.view.BottomNavigationBar


@Composable
fun FavoriteScreen(
    viewModel: HomeViewModel,
    navController: NavController
) {
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
                    text = stringResource(id = R.string.title_favourite),
                    style = MaterialTheme.typography.headlineSmall.copy(fontSize = 20.sp),
                )

                Text(
                    text = stringResource(id = R.string.des_favourite),
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp),
                    color = Gray,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))
            }

            if (viewModel.favoriteMeals.isEmpty()) {
                item {
                    Text(
                        text = stringResource(id = R.string.no_favorite),
                        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp),
                        color = Gray,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            } else {
                items(viewModel.favoriteMeals.chunked(1)) { rowMeals ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        MealItemLarge(
                            meal = rowMeals[0],
                            modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
                            isFavorite = viewModel.favoriteMeals.contains(rowMeals[0]),
                            isPlanned = false, // Giá trị mặc định, vì FavoriteScreen không cần quản lý kế hoạch
                            onFavoriteClick = { viewModel.toggleFavorite(rowMeals[0]) },
                            onPlanClick = { }, // Hàm rỗng, vì không cần chức năng này
                            onClick = { navController.navigate("foodDetail/${rowMeals[0].id}") }
                        )

                        if (rowMeals.size > 1) {
                            MealItemLarge(
                                meal = rowMeals[1],
                                modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
                                isFavorite = viewModel.favoriteMeals.contains(rowMeals[1]),
                                isPlanned = false,
                                onFavoriteClick = { viewModel.toggleFavorite(rowMeals[1]) },
                                onPlanClick = { },
                                onClick = { navController.navigate("foodDetail/${rowMeals[1].id}") }
                            )
                        } else {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun FavoriteScreenPreview() {
    MealtimeAppTheme {
        FavoriteScreen(HomeViewModel(), rememberNavController())
    }
}
