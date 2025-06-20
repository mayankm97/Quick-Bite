package com.example.quickbite

import android.app.Application
import com.example.quickbite.notification.QuickBiteNotificationManager
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class QuickBiteApp : Application() {

    @Inject
    lateinit var quickBiteNotificationManager: QuickBiteNotificationManager
    override fun onCreate() {
        super.onCreate()
        // Initialize any global resources or configurations here if needed
        quickBiteNotificationManager.createChannels()
        quickBiteNotificationManager.getAndStoreToken() // create the first token and update backend
        // based on newly received token
    }
}