package com.example.mealmeatapp.view.component
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.mealmeatapp.R
import com.example.mealmeatapp.ui.theme.MealtimeAppTheme
import com.example.mealmeatapp.viewmodel.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    profileViewModel: ProfileViewModel = viewModel()
) {
    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController)
        },
        topBar = {
            TopAppBar(
                title = { Text(text = "")},//stringResource(R.string.profile_info)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.arrow_back_ios),
                            contentDescription = "Back button"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = stringResource(R.string.title_profile),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            ProfileRow(
                label = "Email: ",
                value = profileViewModel.email.value)
            ProfileRow(
                label = "Goal: ",
                value = if (profileViewModel.isDiet.value) stringResource(R.string.diet_on) else stringResource(R.string.diet_off)
            )
            ProfileRow(
                label = "Gender: ",
                value = ""
            )

            ProfileRow(
                label = "Age: ",
                value = ""
            )

            ProfileRow(
                label = "Height: ",
                value =
                if (profileViewModel.heightUnit.value == "cm")
                    "${profileViewModel.heightCm.intValue} cm"
                else
                    "${profileViewModel.heightFeetInches.value.first}'${profileViewModel.heightFeetInches.value.second}"
            )

            ProfileRow(
                label = "Weight: ",
                value = "${profileViewModel.weight.intValue} ${profileViewModel.weightUnit.value}"
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { navController.navigate("profile_set_up") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(text = "Edit Profile")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    MealtimeAppTheme {
        ProfileScreen(
            navController = rememberNavController(),
            profileViewModel = ProfileViewModel())
    }
}

