package com.example.restaurantapp.dataprovider.api

import com.example.restaurantapp.dataprovider.dataclass.DishesDetails
import com.example.restaurantapp.dataprovider.dataclass.PastOrdersDetails
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface DishDetailAPI {

    @get:GET(value = "getdishes?id=starters")
    val getStarterDishes: Observable<List<DishesDetails>>
    @get:GET(value = "getdishes?id=sweet")
    val getSweetDishes: Observable<List<DishesDetails>>
    @get:GET(value = "getdishes?id=mains&idd=side")
    val getMainDishes: Observable<List<DishesDetails>>
    @get:GET(value = "getdishes")
    val getDishes: Observable<MutableList<DishesDetails>>
    @GET(value = "lastorder")
    fun pastOrders(@Query("mob",encoded = false)id: String):Observable<List<PastOrdersDetails>>
}