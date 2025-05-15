package com.example.mealmeatapp.view.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mealmeatapp.R
import com.example.mealmeatapp.apimodel.recipe.Recipe


@Composable
fun IngredientContent(recipe: Recipe?) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Ingredients",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            fontSize = 22.sp,
            color = colorResource(R.color.orange)
        )
        Spacer(modifier = Modifier.height(8.dp))
        recipe?.extendedIngredients?.forEach { ingredient ->
            Text(
                text = "• ${
                    ingredient.original
                }",
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 16.sp,
                color = Black,
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }
    }
}

@Composable
fun InstructionContent(recipe: Recipe?) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Cooking Instructions",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            fontSize = 22.sp,
            color = colorResource(R.color.orange)
        )
        Spacer(modifier = Modifier.height(8.dp))
        recipe?.analyzedInstructions?.forEachIndexed { index, instruction ->
            recipe?.analyzedInstructions?.size?.let {
                if (it > 1) {
                    Text(
                        text = "${index + 1}. ${instruction.name}",
                        style = MaterialTheme.typography.titleSmall,
                        fontSize = 20.sp,
                        color = Black,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                    recipe?.analyzedInstructions!![index]?.steps?.forEach { st ->
                        Text(
                            text = "${index + 1}.${st.number}. ${st.step}",
                            style = MaterialTheme.typography.bodySmall,
                            fontSize = 16.sp,
                            color = Black,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                    }
                } else {
                    recipe?.analyzedInstructions!![index]?.steps?.forEach { st ->
                        Text(
                            text = "${index + 1}.${st.number}. ${st.step}",
                            style = MaterialTheme.typography.bodySmall,
                            fontSize = 16.sp,
                            color = Black,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                    }
                }
            }


        }
    }
}


@Composable
fun NutrientCircle(
    label: String,
    valuePair: Pair<Float, String>,
    size: Dp = 100.dp,
    strokeWidth: Dp = 4.dp,
    backgroundColor: Color = Color.LightGray,
    progressColor: Color = Color(0xFF2E7D32),
    // Bạn có thể truyền text tuỳ ý, mặc định hiển thị %
) {
    val centerText = valuePair.second
    Box(
        modifier = Modifier.size(size),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val stroke = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
            val radius = size.toPx() / 2
            val center = Offset(size.toPx() / 2, size.toPx() / 2)
            // vòng nền
            drawCircle(color = backgroundColor, radius = radius, center = center, style = stroke)
            // vòng progress
            drawArc(
                color = progressColor,
                startAngle = -90f,
                sweepAngle = (valuePair.first.coerceIn(0f, 1f) * 360f),
                useCenter = false,
                topLeft = Offset(center.x - radius, center.y - radius),
                size = androidx.compose.ui.geometry.Size(radius * 2, radius * 2),
                style = stroke
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = centerText,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                ),
                color = Black,
                textAlign = TextAlign.Center // Black text for value
            )
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                color = Black, // Black text for label,
                textAlign = TextAlign.Center
            )
        }

    }
}
