package com.example.restaurantapp.dataprovider.dataclass

import android.media.Image
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Details (
    @SerializedName("name")
    @Expose
    public val name: String,

    @SerializedName("email")
    @Expose
    public val email: String,

    @SerializedName("password")
    @Expose
    public val password: String,

    @SerializedName("mobile")
    @Expose
    public val mobile: String,

    @SerializedName("address")
    @Expose
    public val address: String,

    @SerializedName("image")
    @Expose
    public val image: String
)