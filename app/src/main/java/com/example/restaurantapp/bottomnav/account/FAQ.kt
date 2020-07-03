package com.example.restaurantapp.bottomnav.account

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ExpandableListAdapter
import android.widget.ExpandableListView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.restaurantapp.R
import com.example.restaurantapp.bottomnav.adapters.ExpandableAdapter
import com.example.restaurantapp.bottomnav.staticdata.ExpandableListDataPump
import com.example.restaurantapp.dataprovider.sharedpref.Prefs
import kotlinx.android.synthetic.main.activity_faq.*

class FAQ : AppCompatActivity()/*, View.OnClickListener, onRecyclerItemClicked*/ {
    internal lateinit var viewPager: ViewPager
    var viewBanner: View? = null
    var list_recycler: RecyclerView? = null
    var expandableListView: ExpandableListView? = null
    var expandableListAdapter: ExpandableListAdapter? = null
    var expandableListTitle: List<String>? = null
    var expandableListDetail: LinkedHashMap<String, MutableList<String>>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_faq)
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
        expandableListView = faq_exp_list
        expandableListDetail = ExpandableListDataPump.data()
        expandableListTitle = ArrayList<String>(expandableListDetail!!.keys)
        expandableListAdapter =
            ExpandableAdapter(this,
                expandableListTitle as ArrayList<String>, expandableListDetail!!
            )
        expandableListView!!.setAdapter(expandableListAdapter)
        expandableListView!!.setOnChildClickListener(ExpandableListView.OnChildClickListener { parent, v, groupPosition, childPosition, id ->
            Toast.makeText(
                this,
                (expandableListTitle as ArrayList<String>).get(groupPosition)
                    .toString() + " -> "
                        + expandableListDetail!!.get(
                    (expandableListTitle as ArrayList<String>).get(groupPosition)
                )!!.get(
                    childPosition
                ), Toast.LENGTH_SHORT
            ).show()
            false
        })


    }
}
