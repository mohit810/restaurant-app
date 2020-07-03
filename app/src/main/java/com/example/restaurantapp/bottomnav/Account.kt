package com.example.restaurantapp.bottomnav


import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.restaurantapp.R
import com.example.restaurantapp.bottomnav.account.*
import com.example.restaurantapp.dataprovider.sharedpref.Prefs
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import kotlinx.android.synthetic.main.fragment_account.*
import java.io.File


/**
 * A simple [Fragment] subclass.
 */
class Account : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val window = activity!!.window
// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
// finally change the color
        window.statusBarColor = ContextCompat.getColor(activity!!, R.color.colorSoothingRed)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE)
        }
        if (Prefs.checker.equals("on")) {
            val loadURL = File(Prefs.profilePicUri)
            Picasso.get()
                .load(loadURL)
                .transform(CropCircleTransformation())
                .resize(400, 400)
                .into(account_frag_profilePic)
            getLoggedInButtons()
        }
        account_frag__faq.setOnClickListener(){
            startActivity(Intent(activity!!,FAQ::class.java))
        }
        account_frag__call_us_card.setOnClickListener(){
            startActivity(Intent(activity!!,CallUs::class.java))
        }

    }

    private fun getLoggedInButtons() {
        account_frag_manageAccount.setOnClickListener(){
            startActivity(Intent(activity!!, AccountDisplay::class.java))
        }
        account_frag__past_order.setOnClickListener(){
            startActivity(Intent(activity!!, PastOrders::class.java))
        }
    }

}
