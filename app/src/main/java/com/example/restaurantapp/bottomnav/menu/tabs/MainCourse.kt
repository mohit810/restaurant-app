package com.example.restaurantapp.bottomnav.menu.tabs


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.example.restaurantapp.R
import com.example.restaurantapp.bottomnav.menu.adapters.MenuAdapter
import com.example.restaurantapp.bottomnav.viewmodel.MainCourseViewModel
import com.example.restaurantapp.dataprovider.api.DishDetailAPI
import com.facebook.shimmer.ShimmerFrameLayout
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_main_course.view.*

/**
 * A simple [Fragment] subclass.
 */

class MainCourse : Fragment() {
    private var shimmerLayout :ShimmerFrameLayout? = null
    private var errorTxt:TextView? = null
    private var lottieAnimation: LottieAnimationView?=null
    private var recyclerView:RecyclerView?=null
    private var adapter:MenuAdapter?=null
private lateinit var mainCourseViewModel: MainCourseViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainCourseViewModel = ViewModelProvider(this).get(MainCourseViewModel::class.java)
        mainCourseViewModel.initMyApi()
        val root =inflater.inflate(R.layout.fragment_main_course, container, false)
        initViews(root)
        mainCourseViewModel.getmutableLiveDataMainCourseItems().observe(this, Observer {
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
                adapter = MenuAdapter(context!!,it)
                recyclerView!!.adapter = adapter
                shimmerLayout!!.visibility = View.GONE
                shimmerLayout!!.stopShimmer()
            }
        })
        // Inflate the layout for this fragment
        return root
    }

    private fun initViews(root: View) {
    shimmerLayout = root.main_course_shimmerLayout
    errorTxt = root.main_course_Errortxt
    lottieAnimation = root.main_course_lottieAnimationView
    recyclerView = root.main_course_recyclerView
        recyclerView!!.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(context)
        recyclerView!!.layoutManager = layoutManager
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onStop() {
        mainCourseViewModel!!.onStop()
        super.onStop()
    }


}
