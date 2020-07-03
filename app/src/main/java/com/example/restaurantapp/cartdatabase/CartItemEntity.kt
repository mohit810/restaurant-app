package com.example.restaurantapp.cartdatabase

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Cart")
class CartItemEntity {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "dishId")
     var dishId:String = ""
    @NonNull
    @ColumnInfo(name = "quantity")
    var dishQuantity:Int = 0
    @ColumnInfo(name = "dishName")
    var dishName:String? = ""
    @ColumnInfo(name = "dishImage")
    var dishImage:String? =""
    @ColumnInfo(name = "dishPrice")
     var dishPrice:Long = 0
    @ColumnInfo(name = "totalPrice")
    var dishTotalPrice:Long = 0
    @ColumnInfo(name = "uid")
    var uid :String = ""
}