package com.example.mealmeatapp.view.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mealmeatapp.R

// Meal Time Screen

@Composable
fun SignInLinkPage(
    navController: NavController,
) {
    TextButton(onClick = {
        navController.navigate("sign_in")
    }
    ) {
        Text(
            text = stringResource(id = R.string.sign_in_text_button),
            color = colorResource(id = R.color.white),
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
fun TitleMealMeat() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(top = 50.dp)
    ) {
        Text(
            text = "Mealmeat",
            color = colorResource(id = R.color.gold),
            style = MaterialTheme.typography.headlineLarge
        )
        Text(
            text = "Recipes & Meal Planning",
            color = colorResource(id = R.color.white),
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
            lineHeight = 16.sp
        )
    }
}
// End Meal Time Screen
