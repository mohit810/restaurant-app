package com.example.restaurantapp.bottomnav.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurantapp.R
import com.example.restaurantapp.bottomnav.menu.dishdetail.DishDetail
import com.example.restaurantapp.dataprovider.dataclass.DishesDetails
import com.example.restaurantapp.dataprovider.dataclass.PastOrdersDetails
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.past_orders_recycler_item_layout.view.*

class PastOrdersAdapter(internal var context: Context, internal var modelList: List<PastOrdersDetails>) :  RecyclerView.Adapter<PastOrdersAdapter.MyViewHolder>() {
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var reorder: Button
        var rate_order: Button
        var thumnail: ImageView
        var title: TextView
        var price: TextView
        var orderDetails: TextView
        var parentLayout: ConstraintLayout

        init {
            thumnail = itemView.past_orders_recycler_img
            title = itemView.past_orders_recycler_titletxt
            price = itemView.past_orders_recycler_pricetxt
            orderDetails = itemView.past_orders_recycler_order_detailtxt
            parentLayout = itemView.past_orders_main_recycler_constraint
            reorder = itemView.past_orders_recycler_reorderbtn
            rate_order = itemView.past_orders_recycler_ratefoodbtn
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var itemview =LayoutInflater.from(context).inflate(R.layout.past_orders_recycler_item_layout,parent,false)
        return MyViewHolder(itemview)
    }

    override fun getItemCount(): Int {
        return modelList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Picasso.get().load(modelList[position].imageone).into(holder.thumnail)
        holder.title.text = "Order No. " + modelList[position].id.toString()
        holder.price.text = "₹"+ modelList[position].price.toString()
        holder.orderDetails.text = modelList[position].description.toString()
        holder.parentLayout.setOnClickListener(){
           /* Toast.makeText(context,position.toString(),Toast.LENGTH_LONG).show()
            var intent = Intent(context, DishDetail::class.java)
            intent.putExtra("dishid",modelList[position].idd.toString())
            intent.putExtra("dishname",modelList[position].namee)
            intent.putExtra("dishprice", modelList[position].pricee.toString())
            intent.putExtra("dishrating",modelList[position].ratingg)
            intent.putExtra("dishimage",modelList[position].imagee)
            intent.putExtra("dishdescription",modelList[position].descriptionn)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)*/
        }
    }
}