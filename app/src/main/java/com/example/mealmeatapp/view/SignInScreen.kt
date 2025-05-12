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
import com.example.mealmeatapp.viewmodel.SignInViewModel

@Composable
fun SignInScreen(
    navController: NavController,
    signInViewModel: SignInViewModel,
    profileViewModel: ProfileViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        TitleSignIn()

        Spacer(modifier = Modifier.height(16.dp))

        FormSignIn(
            navController = navController,
            profileViewModel = profileViewModel,
            signInViewModel = signInViewModel
        )

        CreateLink(navController)

        Text(
            text = "Or",
            color = colorResource(id = R.color.gray),
            style = MaterialTheme.typography.bodySmall
        )

        Spacer(modifier = Modifier.height(16.dp))

        GoogleAuth()
    }
}


@Preview(showBackground = true)
@Composable
fun SignInScreenPreview() {
    MealtimeAppTheme {
        SignInScreen(
            navController = rememberNavController(),
            signInViewModel = SignInViewModel(),
            profileViewModel = ProfileViewModel()
        )
    }
}
