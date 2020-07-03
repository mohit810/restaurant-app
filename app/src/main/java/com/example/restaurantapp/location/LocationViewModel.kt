package com.example.restaurantapp.locationwithlivedata

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.restaurantapp.locationwithlivedata.LocationLiveData

class LocationViewModel(application: Application) : AndroidViewModel(application) {

    private val locationData = LocationLiveData(application)

    fun getLocationData() = locationData
}
