package com.example.restaurantapp.bottomnav.staticdata

import com.example.restaurantapp.R
import java.util.*

object ExpandableListDataPump {
    fun data():LinkedHashMap<String, MutableList<String>>
         {
            val expandableListDetail: LinkedHashMap<String, MutableList<String>> =
                LinkedHashMap<String, MutableList<String>>()
            val myAccount: MutableList<String> =
                ArrayList()
            myAccount.add("Manage Address")
            myAccount.add("Payment")
            myAccount.add("Favorites")
            myAccount.add("Others")
            val helpExpand: MutableList<String> =
                ArrayList()
            helpExpand.add("FAQ")
            helpExpand.add("Chat with Us")
            helpExpand.add("Call Us")
            expandableListDetail["My Account"] = myAccount
            expandableListDetail["Help"] = helpExpand
            return expandableListDetail
        }
}
