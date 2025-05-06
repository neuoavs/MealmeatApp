package com.example.mealmeatapp
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.example.example.Recipe
import com.example.mealmeatapp.apimodel.request.ApiClient
import com.example.mealmeatapp.apimodel.result.RandomRecipesResponse
import com.example.mealmeatapp.ui.theme.MealmeatAppTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MealmeatAppTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    RecipeListScreen()
                }
            }
        }
    }
}

@Composable
fun RecipeListScreen() {
    var recipes by remember { mutableStateOf<List<Recipe>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        ApiClient.apiService.getRamdomRepice().enqueue(object : Callback<RandomRecipesResponse> {
            override fun onResponse(
                call: Call<RandomRecipesResponse>,
                response: Response<RandomRecipesResponse>
            ) {
                recipes = response.body()?.recipes.orEmpty()
                isLoading = false
            }
            override fun onFailure(call: Call<RandomRecipesResponse>, t: Throwable) {
                isLoading = false
            }
        })
    }

    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)) {
            items(items = recipes) { recipe ->
                RecipeCard(recipe)
            }
        }
    }
}

@Composable
fun RecipeCard(recipe: Recipe) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(modifier = Modifier.padding(8.dp)) {
            Image(
                painter = rememberAsyncImagePainter(recipe.imageUrl),
                contentDescription = recipe.title,
                modifier = Modifier
                    .size(100.dp)
                    .padding(end = 8.dp),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = recipe.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Ready in ${recipe.readyInMinutes} min | Servings: ${recipe.servings}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
