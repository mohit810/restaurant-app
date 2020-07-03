package com.example.restaurantapp.bottomnav.viewmodel

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.restaurantapp.cartdatabase.CartDataSource
import com.example.restaurantapp.cartdatabase.CartDatabase
import com.example.restaurantapp.cartdatabase.CartItemEntity
import com.example.restaurantapp.cartdatabase.LocalCartDataSource
import com.example.restaurantapp.dataprovider.dataclass.Details
import com.example.restaurantapp.dataprovider.dataclass.DishesDetails
import com.example.retrofit.ApiClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CartViewModel:ViewModel() {
    private val compositeDisposableCartViewModel:CompositeDisposable
    private var cartDataSourceCartViewModel:CartDataSource?=null
    private var mutableLiveDataCartItem:MutableLiveData<List<CartItemEntity>>? =null
private var mutableLiveDeleteDataCartItem:Int? = null
    init {
        compositeDisposableCartViewModel = CompositeDisposable()
    }
    fun initcartDataSourceCartViewModel(context: Context){
        cartDataSourceCartViewModel = LocalCartDataSource(CartDatabase.getDbInstance(context).cartDAO())
    }
fun getmutableLiveDataCartItem():MutableLiveData<List<CartItemEntity>>{
    if (mutableLiveDataCartItem == null)
        mutableLiveDataCartItem = MutableLiveData()
        getCartItems()
        return mutableLiveDataCartItem!!

}

    private fun getCartItems(){
        compositeDisposableCartViewModel.addAll(cartDataSourceCartViewModel!!.getAllCart("")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({cartItemCartViewModel ->

                mutableLiveDataCartItem!!.value = cartItemCartViewModel

            },{t:Throwable -> mutableLiveDataCartItem!!.value = null}))
    }
    fun deletemutableLiveDataCartItem():MutableLiveData<List<CartItemEntity>>{
        if (mutableLiveDataCartItem == null)
            mutableLiveDataCartItem = MutableLiveData()
        deleteCartItems()
        return mutableLiveDataCartItem!!

    }

    private fun deleteCartItems(){
        compositeDisposableCartViewModel.addAll(cartDataSourceCartViewModel!!.CleanCart("")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({cartItemCartViewModel ->

                mutableLiveDeleteDataCartItem = cartItemCartViewModel

            },{t:Throwable -> mutableLiveDataCartItem!!.value = null}))
    }
    fun onStop(){
        compositeDisposableCartViewModel.clear()
    }
}