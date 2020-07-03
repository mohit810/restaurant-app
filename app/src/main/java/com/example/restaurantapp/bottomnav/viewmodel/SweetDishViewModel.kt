package com.example.restaurantapp.bottomnav.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.restaurantapp.dataprovider.api.DishDetailAPI
import com.example.restaurantapp.dataprovider.dataclass.DishesDetails
import com.example.retrofit.ApiClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SweetDishViewModel: ViewModel() {
    private var myAPI: DishDetailAPI? = null
    private  val retrofit= ApiClient.instance
    private val compositeDisposableSweetDishViewModel: CompositeDisposable
    private var mutableLiveDataCartItem: MutableLiveData<List<DishesDetails>>? =null

    init {


        compositeDisposableSweetDishViewModel = CompositeDisposable()
    }
    fun initMyApi(){
        myAPI = retrofit.create(DishDetailAPI::class.java)
    }
    fun getmutableLiveDataSweetDishItems(): MutableLiveData<List<DishesDetails>> {
        if (mutableLiveDataCartItem == null)
            mutableLiveDataCartItem = MutableLiveData()
        getSweetDishItems()
        return mutableLiveDataCartItem!!

    }

    private fun getSweetDishItems(){
        compositeDisposableSweetDishViewModel.addAll(myAPI!!.getSweetDishes
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({getsweetdishes ->

                mutableLiveDataCartItem!!.value = getsweetdishes

            },{t:Throwable -> mutableLiveDataCartItem!!.value = null}))
    }
    fun onStop(){
        compositeDisposableSweetDishViewModel.clear()
    }
}