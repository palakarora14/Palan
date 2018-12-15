package com.example.aroras.palan.Fragments


import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.example.aroras.palan.Models.*
import com.example.aroras.palan.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_accessories.*
import kotlinx.android.synthetic.main.fragment_main_screen.*
import kotlinx.android.synthetic.main.fragment_women.*


class AccessoriesFragment : AppCompatActivity(){
    val databaseReference=FirebaseDatabase.getInstance().getReference()
    val accessoriesItemsArray=ArrayList<AccessoriesItems>()
    lateinit var adapter:AccessoriesItemsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_accessories)

        adapter= AccessoriesItemsAdapter(accessoriesItemsArray)
        accessories_menu.layoutManager=LinearLayoutManager(this)
        accessories_menu.adapter=adapter


        databaseReference.child("AccessoriesItems").addValueEventListener(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                for (i in p0.children){
                    val categoryChildern=i.getValue(AccessoriesItems::class.java)
                    Log.d("AccessoriesItems",categoryChildern!!.Name+" " + categoryChildern.Image)
                    accessoriesItemsArray.add(categoryChildern)
                    adapter.notifyDataSetChanged()
                }

            }

        })
    }

}



