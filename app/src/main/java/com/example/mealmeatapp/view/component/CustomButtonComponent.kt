package com.example.mealmeatapp.view.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import com.example.mealmeatapp.R
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.rememberAsyncImagePainter

// Primary button (used for "NEXT")

@Composable
fun CustomButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp),
        colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.dark_green))
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

// Secondary button (used for "SKIP", "ft/cm", "kg/lb")
@Composable
fun SecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    textColor: Color = Color.Black
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .height(36.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = textColor
        ),
        elevation = null
    ) {
        Text(
            text = text,
            color = textColor,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

// Dialog button (used for "OK"/"Cancel" in dialogs)
@Composable
fun DialogButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    textColor: Color = colorResource(id = R.color.dark_green)
) {
    TextButton(
        onClick = onClick,
        modifier = modifier
            .height(36.dp)
            .padding(horizontal = 8.dp),
        colors = ButtonDefaults.textButtonColors(contentColor = textColor)
    ) {
        Text(
            text = text,
            color = textColor,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
    }
}
@Composable
fun ImageFromUrl(
    url: String,
) {
    Card {
        Image(
            painter = rememberAsyncImagePainter(url),
            contentDescription = "url"
        )
    }
}


fun Modifier.enabled(enabled: Boolean): Modifier {
    return if (enabled) this else this.then(Modifier.alpha(0.5f))
}

