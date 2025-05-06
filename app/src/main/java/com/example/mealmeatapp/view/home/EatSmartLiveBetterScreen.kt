package com.example.mealmeatapp.view.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.mealmeatapp.R
import com.example.mealmeatapp.ui.theme.AppColors
import com.example.mealmeatapp.ui.theme.AppShapes
import com.example.mealmeatapp.ui.theme.AppTypography

@Composable
fun EatSmartLiveBetterScreen(
    onLogoClick: () -> Unit
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

        // Center content (logo + text)
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo Icon",
                    tint = AppColors.DarkGreen,
                    modifier = Modifier
                        .size(100.dp)
                        .clickable { onLogoClick() }
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Eat Smart Live Better",
                    style = AppTypography.BodyLarge,
                    color = AppColors.DarkBlue,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
