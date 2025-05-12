package com.example.mealmeatapp.view.component
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mealmeatapp.R
import com.example.mealmeatapp.ui.theme.MealtimeAppTheme
import com.example.mealmeatapp.viewmodel.SettingViewModel

data class SettingItem(
    val iconResId: Int,
    val title: String,
    val onClick: () -> Unit
)



@ExperimentalMaterial3Api
@Composable
fun ThemeDialog(settingViewModel: SettingViewModel) {
    ModalBottomSheet(
        onDismissRequest = { settingViewModel.onThemeDialog(false) },
        sheetState = rememberModalBottomSheetState(),
        containerColor = colorResource(id = R.color.white)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Select Theme", fontWeight = FontWeight.SemiBold, fontSize = 20.sp)
            Spacer(modifier = Modifier.height(16.dp))
            listOf("Light" to R.drawable.light_mode_fill, "Dark" to R.drawable.dark_mode_fill).forEach { (opt, icon) ->
                val selected = settingViewModel.selectedTheme == opt
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable { settingViewModel.selectTheme(opt) }
                        .border(
                            width = if (selected) 2.dp else 0.dp,
                            color = if (selected) colorResource(id = R.color.orange) else Color.Transparent,
                            shape = RoundedCornerShape(12.dp)
                        ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(colorResource(id = R.color.orange).copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(painterResource(icon), null, tint = colorResource(id = R.color.orange))
                        }
                        Spacer(Modifier.width(16.dp))
                        Text(opt, fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium)
                    }
                }
            }
            Spacer(Modifier.height(24.dp))
            Button(onClick = {
                settingViewModel.onThemeDialog(false)
                // TODO: persist theme
            }, Modifier.fillMaxWidth().height(48.dp)) {
                Text("Save")
            }
        }
    }
}

@Composable
fun NotificationDialog(settingViewModel: SettingViewModel) {
    AlertDialog(
        onDismissRequest = { settingViewModel.onNotificationDialog(false) },
        title = { Text("Notifications") },
        text = {
            Column {
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Text("Meal reminders", Modifier.weight(1f))
                    Switch(
                        checked = settingViewModel.mealReminders,
                        onCheckedChange = { settingViewModel.toggleMealReminders(it) }
                    )
                }
                Spacer(Modifier.height(8.dp))
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Text("New recipe notifications", Modifier.weight(1f))
                    Switch(
                        checked = settingViewModel.recipeNotifications,
                        onCheckedChange = { settingViewModel.toggleRecipeNotifications(it) }
                    )
                }
                Spacer(Modifier.height(8.dp))
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Text("App updates", Modifier.weight(1f))
                    Switch(
                        checked = settingViewModel.appUpdates,
                        onCheckedChange = { settingViewModel.toggleAppUpdates(it) }
                    )
                }
            }
        },
        confirmButton = {
            TextButton(onClick = { settingViewModel.onNotificationDialog(false) }) { Text("Save") }
        },
        dismissButton = {
            TextButton(onClick = { settingViewModel.onNotificationDialog(false) }) { Text("Cancel") }
        }
    )
}

@Composable
fun ReportDialog(settingViewModel: SettingViewModel, context: android.content.Context) {
    AlertDialog(
        onDismissRequest = { settingViewModel.onReportDialog(false) },
        title = { Text("Suggest or Report") },
        text = {
            Column {
                OutlinedTextField(
                    value = settingViewModel.reportText,
                    onValueChange = { settingViewModel.updateReportText(it) },
                    label = { Text("Describe issue") }
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = settingViewModel.reportEmail,
                    onValueChange = { settingViewModel.updateReportEmail(it) },
                    label = { Text("Email (optional)") }
                )
            }
        },
        confirmButton = {
            TextButton(onClick = {
                settingViewModel.onReportDialog(false)
                val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:support@yourapp.com")).apply {
                    putExtra(Intent.EXTRA_SUBJECT, "Feedback")
                    putExtra(Intent.EXTRA_TEXT, "${settingViewModel.reportText}\nEmail: ${settingViewModel.reportEmail}")
                }
                context.startActivity(Intent.createChooser(intent, "Send Feedback"))
            }) { Text("Submit") }
        },
        dismissButton = {
            TextButton(onClick = { settingViewModel.onReportDialog(false) }) { Text("Cancel") }
        }
    )
}


@Composable
fun RateDialog(context: android.content.Context, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Rate Us") },
        text = { Text("Support us by rating the app!") },
        confirmButton = {
            TextButton(onClick = {
                onDismiss()
                val uri = try {
                    Uri.parse("market://details?id=${context.packageName}")
                } catch (e: Exception) {
                    Uri.parse("https://play.google.com/store/apps/details?id=${context.packageName}")
                }
                context.startActivity(Intent(Intent.ACTION_VIEW, uri))
            }) { Text("Rate Now") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Later") }
        }
    )
}

@Composable
fun ShareDialog(context: android.content.Context, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Share the App") },
        text = { Text("Share this app with friends!") },
        confirmButton = {
            TextButton(onClick = {
                onDismiss()
                val shareIntent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, "Check out this app: https://play.google.com/store/apps/details?id=${context.packageName}")
                }
                context.startActivity(Intent.createChooser(shareIntent, "Share App"))
            }) { Text("Share") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}

@Composable
fun PrivacyDialog(context: android.content.Context, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Privacy Policy") },
        text = { Text("View our privacy policy.") },
        confirmButton = {
            TextButton(onClick = {
                onDismiss()
                context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://yourapp.com/privacy")))
            }) { Text("View") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}

@Composable
fun LogoutDialog(onConfirm: () -> Unit, onCancel: () -> Unit) {
    AlertDialog(
        onDismissRequest = onCancel,
        title = { Text("Log Out") },
        text = { Text("Are you sure you want to log out?") },
        confirmButton = {
            TextButton(onClick = onConfirm) { Text("Log Out") }
        },
        dismissButton = {
            TextButton(onClick = onCancel) { Text("Cancel") }
        }
    )
}


