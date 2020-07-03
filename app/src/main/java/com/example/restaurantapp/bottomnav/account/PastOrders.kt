package com.example.restaurantapp.bottomnav.account

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.example.restaurantapp.R
import com.example.restaurantapp.bottomnav.adapters.PastOrdersAdapter
import com.example.restaurantapp.bottomnav.viewmodel.PastOrdersViewModel
import com.example.restaurantapp.dataprovider.sharedpref.Prefs
import com.facebook.shimmer.ShimmerFrameLayout
import kotlinx.android.synthetic.main.activity_past_orders.view.*

class PastOrders : AppCompatActivity() {
    private var shimmerLayout : ShimmerFrameLayout? = null
    private var errorTxt: TextView? = null
    private var lottieAnimation: LottieAnimationView?=null
    private var recyclerView: RecyclerView?=null
    private var adapter: PastOrdersAdapter?=null
    private lateinit var pastOrdersViewModel: PastOrdersViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_past_orders)
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
        val root = window.decorView.rootView
        pastOrdersViewModel = ViewModelProvider(this).get(PastOrdersViewModel::class.java)
        pastOrdersViewModel.initMyApi()
        initViews(root)
        pastOrdersViewModel.getmutableLiveDataPastOrdersItems(Prefs.customerMobileNumber.toString()).observe(this, Observer {
            if (it == null || it.isEmpty())
            {
                shimmerLayout!!.visibility = View.GONE
                recyclerView!!.visibility = View.GONE
                lottieAnimation!!.visibility = View.VISIBLE
                errorTxt!!.visibility = View.VISIBLE
            }
            else
            {
                lottieAnimation!!.visibility = View.GONE
                errorTxt!!.visibility = View.GONE
                shimmerLayout!!.visibility = View.VISIBLE
                recyclerView!!.visibility = View.VISIBLE
                adapter = PastOrdersAdapter(this,it)
                recyclerView!!.adapter = adapter
                shimmerLayout!!.visibility = View.GONE
                shimmerLayout!!.stopShimmer()
            }
        })

    }
    private fun initViews(root: View) {
        shimmerLayout = root.pastOrders_shimmerLayout
        errorTxt = root.pastOrders_Errortxt
        lottieAnimation = root.pastOrders_lottieAnimationView
        recyclerView = root.pastOrders_recyclerView
        recyclerView!!.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this)
        recyclerView!!.layoutManager = layoutManager
    }
    override fun onStop() {
        pastOrdersViewModel!!.onStop()
        super.onStop()
    }

}
