package com.example.aroras.palan.Adapters

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.aroras.palan.Activity.MainActivity
import com.example.aroras.palan.Fragments.*
import com.example.aroras.palan.MachineLearning.FindProduct
import com.example.aroras.palan.R
import java.util.ArrayList

class NavigationDrawerAdapter(_contentList: ArrayList<String>, _getImages: IntArray, _context: Context) :
        RecyclerView.Adapter<NavigationDrawerAdapter.NavViewHolder>() {
    var contentList: ArrayList<String>? = null
    var getImages: IntArray? = null
    var mContext: Context? = null

    init {
        this.contentList = _contentList
        this.getImages = _getImages
        this.mContext = _context
    }

    override fun onBindViewHolder(holder: NavViewHolder, position: Int) {
        holder.icon_GET?.setBackgroundResource(getImages?.get(position) as Int)
        holder.text_GET?.setText(contentList?.get(position))
        holder.contentHolder?.setOnClickListener({
            if (position == 0) {
//                val mainScreenFragment = MainScreenFragment()
//                (mContext as MainActivity).supportFragmentManager
//                        .beginTransaction()
//                        .replace(R.id.details_fragment, mainScreenFragment)
//                        .commit()
                val startAct = Intent(mContext,MainScreenFragment::class.java)
                mContext?.startActivity(startAct)
            } else if (position == 1) {
             val startAct = Intent(mContext,WomenFragment::class.java)
                mContext?.startActivity(startAct)
            }
            else if (position == 2) {
                val startAct = Intent(mContext,MenFragment::class.java)
                mContext?.startActivity(startAct)


            }else if (position == 3) {
                val startAct = Intent(mContext,AccessoriesFragment::class.java)
                mContext?.startActivity(startAct)

            }else if (position == 4) {
                val startAct =Intent(mContext,FindProduct::class.java)
                mContext?.startActivity(startAct)
            }
            else if (position == 5) {
                val startAct = Intent(mContext,YourAccountFragment::class.java)
                mContext?.startActivity(startAct)

            }
            else if (position == 6){
                val startAct = Intent(mContext,AboutUsFragment::class.java)
                mContext?.startActivity(startAct)

            }
            MainActivity.Statified.drawerLayout?.closeDrawers()
        })

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NavViewHolder {
        var itemView = LayoutInflater.from(parent?.context)
                .inflate(R.layout.row_custom_navigationdrawer, parent, false)
        val returnThis = NavViewHolder(itemView)
        return returnThis
    }

    override fun getItemCount(): Int {
        return (contentList as ArrayList).size

    }


    class NavViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var icon_GET: ImageView? = null
        var text_GET: TextView? = null
        var contentHolder: RelativeLayout? = null

        init {
            icon_GET = itemView?.findViewById(R.id.icon_navdrawer)
            text_GET = itemView?.findViewById(R.id.text_navdrawer)
            contentHolder = itemView?.findViewById(R.id.navdrawer_item_content_holder)
        }
    }
}
