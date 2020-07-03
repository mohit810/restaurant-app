package com.example.restaurantapp.cartdatabase

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

class LocalCartDataSource(private val cartDAO:CartDAO):CartDataSource {
    override fun getAllCart(uid: String): Flowable<List<CartItemEntity>> {
        return cartDAO.getAllCart(uid)
    }

    override fun countItemInCart(uid: String): Single<Int> {
        return cartDAO.countItemInCart(uid)
    }

    override fun sumPrice(uid: String): Single<Double> {
        return cartDAO.sumPrice(uid)
    }

    override fun getItemInCart(dishId: String, uid: String): Single<CartItemEntity> {
        return cartDAO.getItemInCart(dishId, uid)
    }

    override fun insertOrReplaceAll(vararg cartItems: CartItemEntity): Completable {
        return cartDAO.insertOrReplaceAll(*cartItems)
    }

    override fun updateCart(cart: CartItemEntity): Single<Int> {
        return cartDAO.updateCart(cart)
    }

    override fun deleteCart(cart: CartItemEntity): Single<Int> {
        return cartDAO.deleteCart(cart)
    }

    override fun CleanCart(uid: String): Single<Int> {
        return cartDAO.CleanCart(uid)
    }
}