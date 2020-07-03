package com.example.restaurantapp.bottomnav.menu.dishdetail

import android.os.Build
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.restaurantapp.R
import com.example.restaurantapp.cartdatabase.CartDataSource
import com.example.restaurantapp.cartdatabase.CartDatabase
import com.example.restaurantapp.cartdatabase.CartItemEntity
import com.example.restaurantapp.cartdatabase.LocalCartDataSource
import com.example.restaurantapp.eventbus.CountCartEvent
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_dish_detail.*
import org.greenrobot.eventbus.EventBus


class DishDetail : AppCompatActivity() {
lateinit private var  cartDataSource:CartDataSource
    var compositeDisposableDishDetail=CompositeDisposable()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val window: Window = window
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.setStatusBarColor(0x00000000) // transparent
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val flags = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
            window.addFlags(flags)
        }
        setContentView(R.layout.activity_dish_detail)
        cartDataSource = LocalCartDataSource(CartDatabase.getDbInstance(this).cartDAO())
        var dishname = intent.extras!!.getString("dishname")
        var dishprice =intent.extras!!.getString("dishprice")!!.toLong()
        var dishrating = intent.extras!!.getInt("dishrating").toString()
        var dishdescription=intent.extras!!.getString("dishdescription")
        var dishidd = intent.extras!!.getString("dishid")
        val dishimage = intent.extras!!.getString("dishimage")
        dish_details_dishName.setText(dishname)
        dish_details_dishPrice.setText("₹"+ dishprice.toString())
        dish_details_dishRatingtxt.setText(dishrating)
        dish_details_dishDespricption.setText(dishdescription)

        Picasso.get()
            .load(dishimage)
            .into(dish_details_dish_imageView)
        dish_detail_add_to_cartbtn.setOnClickListener {
            var quantity = 1
            val cartItem = CartItemEntity()
            cartItem.dishId = dishidd!!
            cartItem.dishName = dishname
            cartItem.dishPrice = dishprice
            cartItem.dishQuantity = quantity
            cartItem.dishTotalPrice = quantity*dishprice
            cartItem.dishImage = dishimage
            compositeDisposableDishDetail.add(cartDataSource.insertOrReplaceAll(cartItem)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    EventBus.getDefault().postSticky(CountCartEvent(true))
                },{
                    t:Throwable? -> Toast.makeText(this,"Ehh" + t.toString(),Toast.LENGTH_LONG).show()
                })
            )
        }
    }

    override fun onStop() {
        if (compositeDisposableDishDetail != null)
        compositeDisposableDishDetail.clear()
        super.onStop()
    }
}
