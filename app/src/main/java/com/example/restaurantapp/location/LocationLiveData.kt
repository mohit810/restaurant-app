package com.example.restaurantapp.locationwithlivedata

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.location.Location
import androidx.lifecycle.LiveData
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import java.util.*

class LocationLiveData(context: Context) : LiveData<LocationModel>() {
    lateinit var txt:String
    var geocoder        = Geocoder(context, Locale.getDefault())
    public lateinit var address  :String
    public lateinit var city     :String
    public lateinit var local    :String
    private var fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    override fun onInactive() {
        super.onInactive()
       // fusedLocationClient.removeLocationUpdates(locationCallback)
    }


    @SuppressLint("MissingPermission")
    override fun onActive() {
        super.onActive()
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                location?.also {
                    var addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                    address  = addresses[0].getAddressLine(0)
                    city     = addresses[0].locality
                    local    = addresses[0].subLocality
                    var txt = local + ", " + city
                    setLocationData(it,txt)
                }
            }
        startLocationUpdates()
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            //locationCallback,
            null
        )
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            locationResult ?: return
            for (location in locationResult.locations) {
                var addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
//                address  = addresses[0].getAddressLine(0)
  //              city     = addresses[0].locality
    //            local    = addresses[0].subLocality
                txt = addresses[0].subLocality + ", " + addresses[0].locality
                    //local + ", " + city
                setLocationData(location,txt)

            }
        }
    }

    private fun setLocationData(location: Location,txt:String) {

        value = LocationModel(
            longitude = location.longitude,
            latitude = location.latitude,
            addressgps = txt
        )
    }

    companion object {
        val locationRequest: LocationRequest = LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        }
    }
}

data class LocationModel(
    val addressgps:String,
    val longitude: Double,
    val latitude: Double
)
