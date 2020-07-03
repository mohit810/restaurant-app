package com.example.restaurantapp.bottomnav.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.restaurantapp.dataprovider.api.DishDetailAPI
import com.example.restaurantapp.dataprovider.dataclass.DishesDetails
import com.example.retrofit.ApiClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SearchViewModel: ViewModel() {
    private var myAPI: DishDetailAPI? = null
    private  val retrofit= ApiClient.instance
    private val compositeDisposableSearchViewModel: CompositeDisposable
    private var mutableLiveDataCartItem: MutableLiveData<MutableList<DishesDetails>>? =null

    init {


        compositeDisposableSearchViewModel = CompositeDisposable()
    }
    fun initMyApi(){
        myAPI = retrofit.create(DishDetailAPI::class.java)
    }
    fun getmutableLiveDataSearchItems(): MutableLiveData<MutableList<DishesDetails>> {
        if (mutableLiveDataCartItem == null)
            mutableLiveDataCartItem = MutableLiveData()
        getStartersItems()
        return mutableLiveDataCartItem!!

    }

    private fun getStartersItems(){
        compositeDisposableSearchViewModel.addAll(myAPI!!.getDishes
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({getdishes ->

                mutableLiveDataCartItem!!.value = getdishes

            },{t:Throwable -> mutableLiveDataCartItem!!.value = null}))
    }
    fun onStop(){
        compositeDisposableSearchViewModel.clear()
    }
}