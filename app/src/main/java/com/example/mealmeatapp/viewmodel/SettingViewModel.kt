package com.example.mealmeatapp.viewmodel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel


class SettingViewModel : ViewModel(){

    // Dialog visibility states
    var showThemeDialog by mutableStateOf(false)
        private set
    var showNotificationDialog by mutableStateOf(false)
        private set
    var showReportDialog by mutableStateOf(false)
        private set
    var showRateDialog by mutableStateOf(false)
        private set
    var showShareDialog by mutableStateOf(false)
        private set
    var showPrivacyDialog by mutableStateOf(false)
        private set
    var showLogoutDialog by mutableStateOf(false)
        private set

    // Theme selection (to integrate with DataStore later)
    var selectedTheme by mutableStateOf("System Default")
        private set

    // Notification toggles
    var mealReminders by mutableStateOf(true)
        private set
    var recipeNotifications by mutableStateOf(true)
        private set
    var appUpdates by mutableStateOf(true)
        private set

    // Report input
    var reportText by mutableStateOf("")
        private set
    var reportEmail by mutableStateOf("")
        private set

    // Public APIs to update state
    fun onThemeDialog(show: Boolean) { showThemeDialog = show }
    fun selectTheme(theme: String) { selectedTheme = theme }
    fun onNotificationDialog(show: Boolean) { showNotificationDialog = show }
    fun toggleMealReminders(on: Boolean) { mealReminders = on }
    fun toggleRecipeNotifications(on: Boolean) { recipeNotifications = on }
    fun toggleAppUpdates(on: Boolean) { appUpdates = on }
    fun onReportDialog(show: Boolean) { showReportDialog = show }
    fun updateReportText(text: String) { reportText = text }
    fun updateReportEmail(email: String) { reportEmail = email }
    fun onRateDialog(show: Boolean) { showRateDialog = show }
    fun onShareDialog(show: Boolean) { showShareDialog = show }
    fun onPrivacyDialog(show: Boolean) { showPrivacyDialog = show }
    fun onLogoutDialog(show: Boolean) { showLogoutDialog = show }
}