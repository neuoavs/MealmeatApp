package com.example.mealmeatapp.view.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
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
            Text(
                text = "${index + 1}. ${instruction.name}",
                style = MaterialTheme.typography.titleSmall,
            )
            recipe?.analyzedInstructions[index]?.steps?.forEach { st ->
                Text(
                    text = "${index}.${st.number}. ${st.step}",
                    style = MaterialTheme.typography.bodySmall,
                    fontSize = 16.sp,
                    color = Black,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }
    }
}