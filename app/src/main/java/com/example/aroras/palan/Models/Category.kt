package com.example.aroras.palan.Models

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.aroras.palan.Fragments.*
import com.example.aroras.palan.MachineLearning.FindProduct
import com.example.aroras.palan.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.category_items.view.*

data class Category(val Name:String,val Image:String){


    constructor():this("","")
}

class CategoryAdapter(val data:ArrayList<Category>):RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val li=p0.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val holder=li.inflate(R.layout.category_items,p0,false)
        return ViewHolder(holder)
    }

    override fun getItemCount(): Int =data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.category_name.text=data[position].Name
        Picasso.with(holder.itemView.context).load(data[position].Image).fit()
                .into(holder.itemView.category_image)
        holder.itemView.setOnClickListener{
            //Toast.makeText(holder.itemView.context, position.toString(), Toast.LENGTH_SHORT).show()
            if (position == 0) {
                val startAct = Intent(holder.itemView.context, WomenFragment::class.java)
                holder.itemView.context?.startActivity(startAct)
            } else if (position == 1) {
                val startAct = Intent(holder.itemView.context, MenFragment::class.java)
                holder.itemView.context?.startActivity(startAct)
            } else if (position == 2) {
                val startAct = Intent(holder.itemView.context, AccessoriesFragment::class.java)
                holder.itemView.context?.startActivity(startAct)
            }else if (position == 3) {
                val startAct = Intent(holder.itemView.context, FindProduct::class.java)
                holder.itemView.context?.startActivity(startAct)

            }
//            else if (position == 4) {
//                val startAct = Intent(holder.itemView.context, KarmaBucketFragment::class.java)
//                holder.itemView.context?.startActivity(startAct)
//
//         }
//            else if (position == 5) {
//                val startAct = Intent(holder.itemView.context, NgoFragment::class.java)
//                holder.itemView.context?.startActivity(startAct)
//            }
        }
    }

    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {

    }
}

