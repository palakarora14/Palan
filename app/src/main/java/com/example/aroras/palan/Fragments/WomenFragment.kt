package com.example.aroras.palan.Fragments


import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.example.aroras.palan.Models.Category
import com.example.aroras.palan.Models.CategoryAdapter
import com.example.aroras.palan.Models.WomenItems
import com.example.aroras.palan.Models.WomenItemsAdapter
import com.example.aroras.palan.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_main_screen.*
import kotlinx.android.synthetic.main.fragment_women.*


class WomenFragment : AppCompatActivity(){
    val databaseReference=FirebaseDatabase.getInstance().getReference()
    val womenItemsArray=ArrayList<WomenItems>()
    lateinit var adapter:WomenItemsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_women)

        adapter= WomenItemsAdapter(womenItemsArray)
        women_menu.layoutManager=LinearLayoutManager(this)
        women_menu.adapter=adapter


        databaseReference.child("WomenItems").addValueEventListener(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                for (i in p0.children){
                    val categoryChildern=i.getValue(WomenItems::class.java)
                    Log.d("WomenItems",categoryChildern!!.Name+" " + categoryChildern.Image)
                    womenItemsArray.add(categoryChildern)
                    adapter.notifyDataSetChanged()
                }

            }

        })
    }

}



