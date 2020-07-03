package com.example.retrofit

import com.example.restaurantapp.dataprovider.dataclass.Details
import com.example.restaurantapp.dataprovider.dataclass.DishesDetails
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface Apis {
    @GET("user/{id}")
    fun getDetails(@Path(value = "id") id: String): Call<Details>
    @Multipart
    @POST("login")
     fun getUser(
                 @Part("email") id:RequestBody,
                 @Part("password") pwd:RequestBody): Call<List<Details>>
    @GET("placeorder")
    fun placingOrder(@Query("starter",encoded = false) starter:Int,
                     @Query("main",encoded = false) main:Int?,
                     @Query("bread",encoded = false) bread:Int?,
                     @Query("sweet",encoded = false) sweet:Int?,
                     @Query("mob",encoded = false)mob:String?,
                     @Query("qone",encoded = false) qone:Int,
                     @Query("qtwo",encoded = false) qtwo:Int?,
                     @Query("qthree",encoded = false) qthree:Int?,
                     @Query("qfour",encoded = false) qfour:Int?): Call<List<DishesDetails>>
    @Multipart
    @POST("create")
    fun uploadData(
    @Part(value ="name") name:RequestBody,
    @Part(value ="email") email:RequestBody,
    @Part(value ="password") password:RequestBody,
    @Part(value ="mobile") mobile:RequestBody,
    @Part(value ="address")address:RequestBody,
    @Part profilePic:MultipartBody.Part
    ):Call<List<Details>>
}