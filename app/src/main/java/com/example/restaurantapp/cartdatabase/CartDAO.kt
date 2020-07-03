package com.example.restaurantapp.cartdatabase


import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface CartDAO {
    @Query("SELECT * FROM Cart WHERE uid=:uid")
    fun getAllCart(uid:String):Flowable<List<CartItemEntity>>
    @Query("SELECT count(*) FROM Cart where uid=:uid ")
    fun countItemInCart(uid:String):Single<Int>
    @Query("SELECT SUM(dishPrice*quantity) FROM Cart WHERE uid=:uid")
    fun sumPrice(uid:String): Single<Double>
    @Query("SELECT * FROM Cart WHERE dishId=:dishId AND uid=:uid")
    fun getItemInCart(dishId:String,uid:String):Single<CartItemEntity>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrReplaceAll(vararg cartItems:CartItemEntity):Completable
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateCart(cart:CartItemEntity):Single<Int>
    @Delete
    fun deleteCart(cart:CartItemEntity):Single<Int>
    @Query("DELETE FROM Cart WHERE uid=:uid")
    fun CleanCart( uid: String ):Single<Int>
}