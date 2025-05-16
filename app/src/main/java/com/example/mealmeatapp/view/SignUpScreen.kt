package com.example.mealmeatapp.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mealmeatapp.R
import com.example.mealmeatapp.ui.theme.*
import com.example.mealmeatapp.view.component.*
import com.example.mealmeatapp.viewmodel.ProfileViewModel
import com.example.mealmeatapp.viewmodel.SignUpViewModel

@Composable
fun SignUpScreen(
    navController: NavController,
    signUpViewModel: SignUpViewModel,
    profileViewModel: ProfileViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        /* Title */
        TitleSignUp()
        /* End Title */

        /* Form */
        FormSignUp(
            navController = navController,
            signUpViewModel = signUpViewModel,
            profileViewModel = profileViewModel
        )
        /* End Form */

        Spacer(modifier = Modifier.height(16.dp))

        AlreadyLink(navController)

    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SignUpScreenPreview() {
    MealtimeAppTheme {
        SignUpScreen(
            navController = rememberNavController(),
            signUpViewModel = SignUpViewModel(),
            profileViewModel = ProfileViewModel()
        )
    }
}