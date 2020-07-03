package com.example.restaurantapp.dataprovider.dataclass

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DishesDetails (
        @SerializedName("id")
        @Expose
        public val idd: Int,

        @SerializedName("name")
        @Expose
        public val namee: String,

        @SerializedName("image")
        @Expose
        public val imagee: String,

        @SerializedName("category")
        @Expose
        public val categoryy: String,

        @SerializedName("price")
        @Expose
        public val pricee: Int,

        @SerializedName("description")
        @Expose
        public val descriptionn: String,

        @SerializedName("label")
        @Expose
        public val labell: String,

        @SerializedName("comments")
        @Expose
        public val comments: String,
        @SerializedName("rating")
        @Expose
        public val ratingg: Int,
        @SerializedName("Time")
        @Expose
        public val timee: Int
)