package com.example.restaurantapp.dataprovider.sharedpref

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

object Prefs {
    private const val MODE = Context.MODE_PRIVATE
    private const val NAME = "Restaurant"
    val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
    private lateinit var preferences: SharedPreferences

    // list of app specific preferences
    private val IS_LOGGED_IN = Pair("login_status"," ")
    private val USER_NAME = Pair("key1"," ")
    private val EMAIL_ID = Pair("key2"," ")
    private val PASS_WORD = Pair("key3"," ")
    private val FULL_ADDRESS = Pair("key4",null)
    private val MOBILE_NUMBER:Pair<String,Long> = Pair("key5",0)
    private val GPS_ADDRESS = Pair("key6",null)
    private val PROFILE_PIC_URI = Pair("key7",null)

    fun init(context: Context) {
        /*preferences = context.getSharedPreferences(NAME, MODE)*/
        preferences = EncryptedSharedPreferences.create(
            NAME,
            masterKeyAlias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }
    /**
     * SharedPreferences extension function, so we won't need to call edit() and apply()
     * ourselves on every SharedPreferences operation.
     */
    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = preferences.edit()
        operation(editor)
        editor.apply()
    }
    var checker:String
        // custom getter to get a preference of a desired type, with a predefined default value
        get() = preferences.getString(IS_LOGGED_IN.first, IS_LOGGED_IN.second)!!
        // custom setter to save a preference back to preferences file
        set(value) = preferences.edit{
        it.putString(IS_LOGGED_IN.first,value)
    }
    var customerName:String
        // custom getter to get a preference of a desired type, with a predefined default value
        get() = preferences.getString(USER_NAME.first, USER_NAME.second)!!
        // custom setter to save a preference back to preferences file
        set(value) = preferences.edit{
            it.putString(USER_NAME.first,value)
        }
    var customerEmailId:String
        // custom getter to get a preference of a desired type, with a predefined default value
        get() = preferences.getString(EMAIL_ID.first, EMAIL_ID.second)!!
        // custom setter to save a preference back to preferences file
        set(value) = preferences.edit{
            it.putString(EMAIL_ID.first,value)
        }
    var customerPwd:String
        // custom getter to get a preference of a desired type, with a predefined default value
        get() = preferences.getString(PASS_WORD.first, PASS_WORD.second)!!
        // custom setter to save a preference back to preferences file
        set(value) = preferences.edit{
            it.putString(PASS_WORD.first,value)
        }
    var customerAddress:String
        // custom getter to get a preference of a desired type, with a predefined default value
        get() = preferences.getString(FULL_ADDRESS.first, FULL_ADDRESS.second)!!
        // custom setter to save a preference back to preferences file
        set(value) = preferences.edit{
            it.putString(FULL_ADDRESS.first,value)
        }
    var customerMobileNumber:Long
        // custom getter to get a preference of a desired type, with a predefined default value
        get() = preferences.getLong(MOBILE_NUMBER.first, MOBILE_NUMBER.second)!!
        // custom setter to save a preference back to preferences file
        set(value) = preferences.edit{
            it.putLong(MOBILE_NUMBER.first,value)
        }
    var gpsAddress:String
        // custom getter to get a preference of a desired type, with a predefined default value
        get() = preferences.getString(GPS_ADDRESS.first, GPS_ADDRESS.second)!!
        // custom setter to save a preference back to preferences file
        set(value) = preferences.edit{
            it.putString(GPS_ADDRESS.first,value)
        }
    var profilePicUri:String
        // custom getter to get a preference of a desired type, with a predefined default value
        get() = preferences.getString(PROFILE_PIC_URI.first, PROFILE_PIC_URI.second)!!
        // custom setter to save a preference back to preferences file
        set(value) = preferences.edit{
            it.putString(PROFILE_PIC_URI.first,value)
        }
}