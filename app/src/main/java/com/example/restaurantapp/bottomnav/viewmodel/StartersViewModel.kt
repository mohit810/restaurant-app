package com.example.restaurantapp.bottomnav.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.restaurantapp.dataprovider.api.DishDetailAPI
import com.example.restaurantapp.dataprovider.dataclass.DishesDetails
import com.example.retrofit.ApiClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class StartersViewModel: ViewModel() {
    private var myAPI: DishDetailAPI? = null
    private  val retrofit= ApiClient.instance
    private val compositeDisposableStartersViewModel: CompositeDisposable
    private var mutableLiveDataCartItem: MutableLiveData<List<DishesDetails>>? =null

    init {


        compositeDisposableStartersViewModel = CompositeDisposable()
    }
    fun initMyApi(){
        myAPI = retrofit.create(DishDetailAPI::class.java)
    }
    fun getmutableLiveDataStarterItems(): MutableLiveData<List<DishesDetails>> {
        if (mutableLiveDataCartItem == null)
            mutableLiveDataCartItem = MutableLiveData()
        getStartersItems()
        return mutableLiveDataCartItem!!

    }

    private fun getStartersItems(){
        compositeDisposableStartersViewModel.addAll(myAPI!!.getStarterDishes
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({getstarterdishes ->

                mutableLiveDataCartItem!!.value = getstarterdishes

            },{t:Throwable -> mutableLiveDataCartItem!!.value = null}))
    }
    fun onStop(){
        compositeDisposableStartersViewModel.clear()
    }
}