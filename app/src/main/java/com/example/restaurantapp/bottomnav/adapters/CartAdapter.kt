package com.example.restaurantapp.bottomnav.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton
import com.example.restaurantapp.R
import com.example.restaurantapp.cartdatabase.CartDataSource
import com.example.restaurantapp.cartdatabase.CartDatabase
import com.example.restaurantapp.cartdatabase.CartItemEntity
import com.example.restaurantapp.cartdatabase.LocalCartDataSource
import com.example.restaurantapp.eventbus.UpdateItemInCart
import com.squareup.picasso.Picasso
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.cart_item_layout.view.*
import org.greenrobot.eventbus.EventBus

class CartAdapter(internal var context: Context,internal var cartItemsCartAdapter: List<CartItemEntity>):RecyclerView.Adapter<CartAdapter.MyViewHolder>() {
    var compositeDisposableCartAdapter= CompositeDisposable()
    internal var cartDataSourceCartAdapter:CartDataSource
    init {
        cartDataSourceCartAdapter =  LocalCartDataSource(CartDatabase.getDbInstance(context).cartDAO())
    }
    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    {
        lateinit var image_cartItem:ImageView
        lateinit var txt_foodName:TextView
        lateinit var txt_foodPrice:TextView
        lateinit var quantity_numberButton:ElegantNumberButton
        init {
            image_cartItem = itemView.cart_recycler_thumbnail
            txt_foodName = itemView.cart_recycler_titletxt
            txt_foodPrice =itemView.cart_recycler_pricetxt
            quantity_numberButton = itemView.cart_recycler_quantity_btn
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.cart_item_layout,parent,false))
    }

    override fun getItemCount(): Int {
        return cartItemsCartAdapter.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Picasso.get().load(cartItemsCartAdapter[position].dishImage).into(holder.image_cartItem)
        holder.txt_foodName.text = cartItemsCartAdapter[position].dishName
        holder.txt_foodPrice.text = "₹ "+cartItemsCartAdapter[position].dishPrice.toString()
        holder.quantity_numberButton.number = cartItemsCartAdapter[position].dishQuantity.toString()
        //quantity change event
        holder.quantity_numberButton.setOnValueChangeListener { view, oldValue, newValue ->
            cartItemsCartAdapter[position].dishQuantity = newValue
            EventBus.getDefault().postSticky(UpdateItemInCart(cartItemsCartAdapter[position]))
        }
    }

    fun getItemAtPosition(pos: Int): CartItemEntity {
        return cartItemsCartAdapter[pos]
    }


}