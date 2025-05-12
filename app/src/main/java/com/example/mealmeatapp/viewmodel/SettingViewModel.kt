package com.example.mealmeatapp.viewmodel
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel


class SettingViewModel : ViewModel(){

    // Dialog visibility states
    val showThemeDialog = mutableStateOf(false)
    val showNotificationDialog = mutableStateOf(false)
    val showReportDialog = mutableStateOf(false)
    val showRateDialog = mutableStateOf(false)
    val showShareDialog = mutableStateOf(false)
    val showPrivacyDialog = mutableStateOf(false)
    val showLogoutDialog = mutableStateOf(false)
    // Theme selection (to integrate with DataStore later)
    // Notification toggles
    val mealReminders = mutableStateOf(true)
    val recipeNotifications = mutableStateOf(true)
    val appUpdates = mutableStateOf(true)
    // Report input
    val reportText = mutableStateOf("")
    val reportEmail = mutableStateOf("")
  


    fun onNotificationDialog(show: Boolean) { showNotificationDialog.value = show }
    fun toggleMealReminders(on: Boolean) { mealReminders.value = on }
    fun toggleRecipeNotifications(on: Boolean) { recipeNotifications.value = on }
    fun toggleAppUpdates(on: Boolean) { appUpdates.value = on }
    fun onReportDialog(show: Boolean) { showReportDialog.value = show }
    fun updateReportText(text: String) { reportText.value = text }
    fun updateReportEmail(email: String) { reportEmail.value = email }
    fun onRateDialog(show: Boolean) { showRateDialog.value = show }
    fun onShareDialog(show: Boolean) { showShareDialog.value = show }
    fun onPrivacyDialog(show: Boolean) { showPrivacyDialog.value = show }
    fun onLogoutDialog(show: Boolean) { showLogoutDialog.value = show }
}