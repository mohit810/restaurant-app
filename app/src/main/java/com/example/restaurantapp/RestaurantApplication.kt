package com.example.restaurantapp

import android.app.Application
import com.example.restaurantapp.dataprovider.sharedpref.Prefs

class RestaurantApplication:Application() {
    override fun onCreate() {
        Prefs.init(applicationContext)
        super.onCreate()
    }

}