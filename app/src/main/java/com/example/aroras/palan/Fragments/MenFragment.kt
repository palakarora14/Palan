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
import kotlinx.android.synthetic.main.fragment_main_screen.*
import kotlinx.android.synthetic.main.fragment_men.*
import kotlinx.android.synthetic.main.fragment_women.*


class MenFragment : AppCompatActivity(){
    val databaseReference=FirebaseDatabase.getInstance().getReference()
    val menItemsArray=ArrayList<MenItems>()
    lateinit var adapter:MenItemsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_men)

        adapter= MenItemsAdapter(menItemsArray)
        men_menu.layoutManager=LinearLayoutManager(this)
        men_menu.adapter=adapter


        databaseReference.child("MenItems").addValueEventListener(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                for (i in p0.children){
                    val categoryChildern=i.getValue(MenItems::class.java)
                    Log.d("MenItems",categoryChildern!!.Name+" " + categoryChildern.Image)
                    menItemsArray.add(categoryChildern)
                    adapter.notifyDataSetChanged()
                }

            }

        })
    }

}



