package com.example.restaurantapp.bottomnav.account

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.restaurantapp.R
import com.example.restaurantapp.dataprovider.sharedpref.Prefs
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import kotlinx.android.synthetic.main.activity_account_display.*
import java.io.File

class AccountDisplay : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_display)
        val window = this.window
// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
// finally change the color
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorSoothingRed)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE)
        }
        accountDisplayDisplayNameInput.setText(Prefs.customerName)
        accountDisplayemailinput.setText(Prefs.customerEmailId)
        accountDisplaymobilenumberinput.setText(Prefs.customerMobileNumber.toString())
        accountDisplayaddressinput.setText(Prefs.customerAddress)
        accountDisplaypswdinput.setText(Prefs.customerPwd)
        /* val bmp = BitmapFactory.decodeFile("file://"+Prefs.profilePicUri)*/
        val loadURL = File(Prefs.profilePicUri)
        Picasso.get()
            .load(loadURL)
            .transform(CropCircleTransformation())
            .resize(400, 400)
            .into(accountDisplayUserPic)
        accountDisplayEditProfile.setOnClickListener(){
            startActivity(Intent(this, AccountEdit::class.java))
        }
    }
}
