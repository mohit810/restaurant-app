package com.example.restaurantapp.bottomnav.account

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.provider.MediaStore
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.restaurantapp.R
import com.example.restaurantapp.dataprovider.sharedpref.Prefs
import com.squareup.picasso.Picasso
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import kotlinx.android.synthetic.main.activity_account_edit.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.regex.Pattern


class AccountEdit : AppCompatActivity() {
    private val GALLERY_REQUEST_CODE = 1234
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_edit)
        val window = this.window
// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
// finally change the color
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorWhite)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
        }
        accountEditErrorMsg.visibility = View.GONE
        accountEditDisplayNameInput.setText(Prefs.customerName)
        accountEditEmailInput.setText(Prefs.customerEmailId)
        accountEditMobileNumberInput.setText(Prefs.customerMobileNumber.toString())
        accountEditAddressInput.setText(Prefs.customerAddress)
       /* val bmp = BitmapFactory.decodeFile("file://"+Prefs.profilePicUri)*/
        val loadURL = File(Prefs.profilePicUri)
        Picasso.get()
            .load(loadURL)
            .transform(CropCircleTransformation())
            .resize(400, 400)
            .into(accountEditProfilePic)
        accountEdit_Add_dp_btn.setOnClickListener(){
            pickFromGallery()
        }
        accountEditProfilePic.setOnClickListener(){
            pickFromGallery()
        }
      /*  accountEditProfilePic.setImageBitmap(bmp)*/
        accountEditSubmitbtn.setOnClickListener(){
            getData()
        }
    }
    private fun setImage(uri: Uri){
        Picasso.get()
            .load(uri)
            .transform(CropCircleTransformation())
            .resize(400, 400)
            .into(accountEditProfilePic)
        Picasso.get()
            .load(uri)
            .into(object : com.squareup.picasso.Target{
                override fun onPrepareLoad(placeHolderDrawable: Drawable?) {}
                override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {}
                override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                    try {
                        val outputStream = FileOutputStream(Prefs.profilePicUri)
                        bitmap!!.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                        outputStream.flush()
                        outputStream.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                    Toast.makeText(applicationContext, "Image Downloaded", Toast.LENGTH_LONG)
                        .show()
                }
            })
    }
    private fun launchImageCrop(uri: Uri){
        CropImage.activity(uri)
            .setGuidelines(CropImageView.Guidelines.ON)
            .setAspectRatio(400, 400)
            .setCropShape(CropImageView.CropShape.RECTANGLE) // default is rectangle
            .start(this)
    }
    private fun pickFromGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        val mimeTypes = arrayOf("image/jpeg", "image/png", "image/jpg")
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        startActivityForResult(intent, GALLERY_REQUEST_CODE)
    }
    private fun getData() {
        if (accountEditDisplayNameInput.text.toString() == "" || accountEditEmailInput.text.toString() == "" || accountEditMobileNumberInput.text.toString() == "" || accountEditAddressInput.text.toString() == "") {
            accountEditErrorMsg.setText("Please don't leave any field empty")
            accountEditErrorMsg.visibility = View.VISIBLE
            vibratePhone()

        }else  if (!isEmailValid(accountEditEmailInput.text.toString())){
            accountEditErrorMsg.setText("Please Enter correct Email ID")
            accountEditErrorMsg.visibility = View.VISIBLE
            vibratePhone()
        }else if(!isPhoneNumber(accountEditMobileNumberInput.text.toString().replace("+91", ""))){
            accountEditErrorMsg.setText("Please Enter correct Mobile")
            accountEditErrorMsg.visibility = View.VISIBLE
            vibratePhone()
        }
        else {
            Prefs.customerMobileNumber =
                accountEditMobileNumberInput.text.toString().replace("+91", "").toLong()
            Prefs.customerName = accountEditDisplayNameInput.text.toString()
            Prefs.customerAddress = accountEditAddressInput.text.toString()
            Prefs.customerEmailId = accountEditEmailInput.text.toString()
        }
    }
    fun isPhoneNumber(mobile:String) : Boolean{
        val REG = "^([\\-\\s]?)?[0]?(91)?[789]\\d{9}\$"
        val PATTERN: Pattern = Pattern.compile(REG)
        return PATTERN.matcher(mobile).find()}
    fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
    fun vibratePhone(){
        val v: Vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        // Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE))
        } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            //deprecated in API 26
            @Suppress("DEPRECATION")
            v.vibrate(100)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            GALLERY_REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    data?.data?.let { uri ->
                        launchImageCrop(uri)
                    }
                }
                else{
                    Toast.makeText(this,"Image selection error: Couldn't select that image from memory.",
                        Toast.LENGTH_SHORT).show()
                }
            }
            CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE -> {
                val result = CropImage.getActivityResult(data)
                if (resultCode == Activity.RESULT_OK) {
                    setImage(result.uri)
                }
                else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Toast.makeText(this,result.getError().toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
