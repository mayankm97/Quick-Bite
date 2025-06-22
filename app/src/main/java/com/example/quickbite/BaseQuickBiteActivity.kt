package com.example.quickbite

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import com.example.quickbite.notification.QuickBiteMessagingService
import dagger.hilt.android.AndroidEntryPoint

// common point for notifications in all variants
@AndroidEntryPoint
abstract class BaseQuickBiteActivity : ComponentActivity(){

    val viewModel by viewModels<HomeViewModel>()

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        processIntent(intent, viewModel)
    }

    protected fun processIntent(intent: Intent, viewModel: HomeViewModel) {
        if (intent.hasExtra(QuickBiteMessagingService.ORDER_ID)) {
            val orderID = intent.getStringExtra(QuickBiteMessagingService.ORDER_ID)
            viewModel.navigateToOrderDetail(orderID!!)
            intent.removeExtra(QuickBiteMessagingService.ORDER_ID)
        }
    }
}