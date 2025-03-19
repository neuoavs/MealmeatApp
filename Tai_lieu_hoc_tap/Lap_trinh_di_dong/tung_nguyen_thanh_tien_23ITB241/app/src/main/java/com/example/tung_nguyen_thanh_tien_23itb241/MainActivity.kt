package com.example.tung_nguyen_thanh_tien_23itb241

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tung_nguyen_thanh_tien_23itb241.ui.theme.Tung_nguyen_thanh_tien_23ITB241Theme
import android.content.res.Configuration.UI_MODE_NIGHT_YES
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Tung_nguyen_thanh_tien_23ITB241Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    var click by remember { mutableStateOf(false) }
    val changePadding by animateDpAsState(
        if (click) 48.dp else 0.dp,

        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )
    Surface(
        color = MaterialTheme.colorScheme.primary,
        modifier = modifier.padding(horizontal = 8.dp, vertical = 4.dp)) {
        Row (modifier = modifier.padding(24.dp)){
            Column (
                modifier = modifier
                    .weight(1f)
                    .padding(bottom = changePadding)) {
                Text(text = "Hello ")
                Text(text = name, style = MaterialTheme.typography.headlineMedium)
            }

            ElevatedButton(onClick = {
                click = !click

            }) {

                Text(if (click) "Show less" else "Show more")
            }
        }
    }
}

@Composable
fun Greetings(
    modifier: Modifier = Modifier,
    names: List<String> = List(1000) { "$it" }
) {
    LazyColumn(modifier = modifier.padding(vertical = 4.dp)) {
         items(items = names) { name ->
             Greeting(name = name)
         }
    }
}



@Composable
fun MyApp(modifier: Modifier = Modifier) {

    var show by rememberSaveable { mutableStateOf(true)}

    Surface(modifier) {
        if (show) {
            OnboardingScreen(onClickPage = {show = false})
        } else {
            Greetings()
        }
    }
}



@Composable
fun OnboardingScreen(
    onClickPage: () -> Unit,
    modifier: Modifier = Modifier) {

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome to the Basics Codelab!")
        Button(
            modifier = Modifier.padding(vertical = 24.dp),
            onClick = onClickPage
        ) {
            Text("Continue")
        }
    }
}


@Preview(
    showBackground = true,
    widthDp = 320,
    uiMode = UI_MODE_NIGHT_YES,
    name = "GreetingPreviewDark"
)
@Preview(showBackground = true, widthDp = 320)
@Composable
fun OnboardingPreview() {
    Tung_nguyen_thanh_tien_23ITB241Theme {
        MyApp()
    }
}

@Preview(
    showBackground = true,
    widthDp = 320,
    uiMode = UI_MODE_NIGHT_YES,
    name = "GreetingPreviewDark"
)
@Composable
fun GreetingPreview() {
    Tung_nguyen_thanh_tien_23ITB241Theme {
        Greetings()
    }
}
