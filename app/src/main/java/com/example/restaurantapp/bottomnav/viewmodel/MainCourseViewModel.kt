package com.example.restaurantapp.bottomnav.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.restaurantapp.dataprovider.api.DishDetailAPI
import com.example.restaurantapp.dataprovider.dataclass.DishesDetails
import com.example.retrofit.ApiClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainCourseViewModel: ViewModel() {
    private var myAPI: DishDetailAPI? = null
    private  val retrofit= ApiClient.instance
    private val compositeDisposableMenuViewModel: CompositeDisposable
    private var mutableLiveDataCartItem: MutableLiveData<List<DishesDetails>>? =null

    init {


        compositeDisposableMenuViewModel = CompositeDisposable()
    }
    fun initMyApi(){
        myAPI = retrofit.create(DishDetailAPI::class.java)
    }
    fun getmutableLiveDataMainCourseItems(): MutableLiveData<List<DishesDetails>> {
        if (mutableLiveDataCartItem == null)
            mutableLiveDataCartItem = MutableLiveData()
        getMainCourseItems()
        return mutableLiveDataCartItem!!

    }

    private fun getMainCourseItems(){
        compositeDisposableMenuViewModel.addAll(myAPI!!.getMainDishes
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({getmaindishes ->

                mutableLiveDataCartItem!!.value = getmaindishes

            },{t:Throwable -> mutableLiveDataCartItem!!.value = null}))
    }
    fun onStop(){
        compositeDisposableMenuViewModel.clear()
    }
}