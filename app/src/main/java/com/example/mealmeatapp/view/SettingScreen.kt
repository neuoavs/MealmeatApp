package com.example.mealmeatapp.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mealmeatapp.R
import com.example.mealmeatapp.ui.theme.MealtimeAppTheme
import com.example.mealmeatapp.view.component.*
import com.example.mealmeatapp.viewmodel.ProfileViewModel
import com.example.mealmeatapp.viewmodel.SettingViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(
    navController: NavController,
    settingViewModel: SettingViewModel,
    profileViewModel: ProfileViewModel
) {
    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                actions = {
                    IconButton(onClick = { navController.navigate("chat") }) {
                        Icon(
                            painter = painterResource(id = R.drawable.chatbot), // Replace with your drawable
                            contentDescription = "Chatbot",
                            tint = colorResource(id = R.color.dark_green)
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            val items = listOf(
                SettingItem(R.drawable.person_fill, "Profile") {
                    navController.navigate("profile")
                },
                SettingItem(R.drawable.notifications_fill, "Notifications") { settingViewModel.onNotificationDialog(true) },
                SettingItem(R.drawable.report, "Suggest or Report Anything") { settingViewModel.onReportDialog(true) },
                SettingItem(R.drawable.star_fill, "Rate Us on Play Store") { settingViewModel.onRateDialog(true) },
                SettingItem(R.drawable.share_fill, "Share the App with Friends") { settingViewModel.onShareDialog(true) },
                SettingItem(R.drawable.verified_user_fill, "Privacy Policy") { settingViewModel.onPrivacyDialog(true) },
                SettingItem(R.drawable.logout, "Log Out") { settingViewModel.onLogoutDialog(true) }
            )

            items.forEach { item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable { item.onClick() }
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = item.iconResId),
                        contentDescription = item.title,
                        tint = colorResource(id = R.color.dark_green),
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.bodyLarge.copy(fontSize = 16.sp)
                    )
                }
            }
        }

        if (settingViewModel.showNotificationDialog.value) NotificationDialog(settingViewModel)
        if (settingViewModel.showReportDialog.value) ReportDialog(settingViewModel, context)
        if (settingViewModel.showRateDialog.value) RateDialog(context) { settingViewModel.onRateDialog(false) }
        if (settingViewModel.showShareDialog.value) ShareDialog(context) { settingViewModel.onShareDialog(false) }
        if (settingViewModel.showPrivacyDialog.value) PrivacyDialog(context) { settingViewModel.onPrivacyDialog(false) }
        if (settingViewModel.showLogoutDialog.value) LogoutDialog(
            onConfirm = {
                settingViewModel.onLogoutDialog(false)
                navController.navigate("mealtime") { popUpTo(navController.graph.startDestinationId) { inclusive = true } }
            },
            onCancel = { settingViewModel.onLogoutDialog(false) }
        )
    }
}





@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun SettingScreenPreview() {
    MealtimeAppTheme {
        SettingScreen(
            navController = rememberNavController(),
            settingViewModel = SettingViewModel(),
            profileViewModel = ProfileViewModel()
        )
    }
}