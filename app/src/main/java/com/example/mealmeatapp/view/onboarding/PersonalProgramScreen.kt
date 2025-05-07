package com.example.mealmeatapp.view.onboarding

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mealmeatapp.model.NutritionPlan
import com.example.mealmeatapp.view.components.CustomButton


@Composable
fun PersonalProgramScreen(
    nutritionPlan: NutritionPlan,
    weeks: Int,
    onExploreClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF9E6))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "YOUR PERSONAL PROGRAM IS READY",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2E7D32),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "To achieve your target weight we’ve tailored this plan for you",
                fontSize = 16.sp,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 32.dp)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(Color(0xFFE8F5E9), RoundedCornerShape(8.dp))
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = nutritionPlan.weightChangeText,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Text(
                        text = "$weeks week | Today - ${nutritionPlan.endDateText} | ${nutritionPlan.weeklyChangeText}",
                        fontSize = 12.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Canvas(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                    ) {
                        val width = size.width
                        val height = size.height

                        val pointsCount = nutritionPlan.weights.size
                        val stepX = width / (pointsCount - 1)

                        val minWeight = nutritionPlan.weights.minOrNull() ?: 0f
                        val maxWeight = nutritionPlan.weights.maxOrNull() ?: 0f
                        val weightRange = maxWeight - minWeight
                        val points = nutritionPlan.weights.mapIndexed { index, weight ->
                            val x = index * stepX
                            val normalizedY = if (weightRange != 0f) (weight - minWeight) / weightRange else 0f
                            val y = height - (normalizedY * height)
                            Offset(x, y)
                        }

                        val yLabels = if (nutritionPlan.weights.last() >= nutritionPlan.weights.first()) {
                            listOf(minWeight, minWeight + weightRange * 0.2f, minWeight + weightRange * 0.4f,
                                minWeight + weightRange * 0.6f, minWeight + weightRange * 0.8f, maxWeight)
                        } else {
                            listOf(maxWeight, maxWeight - weightRange * 0.2f, maxWeight - weightRange * 0.4f,
                                maxWeight - weightRange * 0.6f, maxWeight - weightRange * 0.8f, minWeight)
                        }
                        yLabels.forEachIndexed { index, weight ->
                            val y = height - (index * height / (yLabels.size - 1))
                            drawLine(
                                start = Offset(0f, y),
                                end = Offset(width, y),
                                color = Color.LightGray.copy(alpha = 0.5f),
                                strokeWidth = 2f
                            )
                        }

                        val path = Path().apply {
                            points.forEachIndexed { index, point ->
                                if (index == 0) {
                                    moveTo(point.x, point.y)
                                } else {
                                    lineTo(point.x, point.y)
                                }
                            }
                        }
                        drawPath(
                            path = path,
                            color = Color(0xFF2E7D32),
                            style = Stroke(width = 4f)
                        )

                        points.forEach { point ->
                            drawCircle(
                                color = Color(0xFF2E7D32),
                                radius = 6f,
                                center = point
                            )
                            drawCircle(
                                color = Color.White,
                                radius = 3f,
                                center = point
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(8.dp))
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .background(Color(0xFF2E7D32), RoundedCornerShape(40.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "${nutritionPlan.dailyCalories} kcal",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            text = "Daily Calories",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(12.dp)
                                    .background(Color(0xFFFF5722), RoundedCornerShape(2.dp))
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Carbs",
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "${nutritionPlan.carbsPercent}%",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(12.dp)
                                    .background(Color(0xFFFFC107), RoundedCornerShape(2.dp))
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Fat",
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "${nutritionPlan.fatPercent}%",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(12.dp)
                                    .background(Color(0xFF6200EE), RoundedCornerShape(2.dp))
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Protein",
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "${nutritionPlan.proteinPercent}%",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .background(Color(0xFF03A9F4), RoundedCornerShape(2.dp))
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Daily water intake",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = "${nutritionPlan.dailyWaterIntake} ml",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomButton(
                text = "EXPLORE NOW",
                onClick = onExploreClick
            )
        }
    }
}
