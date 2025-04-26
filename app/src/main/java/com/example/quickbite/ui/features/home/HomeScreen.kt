package com.example.quickbite.ui.features.home

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.quickbite.data.models.Category
import com.example.quickbite.ui.theme.Primary

@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel = hiltViewModel()) {
    Column(modifier = Modifier.fillMaxSize()) {
        val uiState = viewModel.uiState.collectAsState()
        when (uiState.value) {
            is HomeViewModel.HomeScreenState.Loading -> {
                Text(text = "Loading")
            }

            is HomeViewModel.HomeScreenState.Empty -> {
                Text(text = "Empty")
            }

            is HomeViewModel.HomeScreenState.Success -> {
                val categories = viewModel.categories
                CategoriesList(categories = categories, onCategorySelected = {
                    navController.navigate("category/${it.id}")
                })

//                RestaurantList(
//                    restaurants = viewModel.restaurants,
//                    animatedVisibilityScope,
//                    onRestaurantSelected = {
//                        viewModel.onRestaurantSelected(it)
//                    })
            }
        }
    }
}

@Composable
fun CategoriesList(categories: List<Category>, onCategorySelected: (Category) -> Unit) {
    LazyRow {
        items(categories) {
            CategoryItem(category = it, onCategorySelected = onCategorySelected)
        }
    }
}


@Composable
fun CategoryItem(category: Category, onCategorySelected: (Category) -> Unit) {

    Column(modifier = Modifier
        .padding(8.dp)
        .height(90.dp)
        .width(60.dp)
        .clickable { onCategorySelected(category) }
        .shadow(
            elevation = 16.dp,
            shape = RoundedCornerShape(45.dp),
            ambientColor = Color.Gray.copy(alpha = 0.8f),
            spotColor = Color.Gray.copy(alpha = 0.8f)
        )
        .background(color = Color.White)
        .clip(RoundedCornerShape(45.dp))
        .padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = CenterHorizontally) {
        AsyncImage(
            model = category.imageUrl,
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .shadow(
                    elevation = 16.dp,
                    shape = CircleShape,
                    ambientColor = Primary,
                    spotColor = Primary
                )
                .clip(CircleShape),
            contentScale = ContentScale.Inside
        )
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = category.name, style = TextStyle(fontSize = 10.sp), textAlign = TextAlign.Center
        )
    }
}