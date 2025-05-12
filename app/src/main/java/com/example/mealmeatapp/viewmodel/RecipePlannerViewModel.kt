package com.example.mealmeatapp.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.mealmeatapp.apimodel.recipe.Recipe
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.time.LocalDate

/**
 * ViewModel quản lý lịch lên kế hoạch: lưu trữ, đọc và ghi lên Firebase Realtime Database.
 * Cấu trúc RTDB: /schedules/{userEmail}/{dateIso} -> List<Int> (recipeId)
 */
class RecipePlannerViewModel : ViewModel() {
    // schedule: bản đồ ngày -> danh sách Recipe đã lên kế hoạch
    // Use LocalDate as key and SnapshotStateList for Compose observation
    val schedule = mutableStateMapOf<LocalDate, SnapshotStateList<Recipe>>()

    private val db = Firebase.database.reference

    /**
     * Đọc toàn bộ lịch của user từ Firebase
     */
    fun loadSchedule(profileViewModel: ProfileViewModel) {
        val emailKey = profileViewModel.email.value.replace('.', '_')
        val ref = db.child("schedules").child(emailKey)
        ref.addListenerForSingleValueEvent(object: ValueEventListener {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onDataChange(snapshot: DataSnapshot) {
                schedule.clear()
                for (dateSnap in snapshot.children) {
                    val dateStr = dateSnap.key ?: continue
                    val idList = dateSnap.getValue(object: com.google.firebase.database.GenericTypeIndicator<List<Int>>() {})
                        ?: emptyList()
                    val recipes = mutableStateListOf<Recipe>()
                    // GhépRecipe bằng id từ ProfileViewModel.addedRecipe
                    idList.forEach { rid ->
                        profileViewModel.addedRecipe.find { it?.id == rid }?.let { recipes.add(it) }
                    }
                    // Lưu vào state map
                    schedule[LocalDate.parse(dateStr)] = recipes
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.e("RecipePlannerVM", "loadSchedule canceled: ${error.message}")
            }
        })
    }

    /**
     * Kiểm tra xem Recipe đã được lên kế hoạch cho ngày đó chưa
     */
    fun isScheduled(date: LocalDate, recipe: Recipe): Boolean {
        return schedule[date]?.any { it.id == recipe.id } == true
    }

    /**
     * Toggle trạng thái lên kế hoạch cho một ngày cụ thể
     * Nếu chưa, thêm; nếu đã, xóa.
     * Sau đó đồng bộ lên Firebase
     */
    fun toggleSchedule(date: LocalDate, recipe: Recipe, profileViewModel: ProfileViewModel) {
        val emailKey = profileViewModel.email.value.replace('.', '_')
        val ref = db.child("schedules").child(emailKey)
        // Lấy danh sách hiện tại hoặc khởi tạo
        val list = schedule.getOrPut(date) { mutableStateListOf() }
        if (list.any { it.id == recipe.id }) {
            // Xóa
            list.removeAll { it.id == recipe.id }
        } else {
            // Thêm
            list.add(recipe)
        }
        // Đồng bộ lên Firebase: lưu danh sách id
        val dateKey = date.toString() // ISO format
        val idList = list.map { it.id }
        ref.child(dateKey).setValue(idList)
    }
}
