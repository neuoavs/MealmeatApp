package com.example.mealmeatapp.view

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.mealmeatapp.R
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mealmeatapp.viewmodel.ProfileSetUpViewModel
import com.example.mealmeatapp.view.component.*
import com.example.mealmeatapp.viewmodel.HomeViewModel
import com.example.mealmeatapp.viewmodel.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProfileSetupScreen(
    navController: NavController,
    profileSetUpViewModel: ProfileSetUpViewModel,
    profileViewModel: ProfileViewModel,
    homeViewModel: HomeViewModel
) {
    // Khởi tạo lại trạng thái khi vào màn hình chỉnh sửa profile
    LaunchedEffect(Unit) {
        profileSetUpViewModel.resetState()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                colorResource(id = R.color.light_cream)
            )
    ) {
        // Header với nút điều hướng
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            HeaderProfile(
                navController = navController,
                profileSetUpViewModel = profileSetUpViewModel
            )
        }

        // Nội dung chính theo từng bước
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            when (profileSetUpViewModel.currentStep.intValue) {
                1 -> {
                    StepOne(
                        profileSetUpViewModel = profileSetUpViewModel
                    )
                }
                2 -> {
                    StepTwo(
                        profileSetUpViewModel = profileSetUpViewModel
                    )
                }
                3 -> {
                    StepThree(
                        profileSetUpViewModel = profileSetUpViewModel
                    )
                }
                4 -> {
                    StepFour(
                        profileSetUpViewModel = profileSetUpViewModel
                    )
                }
                5 -> {
                    StepFive(
                        profileSetUpViewModel = profileSetUpViewModel
                    )
                }
            }
        }

        // Dialog hiển thị tóm tắt hoặc thoát
        ProfileDialog(
            navController = navController,
            profileSetUpViewModel = profileSetUpViewModel,
            profileViewModel = profileViewModel,
            homeViewModel = homeViewModel
        )

        // Footer với nút Next và StepCircle
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            NextButtonProfile(
                profileSetUpViewModel = profileSetUpViewModel,
                profileViewModel = profileViewModel,
                homeViewModel = homeViewModel
            )
            Spacer(modifier = Modifier.height(16.dp))
            StepCircle(
                profileSetUpViewModel = profileSetUpViewModel
            )
        }
    }
}

/**
 * Dialog hiển thị tóm tắt thông tin profile hoặc dialog thoát sau khi xác nhận.
 * @param navController Bộ điều hướng để chuyển hướng giữa các màn hình.
 * @param profileSetUpViewModel ViewModel quản lý trạng thái thiết lập hồ sơ.
 * @param profileViewModel ViewModel quản lý dữ liệu hồ sơ.
 * @param homeViewModel ViewModel quản lý dữ liệu chính (home).
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProfileDialog(
    navController: NavController,
    profileSetUpViewModel: ProfileSetUpViewModel,
    profileViewModel: ProfileViewModel,
    homeViewModel: HomeViewModel
) {
    // Hiển thị dialog tóm tắt khi hoàn tất bước 5
    if (profileSetUpViewModel.showSummaryDialog.value) {
        AlertDialog(
            onDismissRequest = { profileSetUpViewModel.showSummaryDialog.value = false },
            title = { Text("Confirm Profile") },
            text = {
                Column {
                    Text("Age: ${profileSetUpViewModel.age.intValue}")
                    Text("Gender: ${profileSetUpViewModel.getGenderValueString()}")
                    Text("Height: ${profileSetUpViewModel.getHeightValueString()}")
                    Text("Weight: ${profileSetUpViewModel.getWeightValueString()}")
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        profileSetUpViewModel.onConfirmClick(navController, profileViewModel, homeViewModel)
                    }
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                Button(
                    onClick = { profileSetUpViewModel.showSummaryDialog.value = false }
                ) {
                    Text("Cancel")
                }
            }
        )
    }

    // Hiển thị dialog thoát sau khi xác nhận profile
    if (profileSetUpViewModel.showExitDialog.value) {
        AlertDialog(
            onDismissRequest = { profileSetUpViewModel.onCancelExitClick() },
            title = { Text("Exit to Settings") },
            text = { Text("Do you want to exit to Settings?") },
            confirmButton = {
                Button(
                    onClick = { profileSetUpViewModel.onExitClick(navController) }
                ) {
                    Text("Exit")
                }
            },
            dismissButton = {
                Button(
                    onClick = { profileSetUpViewModel.onCancelExitClick() }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}