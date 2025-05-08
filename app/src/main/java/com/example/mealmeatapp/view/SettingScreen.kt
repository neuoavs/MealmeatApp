package com.example.mealmeatapp.ui.theme.view

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mealmeatapp.controller.AuthViewModel
import com.example.mealmeatapp.ui.theme.controller.HomeViewModel
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.mealmeatapp.R
import com.example.mealmeatapp.ui.theme.*

data class SettingItem(
    val iconResId: Int,
    val title: String,
    val onClick: () -> Unit
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(
    navController: NavController,
    authViewModel: AuthViewModel,
    homeViewModel: HomeViewModel
) {
    println("Entered SettingScreen")
    val context = LocalContext.current
    var showLogoutDialog by remember { mutableStateOf(false) }
    var showThemeDialog by remember { mutableStateOf(false) }
    var showNotificationDialog by remember { mutableStateOf(false) }
    var showReportDialog by remember { mutableStateOf(false) }
    var showRateDialog by remember { mutableStateOf(false) }
    var showShareDialog by remember { mutableStateOf(false) }
    var showPrivacyDialog by remember { mutableStateOf(false) }

    // Theme state (simplified, to be saved in DataStore)
    var selectedTheme by remember { mutableStateOf("System Default") }

    // Notification states
    var mealReminders by remember { mutableStateOf(true) }
    var recipeNotifications by remember { mutableStateOf(true) }
    var appUpdates by remember { mutableStateOf(true) }

    // Report state
    var reportText by remember { mutableStateOf("") }
    var reportEmail by remember { mutableStateOf("") }

    val settingItems = listOf(
        SettingItem(
            iconResId = R.drawable.dark_mode_fill,
            title = "Change Your Theme",
            onClick = { showThemeDialog = true }
        ),
        SettingItem(
            iconResId = R.drawable.notifications_fill,
            title = "Notifications",
            onClick = { showNotificationDialog = true }
        ),
        SettingItem(
            iconResId = R.drawable.report,
            title = "Suggest or Report Anything",
            onClick = { showReportDialog = true }
        ),
        SettingItem(
            iconResId = R.drawable.star_fill,
            title = "Rate Us on Play Store",
            onClick = { showRateDialog = true }
        ),
        SettingItem(
            iconResId = R.drawable.share_fill,
            title = "Share the App with Friends",
            onClick = { showShareDialog = true }
        ),
        SettingItem(
            iconResId = R.drawable.verified_user_fill,
            title = "Privacy Policy",
            onClick = { showPrivacyDialog = true }
        ),
        SettingItem(
            iconResId = R.drawable.logout,
            title = "Log Out",
            onClick = { showLogoutDialog = true }
        )
    )

    // Theme Change BottomSheetDialog
    if (showThemeDialog) {
        ModalBottomSheet(
            onDismissRequest = { showThemeDialog = false },
            sheetState = rememberModalBottomSheetState(),
            containerColor = White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 24.dp)
            ) {
                // Title
                Text(
                    text = "Select Theme",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp
                    ),
                    color = Black,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Theme Options
                val themeOptions = listOf(
                    "Light" to R.drawable.light_mode_fill, // sunny
                    "Dark" to R.drawable.dark_mode_fill, // nightlight
                    "System Default" to R.drawable.setting // setting
                )
                themeOptions.forEach { (option, iconRes) ->
                    val isSelected = selectedTheme == option
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clickable {
                                selectedTheme = option
                            }
                            .border(
                                width = if (isSelected) 2.dp else 0.dp,
                                color = if (isSelected) Orange else Color.Transparent,
                                shape = RoundedCornerShape(12.dp)
                            ),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isSelected) Orange.copy(alpha = 0.1f) else White
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(Orange.copy(alpha = 0.2f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    painter = painterResource(iconRes),
                                    contentDescription = option,
                                    tint = Orange,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                text = option,
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                    fontSize = 16.sp
                                ),
                                color = Black
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Save Button
                Button(
                    onClick = {
                        showThemeDialog = false
                        // TODO: Save to DataStore and apply theme
                        println("Saved theme: $selectedTheme")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Orange)
                ) {
                    Text(
                        text = "Save",
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.Medium,
                            fontSize = 16.sp
                        ),
                        color = White
                    )
                }
            }
        }
    }
    // Notification Settings Dialog
    if (showNotificationDialog) {
        AlertDialog(
            onDismissRequest = { showNotificationDialog = false },
            title = { Text("Notifications", style = MaterialTheme.typography.titleLarge) },
            text = {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Meal reminders", style = MaterialTheme.typography.bodyLarge, color = Black)
                        Spacer(modifier = Modifier.weight(1f))
                        Switch(
                            checked = mealReminders,
                            onCheckedChange = { mealReminders = it },
                            colors = SwitchDefaults.colors(checkedThumbColor = Orange)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("New recipe notifications", style = MaterialTheme.typography.bodyLarge, color = Black)
                        Spacer(modifier = Modifier.weight(1f))
                        Switch(
                            checked = recipeNotifications,
                            onCheckedChange = { recipeNotifications = it },
                            colors = SwitchDefaults.colors(checkedThumbColor = Orange)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("App updates", style = MaterialTheme.typography.bodyLarge, color = Black)
                        Spacer(modifier = Modifier.weight(1f))
                        Switch(
                            checked = appUpdates,
                            onCheckedChange = { appUpdates = it },
                            colors = SwitchDefaults.colors(checkedThumbColor = Orange)
                        )
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showNotificationDialog = false
                        // TODO: Save to DataStore or SharedPreferences
                        println("Saved notifications: Meal=$mealReminders, Recipe=$recipeNotifications, Updates=$appUpdates")
                    },
                    colors = ButtonDefaults.textButtonColors(contentColor = Orange)
                ) { Text("Save", fontWeight = FontWeight.Medium) }
            },
            dismissButton = {
                TextButton(
                    onClick = { showNotificationDialog = false },
                    colors = ButtonDefaults.textButtonColors(contentColor = Gray)
                ) { Text("Cancel", fontWeight = FontWeight.Medium) }
            },
            containerColor = White,
            titleContentColor = Black,
            textContentColor = Black
        )
    }

    // Suggest or Report Dialog
    if (showReportDialog) {
        AlertDialog(
            onDismissRequest = { showReportDialog = false },
            title = { Text("Suggest or Report", style = MaterialTheme.typography.titleLarge) },
            text = {
                Column {
                    OutlinedTextField(
                        value = reportText,
                        onValueChange = { reportText = it },
                        label = { Text("Describe your suggestion or issue") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = reportEmail,
                        onValueChange = { reportEmail = it },
                        label = { Text("Your email (optional)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showReportDialog = false
                        val intent = Intent(Intent.ACTION_SENDTO).apply {
                            data = Uri.parse("mailto:support@yourapp.com")
                            putExtra(Intent.EXTRA_SUBJECT, "Feedback from user")
                            putExtra(Intent.EXTRA_TEXT, "Description: $reportText\nEmail: $reportEmail")
                        }
                        context.startActivity(Intent.createChooser(intent, "Send Feedback"))
                        // TODO: Optionally send via API
                    },
                    colors = ButtonDefaults.textButtonColors(contentColor = Orange)
                ) { Text("Submit", fontWeight = FontWeight.Medium) }
            },
            dismissButton = {
                TextButton(
                    onClick = { showReportDialog = false },
                    colors = ButtonDefaults.textButtonColors(contentColor = Gray)
                ) { Text("Cancel", fontWeight = FontWeight.Medium) }
            },
            containerColor = White,
            titleContentColor = Black,
            textContentColor = Black
        )
    }

    // Rate Us Dialog
    if (showRateDialog) {
        AlertDialog(
            onDismissRequest = { showRateDialog = false },
            title = { Text("Rate Us", style = MaterialTheme.typography.titleLarge) },
            text = { Text("Support us by rating the app on the Play Store!", style = MaterialTheme.typography.bodyLarge) },
            confirmButton = {
                TextButton(
                    onClick = {
                        showRateDialog = false
                        try {
                            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=${context.packageName}")))
                        } catch (e: Exception) {
                            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=${context.packageName}")))
                        }
                    },
                    colors = ButtonDefaults.textButtonColors(contentColor = Orange)
                ) { Text("Rate Now", fontWeight = FontWeight.Medium) }
            },
            dismissButton = {
                TextButton(
                    onClick = { showRateDialog = false },
                    colors = ButtonDefaults.textButtonColors(contentColor = Gray)
                ) { Text("Later", fontWeight = FontWeight.Medium) }
            },
            containerColor = White,
            titleContentColor = Black,
            textContentColor = Black
        )
    }

    // Share App Dialog
    if (showShareDialog) {
        AlertDialog(
            onDismissRequest = { showShareDialog = false },
            title = { Text("Share the App", style = MaterialTheme.typography.titleLarge) },
            text = { Text("Share this app with your friends!", style = MaterialTheme.typography.bodyLarge) },
            confirmButton = {
                TextButton(
                    onClick = {
                        showShareDialog = false
                        val shareIntent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(Intent.EXTRA_TEXT, "Check out this awesome app: https://play.google.com/store/apps/details?id=${context.packageName}")
                        }
                        context.startActivity(Intent.createChooser(shareIntent, "Share App"))
                    },
                    colors = ButtonDefaults.textButtonColors(contentColor = Orange)
                ) { Text("Share", fontWeight = FontWeight.Medium) }
            },
            dismissButton = {
                TextButton(
                    onClick = { showShareDialog = false },
                    colors = ButtonDefaults.textButtonColors(contentColor = Gray)
                ) { Text("Cancel", fontWeight = FontWeight.Medium) }
            },
            containerColor = White,
            titleContentColor = Black,
            textContentColor = Black
        )
    }

    // Privacy Policy Dialog
    if (showPrivacyDialog) {
        AlertDialog(
            onDismissRequest = { showPrivacyDialog = false },
            title = { Text("Privacy Policy", style = MaterialTheme.typography.titleLarge) },
            text = { Text("View our privacy policy.", style = MaterialTheme.typography.bodyLarge) },
            confirmButton = {
                TextButton(
                    onClick = {
                        showPrivacyDialog = false
                        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://yourapp.com/privacy")))
                    },
                    colors = ButtonDefaults.textButtonColors(contentColor = Orange)
                ) { Text("View", fontWeight = FontWeight.Medium) }
            },
            dismissButton = {
                TextButton(
                    onClick = { showPrivacyDialog = false },
                    colors = ButtonDefaults.textButtonColors(contentColor = Gray)
                ) { Text("Cancel", fontWeight = FontWeight.Medium) }
            },
            containerColor = White,
            titleContentColor = Black,
            textContentColor = Black
        )
    }

    // Logout Dialog
    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            title = { Text("Log Out", style = MaterialTheme.typography.titleLarge) },
            text = { Text("Are you sure you want to log out?", style = MaterialTheme.typography.bodyLarge) },
            confirmButton = {
                TextButton(
                    onClick = {
                        showLogoutDialog = false
                        println("Logging out and navigating to mealtime")
                        authViewModel.logout() // Xóa dữ liệu đăng nhập
                        homeViewModel.clearPlannedMeals() // Xóa dữ liệu bữa ăn
                        navController.navigate("mealtime") {
                            popUpTo(navController.graph.startDestinationId) { inclusive = true }
                        }
                    },
                    colors = ButtonDefaults.textButtonColors(contentColor = Orange)
                ) { Text("Log Out", fontWeight = FontWeight.Medium) }
            },
            dismissButton = {
                TextButton(
                    onClick = { showLogoutDialog = false },
                    colors = ButtonDefaults.textButtonColors(contentColor = Gray)
                ) { Text("Cancel", fontWeight = FontWeight.Medium) }
            },
            containerColor = White,
            titleContentColor = Black,
            textContentColor = Black
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Settings",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold,
                        color = Black,
                        fontSize = 20.sp
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier
                            .size(20.dp)
                            .background(White.copy(alpha = 0.7f), CircleShape)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.arrow_back_ios),
                            contentDescription = "Back",
                            tint = Black
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = White,
                    titleContentColor = Black
                ),
                modifier = Modifier.shadow(4.dp)
            )
        },
        containerColor = White
    ) { paddingValues ->
        LazyColumn(
            contentPadding = paddingValues,
            modifier = Modifier
                .fillMaxSize()
                .background(White)
                .padding(horizontal = 15.dp)
        ) {
            items(settingItems.size) { index ->
                val item = settingItems[index]
                Card(
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .shadow(2.dp, RoundedCornerShape(12.dp))
                        .clickable { item.onClick() },
                    colors = CardDefaults.cardColors(containerColor = White)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(30.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .background(Orange.copy(alpha = 0.1f), CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    painter = painterResource(id = item.iconResId),
                                    contentDescription = item.title,
                                    tint = Orange,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(14.dp))
                            Text(
                                text = item.title,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Medium,
                                fontSize = 20.sp,
                                color = Black
                            )
                        }
                        Icon(
                            painter = painterResource(id = R.drawable.arrow_forward_ios), // arrow_forward_ios
                            contentDescription = "Navigate",
                            tint = Orange,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
                if (index < settingItems.size - 1) {
                    Divider(
                        color = Gray.copy(alpha = 0.2f),
                        thickness = 0.5.dp,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun SettingScreenPreview() {
    MealtimeAppTheme {
        SettingScreen(
            navController = rememberNavController(),
            authViewModel = AuthViewModel(),
            homeViewModel = HomeViewModel()
        )
    }
}