package com.example.restaurantapp.cartdatabase

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

interface CartDataSource {

    fun getAllCart(uid:String): Flowable<List<CartItemEntity>>

    fun countItemInCart(uid:String): Single<Int>

    fun sumPrice(uid:String): Single<Double>

    fun getItemInCart(dishId:String,uid:String):Single<CartItemEntity>

    fun insertOrReplaceAll(vararg cartItems:CartItemEntity): Completable

    fun updateCart(cart:CartItemEntity):Single<Int>

    fun deleteCart(cart:CartItemEntity):Single<Int>

    fun CleanCart( uid: String ):Single<Int>
}