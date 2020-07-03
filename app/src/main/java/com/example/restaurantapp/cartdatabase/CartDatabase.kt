package com.example.restaurantapp.cartdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(version = 1,entities = [CartItemEntity::class],exportSchema = false)
abstract class CartDatabase:RoomDatabase() {
    abstract fun cartDAO():CartDAO

    companion object{
        private var dbinstance:CartDatabase?=null
        fun getDbInstance(context:Context):CartDatabase{
            if (dbinstance==null)
                dbinstance = Room.databaseBuilder<CartDatabase>(context,CartDatabase::class.java!!,"RestaurantDb").build()
            return dbinstance!!
        }
    }
}