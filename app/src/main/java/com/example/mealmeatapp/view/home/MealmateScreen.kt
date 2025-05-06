package com.example.mealmeatapp.view.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.mealmeatapp.R
import com.example.mealmeatapp.ui.theme.AppColors
import com.example.mealmeatapp.ui.theme.AppShapes
import com.example.mealmeatapp.ui.theme.AppTypography
import com.example.mealmeatapp.view.components.CustomButton




@Composable
fun MealmateScreen(
    onLoginClick: () -> Unit
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.LightCream)
    ) {
        // Top-right decorative circle
        Box(
            modifier = Modifier
                .size(200.dp)
                .offset(x = screenWidth - 100.dp, y = (-100).dp)
                .background(AppColors.LightGreen, shape = AppShapes.Circle)
        )

        // Bottom-left decorative circle
        Box(
            modifier = Modifier
                .size(200.dp)
                .offset(x = (-100).dp, y = screenHeight - 100.dp)
                .background(AppColors.LightGreen, shape = AppShapes.Circle)
        )

        // Main content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Welcome to Mealmate.",
                style = AppTypography.TitleLarge,
                color = AppColors.DarkGreen,
                modifier = Modifier.padding(top = 40.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            Icon(
                painter = painterResource(id = R.drawable.group1),
                contentDescription = "Mealmate Illustration",
                tint = Color.Unspecified,
                modifier = Modifier.size(200.dp)
            )

            Text(
                text = "Start growing your meals\nwith our step by step guides\nand helpful tips",
                style = AppTypography.BodyMedium,
                color = AppColors.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            CustomButton(
                text = "Login",
                onClick = onLoginClick
            )
        }
    }
}
