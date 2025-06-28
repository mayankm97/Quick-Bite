package com.example.quickbite

import Mustard
import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.OvershootInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.quickbite.data.FoodApi
import com.example.quickbite.data.FoodHubSession
import com.example.quickbite.ui.QuickBiteNavHost
import com.example.quickbite.ui.features.auth.AuthScreen
import com.example.quickbite.ui.features.auth.login.SignInScreen
import com.example.quickbite.ui.features.auth.signup.SignUpScreen
import com.example.quickbite.ui.features.notifications.NotificationsList
import com.example.quickbite.ui.features.notifications.NotificationsViewModel
import com.example.quickbite.ui.home.DeliveriesScreen
import com.example.quickbite.ui.navigation.AddMenu
import com.example.quickbite.ui.navigation.AuthScreen
import com.example.quickbite.ui.navigation.Home
import com.example.quickbite.ui.navigation.ImagePicker
import com.example.quickbite.ui.navigation.Login
import com.example.quickbite.ui.navigation.MenuList
import com.example.quickbite.ui.navigation.Notification
import com.example.quickbite.ui.navigation.OrderDetails
import com.example.quickbite.ui.navigation.OrderList
import com.example.quickbite.ui.navigation.SignUp
import com.example.quickbite.ui.orders.details.OrderDetailsScreen
import com.example.quickbite.ui.orders.list.OrdersListScreen
import com.example.quickbite.ui.theme.QuickBiteTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseQuickBiteActivity() {
    var showSplashScreen = true
    @Inject
    lateinit var foodApi: FoodApi
    @Inject
    lateinit var session: FoodHubSession

    sealed class BottomNavItem(
        val route: Any,
        val icon: Int,
    ) {
        object Home : BottomNavItem(com.example.quickbite.ui.navigation.Home, R.drawable.ic_home)
        object Notification : BottomNavItem(com.example.quickbite.ui.navigation.Notification, R.drawable.ic_notification)
        object Orders : BottomNavItem(OrderList, R.drawable.ic_orders)
    }

    @OptIn(ExperimentalSharedTransitionApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                showSplashScreen
            }
            setOnExitAnimationListener { screen ->
                val zoomX = ObjectAnimator.ofFloat(
                    screen.iconView,
                    View.SCALE_X,
                    0.4f,
                    0f
                )
                val zoomY = ObjectAnimator.ofFloat(
                    screen.iconView,
                    View.SCALE_Y,
                    0.4f,
                    0f
                )
                zoomX.duration = 500
                zoomY.duration = 500
                zoomX.interpolator = OvershootInterpolator()
                zoomY.interpolator = OvershootInterpolator()
                zoomX.doOnEnd {
                    screen.remove()
                }
                zoomY.doOnEnd {
                    screen.remove()
                }
                zoomY.start()
                zoomX.start()
            }
        }
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QuickBiteTheme {
                var shouldShowBottomNav = remember {
                    mutableStateOf(false)
                }
                val navItems = listOf(
                    BottomNavItem.Home,
                    BottomNavItem.Notification,
                    BottomNavItem.Orders
                )
                val navController = rememberNavController()
                val notificationViewModel : NotificationsViewModel = hiltViewModel()
                val unreadCount = notificationViewModel.unreadCount.collectAsStateWithLifecycle()

                LaunchedEffect(key1 = true) {
                    viewModel.event.collectLatest {
                        when (it) {
                            is HomeViewModel.HomeEvent.NavigateToOrderDetail -> {
                                navController.navigate(OrderDetails(it.orderID))
                            }
                        }
                    }
                }
                Scaffold(modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        val currentRoute = navController.currentBackStackEntryAsState().value?.destination

                        AnimatedVisibility(visible = shouldShowBottomNav.value) {
                            NavigationBar(
                                containerColor = Color.White
                            ) {
                                navItems.forEach { item->
                                    val selected = currentRoute?.hierarchy?.any { it.route == item.route::class.qualifiedName } == true

                                    NavigationBarItem(
                                        selected = selected,
                                        onClick = {
                                            navController.navigate(item.route)
                                        },
                                        icon = {
                                            Box {
                                                Icon(
                                                    painter = painterResource(id = item.icon),
                                                    contentDescription = null,
                                                    tint = if (selected) MaterialTheme.colorScheme.primary else Color.Gray,
                                                    modifier = Modifier.align(Alignment.Center)
                                                    )

                                                if(item.route == Notification && unreadCount.value > 0) {
                                                    ItemCount(unreadCount.value)
                                                }
                                            }
                                        }
                                    )
                                }
                            }
                        }

                    }) { innerPadding ->
                    // since we want the animation to be global, we define it
                    // under NavHost and not inside individual composable
                    SharedTransitionLayout {
                        QuickBiteNavHost (
                            navController = navController,
                            if (session.getToken() != null) Home else AuthScreen,
                            modifier = Modifier.padding(innerPadding)
                        ) {
                            composable<SignUp> {
                                shouldShowBottomNav.value = false
                                SignUpScreen(navController)
                            }
                            composable<AuthScreen> {
                                shouldShowBottomNav.value = false
                                AuthScreen(navController, false)
                            }
                            composable<Login> {
                                shouldShowBottomNav.value = false
                                SignInScreen(navController, false)
                            }
                            composable<Home> {
                                shouldShowBottomNav.value = true
                                DeliveriesScreen(navController)
                            }
                            composable<Notification> {
                                // this effect only runs when all compositions are done
                                SideEffect {
                                    shouldShowBottomNav.value = true
                                }
                                NotificationsList(navController, notificationViewModel)
                            }
                            composable<OrderList> {
                                shouldShowBottomNav.value = true
                                OrdersListScreen(navController)
                            }
                            composable<OrderDetails> {
                                shouldShowBottomNav.value = false
                                val orderID = it.toRoute<OrderDetails>().orderId
                                OrderDetailsScreen(orderID, navController)
                            }
//                            composable<MenuList> {
//                                shouldShowBottomNav.value = true
//                                ListMenuItemsScreen(navController, this)
//                            }
//                            composable<AddMenu> {
//                                shouldShowBottomNav.value = false
//                                AddMenuItemScreen(navController)
//                            }
//                            composable<ImagePicker> {
//                                shouldShowBottomNav.value = false
//                                ImagePickerScreen(navController)
//                            }
                        }
                    }
                }
            }
        }
        // ::foodApi is a Kotlin reflection reference to a property called foodApi
        // ::something means "give me a reference to 'something'"
        if (::foodApi.isInitialized) {
            Log.d("MainActivity", "FoodApi initialized")
        }
        CoroutineScope(Dispatchers.IO).launch {
            delay(3000)
            showSplashScreen = false
        }
    }
}

@Composable
fun BoxScope.ItemCount(count: Int) {
    Box(
        modifier = Modifier
            .size(16.dp)
            .clip(CircleShape)
            .background(Mustard)
            .align(Alignment.TopEnd)
    ) {
        Text(
            text = "${count}",
            modifier = Modifier
                .align(Alignment.Center),
            color = Color.White,
            style = TextStyle(fontSize = 10.sp)
        )
    }
}
