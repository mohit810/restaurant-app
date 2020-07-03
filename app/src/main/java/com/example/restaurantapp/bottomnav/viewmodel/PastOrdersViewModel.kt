package com.example.restaurantapp.bottomnav.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.restaurantapp.dataprovider.api.DishDetailAPI
import com.example.restaurantapp.dataprovider.dataclass.PastOrdersDetails
import com.example.retrofit.ApiClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class PastOrdersViewModel: ViewModel() {
    private var myAPI: DishDetailAPI? = null
    private  val retrofit= ApiClient.instance
    private val compositeDisposablePastOrdersViewModel: CompositeDisposable
    private var mutableLiveDataCartItem: MutableLiveData<List<PastOrdersDetails>>? =null

    init {


        compositeDisposablePastOrdersViewModel = CompositeDisposable()
    }
    fun initMyApi(){
        myAPI = retrofit.create(DishDetailAPI::class.java)
    }
    fun getmutableLiveDataPastOrdersItems(id:String): MutableLiveData<List<PastOrdersDetails>> {
        if (mutableLiveDataCartItem == null)
            mutableLiveDataCartItem = MutableLiveData()
        getMainCourseItems(id)
        return mutableLiveDataCartItem!!

    }

    private fun getMainCourseItems(id:String){
        compositeDisposablePastOrdersViewModel.addAll(myAPI!!.pastOrders(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({getmaindishes ->

                mutableLiveDataCartItem!!.value = getmaindishes

            },{t:Throwable -> mutableLiveDataCartItem!!.value = null}))
    }
    fun onStop(){
        compositeDisposablePastOrdersViewModel.clear()
    }
}