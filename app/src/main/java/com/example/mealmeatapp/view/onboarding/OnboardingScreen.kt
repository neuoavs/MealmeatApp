package com.example.mealmeatapp.view.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mealmeatapp.R
import com.example.mealmeatapp.model.OnboardingPage
import com.example.mealmeatapp.view.components.CustomButton


@Composable
fun OnboardingScreen(
    pages: List<OnboardingPage>,
    currentPage: Int,
    onPageChange: (Int) -> Unit,
    onNextClick: () -> Unit,
    onSkipClick: () -> Unit
) {
    val pageData = pages[currentPage]

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF9E6)) // Light Cream
    ) {
        // Top Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo Icon",
                tint = Color(0xFF2E7D32),
                modifier = Modifier.size(40.dp)
            )
            Text(
                text = "SKIP",
                fontSize = 16.sp,
                color = Color.Black,
                modifier = Modifier
                    .clickable { onSkipClick() }
                    .padding(8.dp)
            )
        }

        // Main Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = pageData.illustrationResId),
                contentDescription = "${pageData.title} Illustration",
                tint = Color.Unspecified,
                modifier = Modifier
                    .size(200.dp)
                    .padding(bottom = 16.dp)
            )
            Text(
                text = pageData.title,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = pageData.description,
                fontSize = 16.sp,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 32.dp)
            )
        }

        // Bottom Navigation
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (currentPage < pages.lastIndex) {
                CustomButton(
                    text = "NEXT",
                    onClick = { onPageChange(currentPage + 1) }
                )
            } else {
                CustomButton(
                    text = "START",
                    onClick = onNextClick
                )
                Text(
                    text = "Maybe later",
                    fontSize = 14.sp,
                    color = Color.Black,
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .clickable { onSkipClick() }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Page Indicator Dots
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                pages.forEachIndexed { index, _ ->
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .background(
                                color = if (index == currentPage) Color(0xFF2E7D32) else Color.Gray,
                                shape = CircleShape
                            )
                    )
                }
            }
        }
    }
}