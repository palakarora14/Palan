package com.example.aroras.palan.Models

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.aroras.palan.Activity.ItemWomenDetails
import com.example.aroras.palan.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.items.view.*

data class WomenItems(val Name:String,val Image:String,val Description:String,val Price:String,val Discount:String,val MenuId:String){


    constructor():this("","","","","","")
}

class WomenItemsAdapter(val data:ArrayList<WomenItems>): RecyclerView.Adapter<WomenItemsAdapter.ViewHolder>() {

    lateinit var adapter:WomenItemsAdapter
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val li=p0.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val holder=li.inflate(R.layout.items,p0,false)

        return ViewHolder(holder)
    }

    override fun getItemCount(): Int =data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.item_name.text=data[position].Name
        Picasso.with(holder.itemView.context).load(data[position].Image).fit()
                .into(holder.itemView.item_image)
        holder.itemView.setOnClickListener{
            var pos=(position+1).toString()
            val startAct = Intent(holder.itemView.context, ItemWomenDetails::class.java)
            //get Item id
            startAct.putExtra("ItemId",pos)

            holder.itemView.context.startActivity(startAct)
        }
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }
}

