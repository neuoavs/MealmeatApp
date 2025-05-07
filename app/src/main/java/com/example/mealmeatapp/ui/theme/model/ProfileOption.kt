package com.example.mealmeatapp.ui.theme.model

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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

@Composable
fun ProfileOption(
    text: String? = null,
    title: String? = null,
    difficulty: String? = null,
    duration: String? = null,
    weightChange: String? = null,
    iconResId: Int,
    isSelected: Boolean,
    onClick: () -> Unit,
    layoutType: String
) {
    when (layoutType) {
        "row" -> {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .background(
                        color = if (isSelected) Color(0xFFF5F5F5) else Color.White,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clickable { onClick() }
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = text ?: "",
                    fontSize = 16.sp,
                    color = Color.Black,
                    lineHeight = 20.sp
                )
                Icon(
                    painter = painterResource(id = iconResId),
                    contentDescription = "$text Icon",
                    tint = Color(0xFF2E7D32),
                    modifier = Modifier.size(24.dp)
                )
            }
        }
        "column" -> {
            Column(
                modifier = Modifier
                    .width(150.dp)
                    .background(
                        color = if (isSelected) Color(0xFFF5F5F5) else Color.White,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clickable { onClick() }
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(id = iconResId),
                    contentDescription = "$text Icon",
                    tint = Color.Unspecified,
                    modifier = Modifier
                        .size(100.dp)
                        .padding(bottom = 8.dp)
                )
                Text(
                    text = text ?: "",
                    fontSize = 16.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
            }
        }
        "progress" -> {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .border(2.dp, Color(0xFF6200EE), RoundedCornerShape(8.dp))
                    .background(
                        color = Color(0xFFF5F5F5),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clickable { onClick() }
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = title ?: "",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Text(
                        text = difficulty ?: "",
                        fontSize = 14.sp,
                        color = Color(0xFFFF5722)
                    )
                    Text(
                        text = duration ?: "",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = weightChange ?: "",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
                Icon(
                    painter = painterResource(id = iconResId),
                    contentDescription = "$title Icon",
                    tint = Color.Unspecified,
                    modifier = Modifier.size(40.dp)
                )
            }
        }
    }
}
