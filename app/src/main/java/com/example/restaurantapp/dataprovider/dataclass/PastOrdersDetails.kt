package com.example.restaurantapp.dataprovider.dataclass

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PastOrdersDetails(
    @SerializedName("id") @Expose val id : Int,
    @SerializedName("mobile") @Expose val mobile : String,
    @SerializedName("time") @Expose val time : Int,
    @SerializedName("nameone") @Expose val nameone : String,
    @SerializedName("imageone") @Expose val imageone : String,
    @SerializedName("nametwo") @Expose val nametwo : String,
    @SerializedName("imagetwo") @Expose val imagetwo : String,
    @SerializedName("nameThree") @Expose val nameThree : String,
    @SerializedName("imagethree") @Expose val imagethree : String,
    @SerializedName("namefour") @Expose val namefour : String,
    @SerializedName("imagefour") @Expose val imagefour : String,
    @SerializedName("price") @Expose val price : Int,
    @SerializedName("description") @Expose val description : String,
    @SerializedName("foodoneid") @Expose val foodoneid : Int,
    @SerializedName("foodtwoid") @Expose val foodtwoid : Int,
    @SerializedName("foodthreeid") @Expose val foodthreeid : Int,
    @SerializedName("foodfourid") @Expose val foodfourid : Int
)