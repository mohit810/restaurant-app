package com.example.restaurantapp.bottomnav.menu.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurantapp.R
import com.example.restaurantapp.bottomnav.menu.dishdetail.DishDetail
import com.example.restaurantapp.dataprovider.dataclass.DishesDetails
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.menu_item_layout.view.*

class MenuAdapter(internal var context: Context, internal var modelList: List<DishesDetails>):
    RecyclerView.Adapter<MenuAdapter.MyViewHolder>() {
    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        var thumnail:ImageView
        var title:TextView
        var price:TextView
        var rating:TextView
        var parentLayout:ConstraintLayout
        init {
            thumnail = itemView.menu_recycler_thumbnail
            title = itemView.menu_recycler_titletxt
            price = itemView.menu_recycler_pricetxt
            rating =itemView.menu_recycler_order_rating
            parentLayout=itemView.menu_main_recycler_constraint
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var itemview =LayoutInflater.from(context).inflate(R.layout.menu_item_layout,parent,false)
        return MyViewHolder(itemview)
    }

    override fun getItemCount(): Int {
      return modelList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            Picasso.get().load(modelList[position].imagee).into(holder.thumnail)
            holder.title.text = modelList[position].namee
            holder.price.text = "₹"+ modelList[position].pricee.toString()
            holder.rating.text = modelList[position].ratingg.toString()
            holder.parentLayout.setOnClickListener(){
                var intent = Intent(context, DishDetail::class.java)
                intent.putExtra("dishid",modelList[position].idd.toString())
                intent.putExtra("dishname",modelList[position].namee)
                intent.putExtra("dishprice", modelList[position].pricee.toString())
                intent.putExtra("dishrating",modelList[position].ratingg)
                intent.putExtra("dishimage",modelList[position].imagee)
                intent.putExtra("dishdescription",modelList[position].descriptionn)
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
            }
        }

}