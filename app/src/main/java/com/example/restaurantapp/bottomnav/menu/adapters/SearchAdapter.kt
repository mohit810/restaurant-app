package com.example.restaurantapp.bottomnav.menu.adapters

import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurantapp.R
import com.example.restaurantapp.bottomnav.menu.dishdetail.DishDetail
import com.example.restaurantapp.dataprovider.dataclass.DishesDetails
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.menu_item_layout.view.*
import java.util.*
import kotlin.collections.ArrayList

class SearchAdapter(internal var context: Context, internal var modelList: MutableList<DishesDetails>):
    RecyclerView.Adapter<SearchAdapter.MyViewHolder>(), Filterable {

    internal var mCompaniesFull:MutableList<DishesDetails>
    init {
        this.mCompaniesFull = modelList //usage of copy of the list is compulsory for the search view to work properly
    }
    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var thumnail: ImageView
        var title: TextView
        var price: TextView
        var rating: TextView
        var parentLayout: ConstraintLayout
        init {
            thumnail = itemView.menu_recycler_thumbnail
            title = itemView.menu_recycler_titletxt
            price = itemView.menu_recycler_pricetxt
            rating =itemView.menu_recycler_order_rating
            parentLayout=itemView.menu_main_recycler_constraint
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var itemview = LayoutInflater.from(context).inflate(R.layout.menu_item_layout,parent,false)
        return MyViewHolder(itemview)
    }

    override fun getItemCount(): Int {
        return mCompaniesFull.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Picasso.get().load(mCompaniesFull[position].imagee).into(holder.thumnail)
        holder.title.text = mCompaniesFull[position].namee
        holder.price.text = "₹"+ mCompaniesFull[position].pricee.toString()
        holder.rating.text = mCompaniesFull[position].ratingg.toString()
        holder.parentLayout.setOnClickListener(){
            Toast.makeText(context,position.toString(), Toast.LENGTH_LONG).show()
            var intent = Intent(context, DishDetail::class.java)
            intent.putExtra("dishid",mCompaniesFull[position].idd.toString())
            intent.putExtra("dishname",mCompaniesFull[position].namee)
            intent.putExtra("dishprice", mCompaniesFull[position].pricee.toString())
            intent.putExtra("dishrating",mCompaniesFull[position].ratingg)
            intent.putExtra("dishimage",mCompaniesFull[position].imagee)
            intent.putExtra("dishdescription",mCompaniesFull[position].descriptionn)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }

    override fun getFilter(): Filter {
        return companyFilter
    }


    private val companyFilter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): Filter.FilterResults {
            val charString:String = constraint.toString()
            if (charString.isEmpty()){
               mCompaniesFull = modelList
            }
            else
            {
                val filteredList: MutableList<DishesDetails> = mutableListOf()
                for (row in modelList)
                {
                    if (row.namee!!.matchesIgnoreCase(charString))
                    {
                        filteredList.add(row)
                    }
                }
                mCompaniesFull = filteredList
            }
            var filterResults = Filter.FilterResults()
            filterResults.values = mCompaniesFull
            return filterResults
        }

        override fun publishResults(constraint: CharSequence?, results : Filter.FilterResults?) {
            mCompaniesFull = results!!.values as MutableList<DishesDetails>
            notifyDataSetChanged()
        }
    }
    private fun String.matchesIgnoreCase(otherString: String): Boolean {
        return this.toLowerCase().contains(otherString.trim().toLowerCase())
    }
}