package com.example.restaurantapp.bottomnav


import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView

import com.example.restaurantapp.R
import com.example.restaurantapp.bottomnav.menu.adapters.MenuAdapter
import com.example.restaurantapp.bottomnav.menu.adapters.SearchAdapter
import com.example.restaurantapp.bottomnav.viewmodel.SearchViewModel
import com.example.restaurantapp.bottomnav.viewmodel.StartersViewModel
import com.facebook.shimmer.ShimmerFrameLayout
import kotlinx.android.synthetic.main.fragment_search.view.*

/**
 * A simple [Fragment] subclass.
 */
class Search : Fragment() {
    private var shimmerLayout : ShimmerFrameLayout? = null
    private var errorTxt: TextView? = null
    private var lottieAnimation: LottieAnimationView?=null
    private var recyclerView: RecyclerView?=null
    private var adapter: SearchAdapter?=null
    private var searchitem:SearchView?=null
    private lateinit var searchViewModel: SearchViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        searchViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        searchViewModel.initMyApi()
        val root = inflater.inflate(R.layout.fragment_search, container, false)
        initViews(root)
        searchViewModel.getmutableLiveDataSearchItems().observe(this, Observer {
            if (it == null || it.isEmpty())
            {
                shimmerLayout!!.visibility = View.GONE
                recyclerView!!.visibility = View.GONE
                searchitem!!.visibility= View.GONE
                lottieAnimation!!.visibility = View.VISIBLE
                errorTxt!!.visibility = View.VISIBLE
            }
            else
            {
                lottieAnimation!!.visibility = View.GONE
                errorTxt!!.visibility = View.GONE
                shimmerLayout!!.visibility = View.VISIBLE
                recyclerView!!.visibility = View.VISIBLE
                adapter = SearchAdapter(context!!,it)
                recyclerView!!.adapter = adapter
                shimmerLayout!!.visibility = View.GONE
                shimmerLayout!!.stopShimmer()
            }
        })
        // Inflate the layout for this fragment
        return root
    }

    private fun initViews(root: View) {
        shimmerLayout = root.search_frag_shimmerLayout
        errorTxt = root.search_frag_Errortxt
        lottieAnimation = root.search_frag_lottieAnimationView
        recyclerView = root.search_frag_recyclerView
        recyclerView!!.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(context)
        recyclerView!!.layoutManager = layoutManager
        searchitem =root.search_frag_search
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val window = activity!!.window
// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
// finally change the color
        window.statusBarColor = ContextCompat.getColor(activity!!, R.color.colorWhite)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
        }
        searchitem!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query!=null) {
                    adapter!!.filter.filter(query)
                }else{
                        Toast.makeText(context,"yo",Toast.LENGTH_SHORT).show()
                }
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText !="") {
                    adapter!!.filter.filter(newText)
                    adapter!!.notifyDataSetChanged()
                }else
                {
                    Toast.makeText(context,"yoo",Toast.LENGTH_SHORT).show()
                }
                return false
            }
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }
    override fun onStop() {
        searchViewModel!!.onStop()
        super.onStop()
    }
}
